package gr.tylr.entity;

import gr.tylr.resource.Sprite;
import gr.tylr.state.GameplayState;
import gr.tylr.util.Consts;
import gr.tylr.util.Consts.Button;
import gr.tylr.util.Consts.HeroState;
import static gr.tylr.util.Consts.HeroState.*;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.Input;

/**
 *
 * @author manji
 */
public class Hero extends DynamicEntity {
    
    private final static float JUMP_FORCE_PRESSED = 410.0f;
    private final static float JUMP_FORCE_DOWN = 20.0f;
    private final static float MOVEMENT_FORCE = 20.0f;
    private final static int MAX_JUMP_TIMER = 400;
	private final static float ON_GROUND_OFFSET = 0.0005f;
    public final int SPAWN_RANGE = 4; 
    private long jumpTimer;
    private TileSpawner tileSpawner;
    private HeroState state;	
	private AABB aabb;
	private onGroundCallback onGroundCallback;
	private boolean isOnAir = false;
	private boolean isJumping = false;
	private Vec2 size;
	
    public Hero(Vec2 position, Vec2 size, String name) {
        super(position, size, name, Sprite.MEGAMAN);
        tileSpawner = new TileSpawner(this);
        state = NORMAL;
		this.size = size;
		
		aabb = new AABB();
		onGroundCallback = new onGroundCallback();	
    }
	
	public Hero(Vec2 position, String name) {
        this(position, 
		     new Vec2(Sprite.MEGAMAN.getWidth(), Sprite.MEGAMAN.getHeight()), 
			 name);
    }
    
	@Override
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
		
        if (GameplayState.getContainer().getInput().isKeyPressed(Input.KEY_SPACE) && isOnGround()) {			
            body.applyForce(new Vec2(000.0f, JUMP_FORCE_PRESSED), body.getWorldCenter());
            jumpTimer = 0;
			isJumping = true;
        } else if (isJumping && GameplayState.getContainer().getInput().isKeyDown(Input.KEY_SPACE) &&
	    		   jumpTimer < MAX_JUMP_TIMER) {
            body.applyForce(new Vec2(000.0f, JUMP_FORCE_DOWN), body.getWorldCenter());
            jumpTimer += delta;
        }
        else {
            isJumping = false;
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
		
		boolean spawnPressed = false;
		boolean unspawnPressed = false;
		
		if (GameplayState.getContainer().getInput().
                               isKeyPressed(Button.SPAWN_CIRCLE.getButton()) ) {
			spawnPressed = true;
		} else if (GameplayState.getContainer().getInput().
                               isKeyPressed(Button.UNSPAWN_CIRCLE.getButton())){
			unspawnPressed = true;
		}				
		
		
		if (spawnPressed || unspawnPressed) {
			if (state == NORMAL) {
				state = spawnPressed ? SPAWN : UNSPAWN;
				equipTileSpawner();
			} else {
				unequipTileSpawner();
			}
		}		

    }
    
	private void equipTileSpawner() {
		
		// Not sure why, tileSpawner.update will use the previously 
		// cached key here, so I clear it						
		GameplayState.getContainer().getInput().clearKeyPressedRecord();

		EntityManager.addPost(tileSpawner, true, true);

		// Put body to sleep while using tileSpawner
		body.setAwake(false);
		
		tileSpawner.spawnCircle();
	}
	
	void unequipTileSpawner() {
		
		state = NORMAL;
		
		// No physics while spawninig/unspawning				
		tileSpawner.clear();
		
		// Wake up body after unequipping
		body.setAwake(true);
	}	    
	
	public class onGroundCallback implements QueryCallback {

		public boolean reportFixture(Fixture fixture) {		
			
			GameplayState.getWorld().clearForces();
			AbstractPhysicsEntity entity = 
					(AbstractPhysicsEntity)(fixture.getBody().getUserData());
			
			if (!entity.getName().equals("HERO")) {
				isOnAir = false;			
			}
			
			// Will terminate query if false
			return isOnAir;
		}
	} 
	
	public boolean isOnGround() {
		
		isOnAir = true;
	
		// Do not test if no vertical velocity
		if (body.getLinearVelocity().y == 0.0f) {
		
			aabb.lowerBound.set(body.getPosition().x - size.x/Consts.PTM_SCALE,
								getWorldPosition().y/Consts.PTM_SCALE - 
							    ON_GROUND_OFFSET);

			aabb.upperBound.set(body.getPosition().x + size.x/Consts.PTM_SCALE,
								getWorldPosition().y/Consts.PTM_SCALE);	
						
			GameplayState.getWorld().queryAABB(onGroundCallback, aabb);				
		}
		
		return !isOnAir;
	}
	 	
	HeroState getState() {
		return state;
	}
	
	Vec2 getSize() {
		return size;
	}
}
