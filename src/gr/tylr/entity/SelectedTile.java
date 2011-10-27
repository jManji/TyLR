package gr.tylr.entity;

import org.jbox2d.common.Vec2;
import gr.tylr.resource.Sprite;

/**
 *
 * @author manji
 */
public class SelectedTile extends ImageEntity {    
    
    public SelectedTile(Vec2 position) {        
        super(position, "SELECTED_TILE", Sprite.VALID_SELECTED_TILE);
    }

    public void update(int delta) {}
    
}
