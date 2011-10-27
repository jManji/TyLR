package gr.tylr.state;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author manji
 */
public class TyLRGame extends StateBasedGame {

    private static GameContainer container;
    
    public TyLRGame () {
        super("test game");
    }

    public void initStatesList(GameContainer container) throws SlickException{
        this.container = container;
        addState(new GameplayState());
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new TyLRGame());
        app.setVSync(true);
        app.setDisplayMode(800, 600, false);
        app.start();
    }
    
    public static int getWindowHeight() {
        return container.getHeight();
    }

    public static int getWindowWidth() {
        return container.getWidth();
    }

}
