package gr.jmanji.tylr.level;

import java.util.ArrayList;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.SlickException;

public class LevelManager {

	private ArrayList<Level> levels;
	private static Level currentLevel;
	
	public void init() throws SlickException {
		levels = new ArrayList<Level>();
		
		levels.add(new Level(new TiledMap("/data/levels/map01.tmx")));
		
		// Sanity 
		currentLevel = levels.get(0);
	}
	
	public static Level getCurrentLevel() {
		return currentLevel;
	}
	
	public void setLevel(int index) {
		currentLevel = levels.get(index);
	}
	
}
