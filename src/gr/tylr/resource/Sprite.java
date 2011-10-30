package gr.tylr.resource;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author manji
 */
public class Sprite {

    public static Image INVALID_CANDIDATE_TILE;
    public static Image VALID_CANDIDATE_TILE;
    public static Image VALID_SELECTED_TILE;
    public static Image PLAIN_TILE;
    public static Image MEGAMAN;
    
    public static void load() throws SlickException{
        VALID_CANDIDATE_TILE = new Image("/data/candidate_block.png"); 
        INVALID_CANDIDATE_TILE = new Image("/data/invalid_block.png");
        VALID_SELECTED_TILE = new Image("/data/valid_selected_tile.png");
        PLAIN_TILE = new Image("/data/plain_tile.png");
        MEGAMAN = new Image("/data/megaman.png");
    }
}
