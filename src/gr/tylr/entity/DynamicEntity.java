package gr.tylr.entity;

import gr.tylr.state.GameplayState;
import static gr.tylr.util.Consts.PTM_SCALE;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Image;

/**
 *
 * @author manji
 */
public class DynamicEntity extends AbstractPhysicsEntity {
    
    public DynamicEntity (Vec2 position, Vec2 size, String name) {
        super(position, size, true, name);       
    }

    public DynamicEntity (Vec2 position, Vec2 size, String name, Image sprite) {
        super(position, size, true, name, sprite);
    }
    
    @Override
    public void update(int delta) {
        polygon.setX(body.getPosition().x*PTM_SCALE - size.x/2);
        polygon.setY(GameplayState.getContainer().getHeight() -
                     (body.getPosition().y*PTM_SCALE + size.y/2));
    }
}
