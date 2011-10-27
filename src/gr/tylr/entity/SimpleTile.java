package gr.tylr.entity;

import org.jbox2d.common.Vec2;

/**
 *
 * @author manji
 */
public class SimpleTile extends AbstractPhysicsEntity {

    public SimpleTile (Vec2 position, Vec2 size, String name) {
        super(position, size, true, name);
    }

    @Override
    public void update(int delta) {}
    
}
