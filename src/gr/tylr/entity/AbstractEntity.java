package gr.tylr.entity;

import gr.tylr.level.LevelManager;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;
import gr.tylr.util.Util;
import gr.tylr.state.GameplayState;
import gr.tylr.state.TyLRGame;

/**
 *
 * @author manji
 */
public abstract class AbstractEntity implements Entity {
    
    protected String name;    
    protected Polygon polygon;
    protected Vec2 size;
    protected Image sprite = null;
    
    public AbstractEntity(Vec2 position, 
                          Vec2 size,                          
                          String name) {
        
        this.size = size;
        this.name = name;
            
        // Shape
        float square[] = { -size.x/2, -size.y/2,
                            size.x/2, -size.y/2,
                            size.x/2,  size.y/2,
                           -size.x/2,  size.y/2};

        polygon = new Polygon(new float[]{ square[0], square[1],
                                           square[2], square[3],
                                           square[4], square[5],
                                           square[6], square[7] });
    }
    
    public AbstractEntity(Vec2 position,                                                     
                          String name,
                          Image sprite) {
        this(position, 
             new Vec2(sprite.getWidth(),
                      sprite.getHeight()), 
             name, sprite);
    }
    
    public AbstractEntity(Vec2 position, 
                          Vec2 size,                          
                          String name,
                          Image sprite) {
        this(position, size, name);
        this.sprite = sprite;
    }
    
    public AbstractEntity(String name) {
        this.name = name;
    }
    
    // TODO This will be rendered only if put in rendable list, maybe a sanity
    // checks is needed
    public void render(Graphics graphics) {        
        
        if (sprite != null) {            
            sprite.draw(polygon.getX(), polygon.getY());
        } else {
            
            graphics.draw(polygon);
        }
        
    }
    
    public abstract void update(int delta);

    public String getName() {
        return name;
    }
    
    public Polygon getPolygon() {
        return polygon;
    }
    
    public Vec2 getMapPosition() {
        return Util.getMapPosition(getWorldPosition());
    }
    
    public Image getSprite() {
        return sprite;
    }
    
    public Vec2 getPolygonPosition() {
        return new Vec2(polygon.getX(), polygon.getY());
    }

    public Vec2 getWorldPosition() {
        return new Vec2(polygon.getX(), 
                        TyLRGame.getWindowHeight() - polygon.getY() - size.y);
    }
    
    public void setPosition(Vec2 position) {
        polygon.setX(position.x);
        polygon.setY(TyLRGame.getWindowHeight() - position.y - LevelManager.getTileHeight());
    }
}
