package gr.tylr.level;

import gr.tylr.entity.Entity;
import gr.tylr.state.TyLRGame;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.tiled.TiledMap;

public class Level {

    TiledMap map;
    Entity[][] mapOccupation;

    public Level(TiledMap map)  {
        this.map = map;
        mapOccupation = new Entity[map.getWidth()][map.getHeight()];
    }

    public void render() {        
        map.render(0, TyLRGame.getWindowHeight() - 
                      map.getHeight()*map.getTileHeight());                
    }

    public TiledMap getTileMap() {
        return map;
    }
    
    public void setMapOccupation(int i, int j, Entity entity) {
        mapOccupation[i][j] = entity;
    }
    
    public boolean isOccupied(float x, float y) {
        try {        
            return mapOccupation[(int)x][(int)y] != null;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
	
	public boolean isOccupied(Vec2 position) {
		return isOccupied(position.x, position.y);
	}
    
    /**
     * Return height in pixels
     */
    public int getHeight() {
        return map.getHeight()*map.getTileHeight();
    }
    
    /**
     * Return width in pixels
     */
    public int getWidth() {
        return map.getWidth()*map.getTileWidth();
    }
    
    public Entity[][] getMapOccupation() {
        return mapOccupation;                
    }

	Entity getOccupiedEntity(Vec2 position) {
		try {        
            return mapOccupation[(int)position.x][(int)position.y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
	}
}
