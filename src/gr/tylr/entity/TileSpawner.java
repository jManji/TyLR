package gr.tylr.entity;

import gr.tylr.level.LevelManager;
import gr.tylr.resource.Sprite;
import gr.tylr.state.GameplayState;
import gr.tylr.util.Consts.Button;
import gr.tylr.util.Consts.Direction;
import static gr.tylr.util.Consts.Direction.*;
import gr.tylr.util.Util;
import java.util.LinkedList;
import java.util.List;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Image;

/**
 *
 * @author manji
 */
public class TileSpawner extends AbstractEntity {
    
    private List<String> spawnedTiles;
    private ImageEntity selectedTile;
    private Vec2 center;
    private Hero hero;
    
    public TileSpawner (Hero hero) {
        super("TILE_SPAWNER");               
        this.hero = hero;
        spawnedTiles = new LinkedList<String>();
    }
    
    public void spawnCircle() {
        this.center = hero.getMapPosition();
        int range = hero.SPAWN_RANGE;
        
        for(int y=-range; y<=range; y++) {
            for(int x=-range; x<=range; x++) {
                if(x*x + y*y <= range*range + 1 &&
                   !(x ==0 && y==0)) {
                    if (LevelManager.isOccupied(center.x + x,
                                                center.y + y)) {
                        addToEntityManager(center, x, y, false);
                    } else {
                        addToEntityManager(center, x, y, true);
                    }
                }
            }
        }
        
        selectedTile = new ImageEntity(new Vec2(Util.mapToWorld(center.x + 1),                                       
                                                Util.mapToWorld(center.y +1)),
                                       "SELECTED_TILE",
                                       Sprite.VALID_SELECTED_TILE);
        EntityManager.addPost(selectedTile, false, true);
    }
    
    private void addToEntityManager(final Vec2 center,
                                    final float x,
                                    final float y,
                                    final boolean valid) {
        
        Image candidateSprite = valid ? Sprite.VALID_CANDIDATE_TILE :
                                                            Sprite.INVALID_CANDIDATE_TILE;


        
        String candidateName = "CANDIDATE_TILE_" +
                                                 (int)(center.x + x) + "_" +
                                                 (int)(center.y + y);                                

        EntityManager.addPost(new ImageEntity(new Vec2(
                                    Util.mapToWorld(center.x + x),
                                    Util.mapToWorld(center.y + y)),
                                    candidateName, candidateSprite), false, true);

        spawnedTiles.add(candidateName);
    }

    public void clear() {
        EntityManager.remove(spawnedTiles);
        EntityManager.remove(selectedTile);
        EntityManager.remove(this);
        // spawnedTiles.clear() is called from the postUpdate method of
        // the EntityManager
    }

    public void update(int delta) {
        
        moveTile();
        
        spawn();
                
    }
    
    private void moveTile() {
        Direction direction = Util.getDirectionFromInput();
        
        if (direction != NONE) {

            Vec2 newPosition = selectedTile.getMapPosition();

//            System.out.println("------------");
//            System.out.println(selectedTile.getWorldPosition());
//            System.out.println(newPosition);
//            System.out.println(Util.mapToWorld(newPosition.y));            
//            System.out.println("==");
            
            if (direction == LEFT) {
                newPosition.x -= 1;
            } else if (direction == RIGHT) {
                newPosition.x += 1;
            } else if (direction == UP) {
                newPosition.y += 1;
            } else if (direction == DOWN) {
                newPosition.y -= 1;
            }
            
            
//            for(int y=0; y<=10; y++) {
//                for(int x=0; x<=14; x++) {
//                    System.out.print(x + "," + y + ":" + LevelManager.getCurrentLevel().getMapOccupation()[x][y] + "  ");
//                }
//                System.out.println();
//            }
            
//            System.out.println(newPosition);
//            System.out.println(Util.mapToWorld(newPosition.y));
//            System.out.println(LevelManager.isOccupied((int)newPosition.x,
//                                                       (int)newPosition.y));
            // TODO could be done better here
            if (!newPosition.equals(center) &&
                EntityManager.getRendableEntities().
                              containsKey("CANDIDATE_TILE_" +
                              (int)newPosition.x + "_" + (int)newPosition.y) &&
                !LevelManager.isOccupied((int)newPosition.x,
                                         (int)newPosition.y)) {

                selectedTile.setPosition(new Vec2(
                                               Util.mapToWorld(newPosition.x), 
                                               Util.mapToWorld(newPosition.y)));
            }
        }
    }

	private void spawn() {
		
        if (GameplayState.getContainer().getInput().
                                        isKeyPressed(Button.SPAWN.getButton())) {
			
            EntityManager.addPost(new StaticEntity(
                                            selectedTile.getWorldPosition(),
                                            "SPAWNED_TILE" + Util.generateID(),
                                            Sprite.PLAIN_TILE),
                                            false, true);
            clear();
            
            hero.unSpawn();
        }
    }

}
