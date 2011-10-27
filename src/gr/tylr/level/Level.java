package gr.tylr.level;

import gr.tylr.state.TyLRGame;
import org.newdawn.slick.tiled.TiledMap;

public class Level {

    TiledMap map;
    boolean[][] mapOccupation;

    public Level(TiledMap map)  {
        this.map = map;
        mapOccupation = new boolean[map.getWidth()][map.getHeight()];
    }

    public void render() {        
        map.render(0, TyLRGame.getWindowHeight() - 
                      map.getHeight()*map.getTileHeight());                
    }

    public TiledMap getTileMap() {
        return map;
    }
    
    public void setMapOccupation(int i, int j) {
        mapOccupation[i][j] = true;
    }
    
    public boolean isOccupied(float x, float y) {
        try {        
            return mapOccupation[(int)x][(int)y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
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
    
    public boolean[][] getMapOccupation() {
        return mapOccupation;                
    }
}
