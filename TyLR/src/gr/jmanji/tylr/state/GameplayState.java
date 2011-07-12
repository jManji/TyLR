package gr.jmanji.tylr.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import gr.jmanji.tylr.character.CharacterManager;
import gr.jmanji.tylr.character.MainCharacter;
import gr.jmanji.tylr.level.Level;
import gr.jmanji.tylr.level.LevelManager;

public class GameplayState extends BasicGameState{
	
	LevelManager levelManager;
	CharacterManager characterManger;
	Level currentLevel;
	MainCharacter mainChar;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		levelManager = new LevelManager();
		levelManager.init();
		levelManager.setLevel(0);
		
		characterManger = new CharacterManager();
		characterManger.init();
		
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		mainChar = CharacterManager.getMainCharacter();
		currentLevel = LevelManager.getCurrentLevel();
		
		mainChar.setPosition(new Vector2f(35.0f, 35.0f));
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics graphics)
			throws SlickException {
		currentLevel.render();
		mainChar.render();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		mainChar.update(container, game, delta);
		// TODO Auto-generated method stub
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
}
