package gr.tylr.entity;

import org.newdawn.slick.Graphics;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Input;
import gr.tylr.state.GameplayState;
import gr.tylr.util.Consts.Button;
import gr.tylr.util.Consts.HeroState;
import gr.tylr.resource.Sprite;
import static gr.tylr.util.Consts.HeroState.*;

/**
 *
 * @author manji
 */
public class Hero extends DynamicEntity {
    
    private final static float JUMP_FORCE_PRESSED = 530.0f;
    private final static float JUMP_FORCE_DOWN = 20.0f;
    private final static float MOVEMENT_FORCE = 30.0f;
    private final static int MAX_JUMP_TIMER = 400;
    public final int SPAWN_RANGE = 4; 
    private long jumpTimer;
    private TileSpawner tileSpawner;
    private HeroState state;
    
    public Hero(Vec2 position, Vec2 size, String name) {
        super(position, size, name, Sprite.MEGAMAN);
        tileSpawner = new TileSpawner(this);
        state = NORMAL;
    }
    
    public void update(final int delta) {
        
        switch (state) {
            case NORMAL: {
                
                super.update(delta);
        
                jump(delta);

                move();
                
                break;
            }
            case SPAWN: {
                
                // Do nothing
                
                break;
            }
            default: {
                // Do nothing again
            }
        }

        spawnTiles();
    }
    
    private void jump(final int delta) {
        if (GameplayState.getContainer().getInput().isKeyPressed(Input.KEY_SPACE)) {
            body.applyForce(new Vec2(000.0f, JUMP_FORCE_PRESSED), body.getWorldCenter());
            jumpTimer = 0;
        } else if (GameplayState.getContainer().getInput().isKeyDown(Input.KEY_SPACE) &&
	    		   jumpTimer < MAX_JUMP_TIMER) {
            body.applyForce(new Vec2(000.0f, JUMP_FORCE_DOWN), body.getWorldCenter());
            jumpTimer += delta;
        }
        else { 
            
        }
    } 

    private void move() {
              
        if (GameplayState.getContainer().getInput().isKeyDown(Input.KEY_D)) {
            body.applyForce(new Vec2(MOVEMENT_FORCE, 0.0f), body.getWorldCenter());
        }
        if (GameplayState.getContainer().getInput().isKeyDown(Input.KEY_A)) {
            body.applyForce(new Vec2(-MOVEMENT_FORCE, 0.0f), body.getWorldCenter());
        }
    }
    
    private void spawnTiles() {
        
        if ( state != SPAWN && GameplayState.getContainer().getInput().
                               isKeyPressed(Button.SPAWN_CIRCLE.getButton())) {
            
            // Not sure why, tileSpawner.update will use the previously 
            // cached key here, so i clear it
            GameplayState.getContainer().getInput().clearKeyPressedRecord();

            System.out.println("add");
            EntityManager.addPost(tileSpawner, true, false);

            tileSpawner.spawnCircle();
            state = SPAWN;
        }
//        if (state == SPAWN && 
//            GameplayState.getContainer().getInput().isKeyPressed(Input.KEY_X)) {
//            System.out.println("remove");
//            tileSpawner.clear();
//            EntityManager.remove("TILE_SPAWNER");
//            state = NORMAL;
//        }
    }
    
    public void unSpawn() {
        state = NORMAL;
    }
            
}
