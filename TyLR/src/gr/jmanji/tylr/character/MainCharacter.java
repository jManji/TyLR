package gr.jmanji.tylr.character;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import gr.jmanji.tylr.Collidable;
import gr.jmanji.tylr.entity.Entity;
import gr.jmanji.tylr.util.Util;

public class MainCharacter extends Entity implements Collidable {
	
	private final static float ACCEL = 0.02f; 
	private final static float MAX_SPEED = 0.4f;
	private final static float FRICTION = 0.5f;
	private float speed = 0.0f;
	
	public MainCharacter(Image sprite) {
		this.sprite = sprite;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		//move(container, delta);
		
//	    const float JUMP_STRENGTH = 65; 
//	    const float GRAVITY = -3; 
//	 
//	    if (onFloor() && Input.Pressed(Jump)) 
//	        speed.y = JUMP_STRENGTH; 
//	    else 
//	        speed.y += GRAVITY; 
//	 
//	    position.y += speed.y; 
		
	}
	
	private void move(GameContainer container, int delta) {
		// X axis
		if (container.getInput().isKeyDown(Input.KEY_D)) {
			speed += ACCEL;
		}
		else if (container.getInput().isKeyDown(Input.KEY_A)) {
			speed -= ACCEL;
		}
		else {
			speed *= FRICTION;
		}
		
		speed = Util.clamp(speed, -MAX_SPEED, MAX_SPEED);
		position.x += speed * delta;
	}
	
	public boolean isCollidingWith(Collidable collidable) {
		return false;
	}
}
