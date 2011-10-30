package gr.tylr.util;

import org.newdawn.slick.Input;

/**
 *
 * @author manji
 */
public class Consts {
    // Pixels to meter scale
    public static final float PTM_SCALE = 30.0f;
    
    public enum HeroState {
        NORMAL, 
        SPAWN,
		UNSPAWN;
    }
    
    public enum Direction {
        UP, 
        DOWN,
        LEFT,
        RIGHT,
        NONE;
    }
    
    public enum Button {
        UP(Input.KEY_W), 
        DOWN(Input.KEY_S), 
        LEFT(Input.KEY_A),
        RIGHT(Input.KEY_D),
        JUMP(Input.KEY_SPACE),
        SPAWN(Input.KEY_SPACE),
        SPAWN_CIRCLE(Input.KEY_Z),
		UNSPAWN_CIRCLE(Input.KEY_X);

        private int button;

        private Button(int button) {
            this.button = button;
        }
        
        public int getButton() {
            return button;
        }
    }
}
