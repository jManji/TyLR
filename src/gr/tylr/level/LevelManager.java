package gr.tylr.level;

import java.util.ArrayList;
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
}
