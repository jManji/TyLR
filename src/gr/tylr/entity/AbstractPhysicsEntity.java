package gr.tylr.entity;

import gr.tylr.state.GameplayState;
import static gr.tylr.util.Consts.PTM_SCALE;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.Image;

/**
 *
 * @author manji
 */
public abstract class AbstractPhysicsEntity extends AbstractEntity {
    
    protected Body body;    
    public BodyDef bodyDef;
    protected PolygonShape box;
    protected FixtureDef fixtureDef; 
    
    public AbstractPhysicsEntity(Vec2 position, 
                                 Vec2 size,
                                 boolean isDynamic,
                                 String name) {
        
        super(position, size, name);
        init(position, isDynamic);
    }
    
    public AbstractPhysicsEntity(Vec2 position, 
                                 Vec2 size,
                                 boolean isDynamic,
                                 String name,
                                 Image sprite) {
        super(position, size, name, sprite);
        init(position, isDynamic);
    }
    
    public AbstractPhysicsEntity(Vec2 position, 
                                 boolean isDynamic,
                                 String name,
                                 Image sprite) {
        super(position, name, sprite);
        init(position, isDynamic);
    }
    
    // TODO this will not be needed in the end, polygon is rendered
    // for debugging. Graphics parameter migh be redundant.
//    @Override
//    public void render(Graphics graphics) {
//        graphics.draw(polygon);
//    }

    private void init(Vec2 position, boolean isDynamic) {
        
        /***********
         * PHYSICS *
         ***********/
        
        // Body definition
        bodyDef = new BodyDef();        
        bodyDef.type = isDynamic == true? BodyType.DYNAMIC : BodyType.STATIC;
		
		if (name.equals("HERO")) {
			System.out.println("init phys: " + (position.y + size.y/2));
		}
		
        bodyDef.position.set((position.x + size.x/2)/PTM_SCALE, 
                             (position.y + size.y/2)/PTM_SCALE);
        body = GameplayState.getWorld().createBody(bodyDef);
		body.m_userData = this;

        // Shape
        box = new PolygonShape();
        box.setAsBox((size.x/2)/PTM_SCALE, 
                     (size.y/2)/PTM_SCALE);

        // Fixture
        fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
//        fixtureDef.density = 0.0f;
//        fixtureDef.friction = 0.5f;
//        fixtureDef.restitution = 0.0f;
        
        body.createFixture(fixtureDef);

		
        /************
         * Graphics *
         ************/                
        
        // Initialise position
        polygon.setX((body.getPosition().x*PTM_SCALE - size.x/2));
        polygon.setY(GameplayState.getContainer().getHeight() -
                            (body.getPosition().y*PTM_SCALE + size.y/2));
		if (this.getName().equals("HERO")) {
//			System.out.println("y:" + (
//                            (body.getPosition().y*PTM_SCALE -  size.y/2)));
			System.out.println("init pol: " + polygon.getY());
		}
    }
	
	public Body getBody() {
		return body;
	}
}
