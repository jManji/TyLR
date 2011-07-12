package gr.jmanji.tylr.character;

import java.util.ArrayList;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CharacterManager {

	private static MainCharacter mainChar;
	private ArrayList<Character> characters;
	
	public void init() throws SlickException {
		mainChar = new MainCharacter(new Image ("/data/megaman.png"));
	}
	
	public static MainCharacter getMainCharacter() {
		return mainChar;
	}
}
