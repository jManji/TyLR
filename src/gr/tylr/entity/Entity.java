package gr.tylr.entity;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.geom.Polygon;

/**
 *
 * @author manji
 */
public class Entity {
    String name;
    Vec2 position;
    Body body;
    Polygon polygon;
    BodyDef bodyDef;
    PolygonShape dynamicBox;
    FixtureDef fixtureDef;
    
    public Entity(World world, Vec2 position, Vec2 size) {

        // Dynamic
        bodyDef = new BodyDef();
//        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position.x, position.y);
        body = world.createBody(bodyDef);

        dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(size.x, size.y);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox; //?
//        fixtureDef.density = 1.0f;
        fixtureDef.friction = 7.3f;
//        fixtureDef.restitution = 100.3f;
        body.createFixture(fixtureDef);

        float square[] = { -size.x/2, -size.y/2,
                           size.x/2,  -size.y/2,
                           size.x/2,  size.y/2,
                           -size.x/2, size.y/2};

        polygon = new Polygon(new float[]{ square[0], square[1],
                                           square[2], square[3],
                                           square[4], square[5],
                                           square[6], square[7] });
    }

    public void render() {
//        polygon.
    }
}
