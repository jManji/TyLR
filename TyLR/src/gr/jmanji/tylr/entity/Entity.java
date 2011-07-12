package gr.jmanji.tylr.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Entity {

	protected Image sprite;
	protected Vector2f position;
	
	public Entity() {
		position = new Vector2f();
	}
	
	public void render() {
		sprite.draw(position.x, position.y);
	}
	
	public Vector2f getPosition() {
		return position; 
	}
	
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		
	}

}