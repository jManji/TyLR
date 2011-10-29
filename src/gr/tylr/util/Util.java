package gr.tylr.util;

import gr.tylr.level.LevelManager;
import gr.tylr.state.GameplayState;
import gr.tylr.util.Consts.Direction;
import static gr.tylr.util.Consts.Direction.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Input;

public abstract class Util {

    private static long id = 0;
    
    public static float clamp (float currentValue, float minValue, float maxValue) {
            return Math.min(maxValue, Math.max(currentValue, minValue));
    }

    /**
     * printError Prints an error, regarding an error level
     * @param level 1 for warning, 2 for error
     * @param errorMessage The error string
     */
    public static void printError(int level, String errorMessage) {
		Date date = new Date();
		if (level == 1) {
				System.out.println("WARNING " + "[" + getTime() + "]: " + 
													errorMessage);
		} else if (level == 2) {
				System.err.println("ERROR [" + getTime() + "]: " + 
														errorMessage);
		}
    }

    private static String getTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss:SSS");
		return sdf.format(cal.getTime());
    }

    public static long generateID() {
        return id++;
    }

    public static float mapToWorld(float x) {
        return x * LevelManager.getTileHeight();
    }
    
    public static Direction getDirectionFromInput () {
        Direction direction = NONE;

        if (GameplayState.getContainer().getInput().isKeyPressed(Input.KEY_A)) {
            direction = LEFT;
        }
        if (GameplayState.getContainer().getInput().isKeyPressed(Input.KEY_D)) {            
            direction = RIGHT;
        }
        if (GameplayState.getContainer().getInput().isKeyPressed(Input.KEY_W)) {
            direction = UP;
        }
        if (GameplayState.getContainer().getInput().isKeyPressed(Input.KEY_S)) {
            direction = DOWN;
        }
        return direction;
    }
    
    public static Vec2 getMapPosition(Vec2 position) {
        return new Vec2((int)(position.x/LevelManager.getTileHeight()), 
                        (int)(position.y/LevelManager.getTileHeight()));
    }
}
