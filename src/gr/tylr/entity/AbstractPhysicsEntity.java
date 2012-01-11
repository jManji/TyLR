package gr.tylr.entity;

import gr.tylr.state.GameplayState;
import gr.tylr.util.Consts;
import gr.tylr.util.Consts.BodyShape;
import gr.tylr.util.Consts.CollisionFilter;
import static gr.tylr.util.Consts.PTM_SCALE;
import gr.tylr.util.FilterData;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.newdawn.slick.Image;

/**
 *
 * @author manji
 */
        /************
         * Graphics *
         ************/
public abstract class AbstractPhysicsEntity extends AbstractEntity {

    private BodyDef bodyDef;	
    private FixtureDef fixtureDef; 	
	private Shape box2dShape;

    public AbstractPhysicsEntity(final Vec2 position, 
                                 final Vec2 size,
								 final boolean isDynamic,
								 final BodyShape bodyShape,
                                 final String name) {        
        super(position, size, bodyShape, name);
		init(position, isDynamic, bodyShape);
		this.bodyShape = bodyShape;
    }

    public AbstractPhysicsEntity(final Vec2 position,                                 
								 final Image sprite,
								 final boolean isDynamic,
								 final BodyShape bodyShape,
                                 final String name) {
        super(position, bodyShape, sprite, name);
		init(position, isDynamic, bodyShape);
		this.bodyShape = bodyShape;
    }

	public AbstractPhysicsEntity(String name) {
		super(name);
	}
	
	protected void initBody(boolean isDynamic, Vec2 position) {		
        bodyDef = new BodyDef();
		if (name.equals("HERO")) {
			bodyDef.fixedRotation = true;
		} 
        bodyDef.type = isDynamic == true? BodyType.DYNAMIC : BodyType.STATIC;
		
        bodyDef.position.set((position.x + size.x/2)/PTM_SCALE, 
                             (position.y + size.y/2)/PTM_SCALE);
        body = GameplayState.getWorld().createBody(bodyDef);
		body.m_userData = this;
	}
	
	protected void addDefaultFixture() {
		// Shape
        box2dShape = new PolygonShape();
        ((PolygonShape)box2dShape).setAsBox((size.x/2)/PTM_SCALE, 
					                 (size.y/2)/PTM_SCALE);

        // Fixture
        fixtureDef = new FixtureDef();
        fixtureDef.shape = box2dShape;
        fixtureDef.density = 0f;
		fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.0f;
        						
        body.createFixture(fixtureDef);
	}
	
	protected void updatePosition() {
		slick2dShape.setX((body.getPosition().x*PTM_SCALE - size.x/2));
        slick2dShape.setY(GameplayState.getContainer().getHeight() -
                          body.getPosition().y*PTM_SCALE - size.y/2);
	}
	
	public Body getBody() {
		return body;
	}

	protected void init(final Vec2 position, 
			            final boolean isDynamic, 
		                final BodyShape bodyShape) {
		
		/***********
         * PHYSICS *
         ***********/
        
		initBody(isDynamic, position);

		// Available vertices
		/*
		 * D_________C
		 * |        |
		 * |        |
		 * |        |
		 * ----------
		 * A		B
		 *
		 */

		box2dShape = new PolygonShape();		
		switch (bodyShape) {
			case TRIANGLE_0 : {
				Vec2[] triangle = new Vec2[] {new Vec2(coords[0]/PTM_SCALE, 
													   coords[1]/PTM_SCALE),
						                      new Vec2(coords[2]/PTM_SCALE,
						                               coords[3]/PTM_SCALE),
									          new Vec2(coords[4]/PTM_SCALE,
						                               coords[5]/PTM_SCALE)};
				((PolygonShape)box2dShape).set(triangle, 3);
				break;
			}
			case TRIANGLE_1 : {
				Vec2[] triangle = new Vec2[] {new Vec2(coords[0]/PTM_SCALE,
						                               coords[1]/PTM_SCALE),
									          new Vec2(coords[2]/PTM_SCALE,
						                               coords[3]/PTM_SCALE),
									          new Vec2(coords[6]/PTM_SCALE,
						                               coords[7]/PTM_SCALE)};
				((PolygonShape)box2dShape).set(triangle, 3);
				break;
			}
			case SQUARE : {
				box2dShape = new PolygonShape();
				((PolygonShape)box2dShape).setAsBox((size.x/2)/PTM_SCALE, 
 					    	                        (size.y/2)/PTM_SCALE);
				break;
			}
			case CIRCLE : {
				box2dShape = new CircleShape();
				box2dShape.m_radius = (size.y/2)/PTM_SCALE;
				break;
			}
		}        										

        // Fixture
        fixtureDef = new FixtureDef();
        fixtureDef.shape = box2dShape;
        fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.0f;
//		fixtureDef.filter.categoryBits = 0x0001;
		fixtureDef.filter.categoryBits = CollisionFilter.TILE_BITS.getFilter();
//		fixtureDef.filter.groupIndex = -1;
		fixtureDef.filter.maskBits = CollisionFilter.ALL.getFilter();
//		fixtureDef.filter.maskBits = CollisionFilter.ALL.getFilter();
		if (name.contains("BULLET")) {
			fixtureDef.restitution = 0.7f;
			fixtureDef.friction = 0.0f;
		}
        
		if (name.contains("DUMMY")) {
			fixtureDef.restitution = 0.0f;
			fixtureDef.friction = 0.0f;
			fixtureDef.density = 0.0f;
            bodyDef.fixedRotation = true;
		}
		
		if (name.contains("SPAWNED")) {
			fixtureDef.restitution = 0.0f;
			fixtureDef.friction = 0.8f;
			fixtureDef.density = 100.0f;
			bodyDef.fixedRotation = true;
		}
		
		if (name.contains("MOVING_TILE")) {
			fixtureDef.restitution = 0.0f;
			fixtureDef.friction = 0.8f;
			fixtureDef.density = 1.0f;
//			bodyDef.fixedRotation = true;
            fixtureDef.isSensor = true;
		}
		
        
        
		if (name.contains("SENSOR")) {		
			fixtureDef.isSensor = true;
//			fixtureDef.filter.maskBits = CollisionFilter.TILE_BITS.getFilter();
		}
		
		if (name.equals("HERO")) {
			fixtureDef.friction = 0.3f;
			fixtureDef.density = 0.0f;
//			fixtureDef.restitution = 10.0f;
			bodyDef.fixedRotation = true;
//			bodyDef.linearDamping = 2.1f;
//			fixtureDef.filter.categoryBits = 0x0003;
			
//			fixtureDef.filter.categoryBits = CollisionFilter.HERO_BITS.getFilter();
//			fixtureDef.filter.maskBits = CollisionFilter.ALL.getFilter();
		}				
		
        body.createFixture(fixtureDef);		
		
//		if (name.equals("HERO")) {
//			setFixtureDef(new FilterData(CollisionFilter.HERO_BITS, 
//									     CollisionFilter.ALL));
//		}
		
        /************
         * Graphics *
         ************/
        
        // Initialise position
        updatePosition();
	}
	
	public BodyShape getBodyShape() {
		return bodyShape;
	}
	
	public Shape getBox2DShape() {
		return box2dShape;
	}
	
	public void setFixtureDef(FilterData filterData) {
		Filter filter = new Filter();
		filter.categoryBits = filterData.categoryBits.getFilter();
		filter.maskBits = filterData.maskBits.getFilter();
		
		for (Fixture fixture = body.getFixtureList();
			 fixture != null; 
			 fixture = fixture.getNext()) {
			fixture.setFilterData(filter);
		}
		
	}
}
