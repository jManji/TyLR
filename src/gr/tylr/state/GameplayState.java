package gr.tylr.state;

import gr.tylr.level.LevelManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author manji
 */
public class GameplayState extends BasicGameState{

    LevelManager levelManager;

    @Override
    public int getID() {
        return 0;
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        levelManager = new LevelManager();
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        LevelManager.getCurrentLevel().render();
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}
