package gr.tylr.entity;

import org.newdawn.slick.Image;
import org.jbox2d.common.Vec2;
import gr.tylr.state.GameplayState;
import static gr.tylr.util.Consts.PTM_SCALE;

/**
 *
 * @author manji
 */
public class DynamicEntity extends AbstractPhysicsEntity {
    
    public DynamicEntity (Vec2 position, Vec2 size, String name) {
        super(position, size, true, name);       
    }

    public DynamicEntity (Vec2 position, Vec2 size, String name, Image sprite) {
        super(position, true, name, sprite);
    }
    
    @Override
    public void update(int delta) {

//        polygon.setX((body.getPosition().x*PTM_SCALE - size.x/2));
//        polygon.setY(GameplayState.getContainer().getHeight() - 
//                            body.getPosition().y*PTM_SCALE + size.y/2);
        polygon.setX(body.getPosition().x*PTM_SCALE - size.x/2);
        polygon.setY(GameplayState.getContainer().getHeight() -
                     (body.getPosition().y*PTM_SCALE + size.y/2));
        
    }
    
//    class CL implements ContactListener {
//
//        public void beginContact(Contact contact) {
////            throw new UnsupportedOperationException("Not supported yet.");
//            
//            System.out.println(contact.getFixtureA().getBody().getPosition().x + 
//                    " " + contact.getFixtureA().getBody().getPosition().y);
//        }
//
//        public void endContact(Contact contact) {
////            throw new UnsupportedOperationException("Not supported yet.");
//        }
//
//        public void preSolve(Contact contact, Manifold oldManifold) {
////            throw new UnsupportedOperationException("Not supported yet.");
//        }
//
//        public void postSolve(Contact contact, ContactImpulse impulse) {
////            throw new UnsupportedOperationException("Not supported yet.");
//        }
//        
//    }
}
