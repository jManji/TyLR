package gr.tylr.entity;

import gr.tylr.resource.Sprite;
import gr.tylr.state.GameplayState;
import gr.tylr.util.Consts;
import gr.tylr.util.Consts.Button;
import gr.tylr.util.Consts.HeroState;
import static gr.tylr.util.Consts.HeroState.NORMAL;
import static gr.tylr.util.Consts.HeroState.SPAWN;
import gr.tylr.util.Util;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;

/**
 *
 * @author manji
 */
public class Hero extends DynamicEntity {
    
    private final static float JUMP_FORCE_PRESSED = 530.0f;
    private final static float JUMP_FORCE_DOWN = 20.0f;
    private final static float MOVEMENT_FORCE = 30.0f;
    private final static int MAX_JUMP_TIMER = 400;
	private final static float ON_GROUND_OFFSET = 0.46f;
    public final int SPAWN_RANGE = 4; 
    private long jumpTimer;
    private TileSpawner tileSpawner;
    private HeroState state;
	private boolean isOnAir = false;
	private boolean isJumping = false;
	private AABB aabb;
	onGroundCallback onGroundCallback;
	StaticEntity s;
	Polygon testPolygon;
    
    public Hero(Vec2 position, Vec2 size, String name) {
        super(position, size, name, Sprite.MEGAMAN);
        tileSpawner = new TileSpawner(this);
        state = NORMAL;
		
		aabb = new AABB();
		onGroundCallback = new onGroundCallback();
		// Shape		
		Vec2 testSize = new Vec2(0.4f*Consts.PTM_SCALE, 
						         0.4f*Consts.PTM_SCALE);
        float square[] = { -testSize.x, -testSize.y,
                            testSize.x, -testSize.y,
                            testSize.x,  testSize.y,
                           -testSize.x,  testSize.y};

        testPolygon = new Polygon(new float[]{ square[0], square[1],
                                           square[2], square[3],
                                           square[4], square[5],
                                           square[6], square[7] });		
    }
	
	public Hero(Vec2 position, String name) {
        this(position, 
		     new Vec2(Sprite.MEGAMAN.getWidth(), Sprite.MEGAMAN.getHeight()), 
			 name);
    }
    
    public void update(final int delta) {

		
		
//		System.out.println(isOnGround());
        switch (state) {
            case NORMAL: {
                                        										
                jump(delta);
								
                move();
                
				super.update(delta);
				
				testPolygon.setX(getWorldPosition().x + size.x/4);
				testPolygon.setY(600 - (getWorldPosition().y - ON_GROUND_OFFSET*Consts.PTM_SCALE));
				
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
//			System.out.println("PRESSED");
        } else if (isJumping && GameplayState.getContainer().getInput().isKeyDown(Input.KEY_SPACE) &&
	    		   jumpTimer < MAX_JUMP_TIMER) {
            body.applyForce(new Vec2(000.0f, JUMP_FORCE_DOWN), body.getWorldCenter());
            jumpTimer += delta;
//			System.out.println("DOWN");
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
        
        if ( state != SPAWN && GameplayState.getContainer().getInput().
                               isKeyPressed(Button.SPAWN_CIRCLE.getButton())) {
            
            // Not sure why, tileSpawner.update will use the previously 
            // cached key here, so i clear it
            GameplayState.getContainer().getInput().clearKeyPressedRecord();

            EntityManager.addPost(tileSpawner, true, false);

            tileSpawner.spawnCircle();
            state = SPAWN;
        } else if (state == SPAWN && 
            GameplayState.getContainer().getInput().
								isKeyPressed(Button.SPAWN_CIRCLE.getButton())) {
            tileSpawner.clear();
            state = NORMAL;
        }
    }
    
    public void unSpawn() {
        state = NORMAL;
    }
	
	public void render(Graphics g) {		
		sprite.draw(polygon.getX(), polygon.getY());
		
//		s = new StaticEntity(getWorldPosition(),
//				new Vec2(ON_GROUND_OFFSET*Consts.PTM_SCALE, ON_GROUND_OFFSET*Consts.PTM_SCALE), "TEST");
			
		g.draw(testPolygon);
	}
	
	
	public class onGroundCallback implements QueryCallback {

		public boolean reportFixture(Fixture fixture) {		
			
			GameplayState.getWorld().clearForces();
			AbstractPhysicsEntity entity = 
					(AbstractPhysicsEntity)(fixture.getBody().getUserData());
			
			System.out.println(entity.getName());		
			
			if (entity.getName().equals("HERO")) {
				isOnAir = true;
				
				System.out.println(600 - polygon.getMaxY());
				System.out.println(aabb.upperBound.y*Consts.PTM_SCALE);
				System.out.println(aabb.lowerBound.y*Consts.PTM_SCALE);
				System.out.println("=======");
				
////				System.out.println("HERO");
//				
//				System.out.println(body.getPosition().y - (size.y/2)/Consts.PTM_SCALE );
//				System.out.println(aabb.upperBound);
//				System.out.println(aabb.lowerBound);
			} 

			
			// terminate query		
			return true;
		}		
	} 
	
	public boolean isOnGround() {
		
		isOnAir = false;
		
		
//		aabb.lowerBound.set(body.getPosition().x - ON_GROUND_OFFSET,
//							(600 - polygon.getMaxY())/Consts.PTM_SCALE - 2*ON_GROUND_OFFSET);
//		aabb.upperBound.set(body.getPosition().x + ON_GROUND_OFFSET, 
//							(600 - polygon.getMaxY())/Consts.PTM_SCALE - ON_GROUND_OFFSET);
				
		
		aabb.lowerBound.set(testPolygon.getMinX()/Consts.PTM_SCALE,
							(600 - testPolygon.getMaxY())/Consts.PTM_SCALE);
		
		aabb.upperBound.set(testPolygon.getMaxX()/Consts.PTM_SCALE,
							(600 - testPolygon.getMinY())/Consts.PTM_SCALE);		
		
		
//		aabb.lowerBound.set(body.getPosition().x - ON_GROUND_OFFSET,
//							body.getPosition().y - Consts.PTM_SCALE/(size.y) - ON_GROUND_OFFSET;
//		aabb.upperBound.set(body.getPosition().x + ON_GROUND_OFFSET,
//							body.getPosition().y - Consts.PTM_SCALE/(size.y) - ON_GROUND_OFFSET);
		
//		System.out.println(body.getPosition().y*Consts.PTM_SCALE - Consts.PTM_SCALE*Consts.PTM_SCALE/(size.y));

//		PolygonShape shape = (PolygonShape)body.getFixtureList().m_shape;
//		for (Vec2 v: shape.getVertices()) {
//			System.out.println("v: " + v);
//		}		
//		
////		for (Vec2 v: shape.getVertices()) {
////			System.out.println("v: " + v);
////		}
//		System.out.println("=======");
//		System.out.println(aabb.getCenter());
//		
//		System.out.println("===== 0 =====");
				
		GameplayState.getWorld().queryAABB(onGroundCallback, aabb);		
		
		return !isOnAir;
	}	
            
}
