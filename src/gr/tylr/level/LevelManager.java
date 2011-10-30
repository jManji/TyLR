package gr.tylr.level;

import gr.tylr.entity.Entity;
import gr.tylr.entity.EntityManager;
import gr.tylr.entity.StaticEntity;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;

/**
 *
 * @author manji
 */
public class LevelManager {
    
    private ArrayList<Level> levels;
    private static Level currentLevel;
    

    public LevelManager() throws SlickException {
        
        levels = new ArrayList<Level>();

        levels.add(new Level(new TiledMap("/data/levels/map04_48.tmx")));

        // Sanity
        currentLevel = levels.get(0);
    }

    public static Level getCurrentLevel() {
        return currentLevel;
    }

    public void setLevel(int index) {
        try {
            currentLevel = levels.get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.warn("Level " + index + " does not exist");
        }
    }
    
    public void loadCurrentLevel() {
        TiledMap tiledMap = currentLevel.getTileMap();
        float size = tiledMap.getTileHeight();
        for (int x = 0; x < tiledMap.getWidth(); x++) {
            for (int y = 0; y < tiledMap.getHeight(); y++) {  
                if (tiledMap.getTileId(x, y, 0) == 1) {
					Entity mapTileEntity = new StaticEntity(new Vec2(x*size,
                                             (tiledMap.getHeight()-y-1)*size),
                                         new Vec2(size, size),
                                         ("MAP_TILE_" + x + "_" + y));                                         
                    EntityManager.add(mapTileEntity, false, false);
                    currentLevel.setMapOccupation(x,
							                      tiledMap.getHeight()-y-1,
												  mapTileEntity);
                }
            }
        }
    }
    
    public static float getTileHeight() {
        return currentLevel.getTileMap().getTileHeight();
    }
    
    public static boolean isOccupied(float x, float y) {
        return currentLevel.isOccupied(x, y);
    }
    
	public static boolean isOccupied(Vec2 position) {
        return currentLevel.isOccupied(position);
    }
	
	public static Entity getOccupiedEntity(Vec2 position) {
        return currentLevel.getOccupiedEntity(position);
    }
	
    public static int getLevelHeight() {
        return currentLevel.getTileMap().getHeight();
    }

}
