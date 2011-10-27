/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.tylr.entity;

import gr.tylr.level.LevelManager;
import gr.tylr.state.TyLRGame;
import org.jbox2d.common.Vec2;
import org.lwjgl.Sys;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author manji
 */
public class ImageEntity extends AbstractEntity {
   
    boolean isWarping;
    boolean mustWarp;
    private static final float MAX_WARP_SCALE = 2.5f;
    private static final float WARP_SPEED = 0.01f;
    private float warpScale = 0.0f;
   
    public ImageEntity(Vec2 position, String name, Image sprite) {
        super(position, name, sprite);
        
        // TODO I cheated here, as this has to be set explicitely for render 
        // to work. Polygon is never set for AbstractEntity
        setPosition(position);
//        polygon.setX(position.x);
//        polygon.setY(TyLRGame.getWindowHeight() - position.y - 
//                     LevelManager.getTileHeight());
//        warp();
    }
    
//    public void render(Graphics graphics) {
////        
////        if (isWarping) {
////            graphics.pushTransform();
////            sprite.setCenterOfRotation(sprite.getWidth()/2, sprite.getHeight()/2);
////            sprite.draw(position.x, position.y, warpScale);
////            
//////            plane.setCenterOfRotation(plane.getWidth()/2.0f*scale, plane.getHeight()/2.0f*scale);
//////            graphics.scale(warpScale, warpScale);
////        } else
//        
////        sprite.draw(position.x, position.y);
//        
////        if (isWarping) {
////            graphics.popTransform();
////        }
//        
//        
////        graphics.scale(-0.8f, -0.8f);
//    }    

    public void update(int delta) {
//        if (mustWarp) {            
//            if (!isWarping) {
//                isWarping = true;
//            } else {
//                warpScale += WARP_SPEED;
////                System.out.println(Sys.getTime() + " " + warpScale);
//                if (warpScale >= MAX_WARP_SCALE) {
//                    isWarping = false;
//                    mustWarp = false;                    
//                }
//            }
//        }
    }
    
    private void warp() {
        mustWarp = true;
    }
    
    
}
