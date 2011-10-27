package gr.tylr.entity;

import org.newdawn.slick.Image;
import org.jbox2d.common.Vec2;

/**
 *
 * @author manji
 */
public class StaticEntity extends AbstractPhysicsEntity {
            
    public StaticEntity(Vec2 position, Vec2 size, String name) {
        super(position, size, false, name);
    }

    public StaticEntity(Vec2 position, String name, Image sprite) {
        super(position, false, name, sprite);
    }
    
    public StaticEntity(Vec2 position, Vec2 size, String name, Image sprite) {
        super(position, size, false, name, sprite);
    }

    @Override
    public void update(int delta) {}

}
