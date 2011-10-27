package gr.tylr.entity;

import org.newdawn.slick.Graphics;

/**
 *
 * @author manji
 */
public interface Entity {
    
    public void render(Graphics graphics);
    
    public void update(int delta);
    
    public String getName();

}