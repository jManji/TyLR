package gr.tylr.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.tiled.TiledMap;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import gr.tylr.resource.ResourceManager;
import gr.tylr.entity.Entity;
import gr.tylr.entity.Hero;
import gr.tylr.entity.AbstractPhysicsEntity;
import gr.tylr.entity.EntityManager;
import gr.tylr.level.LevelManager;
import gr.tylr.util.Util;
import static gr.tylr.util.Consts.PTM_SCALE;

/**
 *
 * @author manji
 */
public class GameplayState extends BasicGameState {

    TiledMap map;

    Polygon polygon;
    Polygon polygonGround;
    private static World world;
    private static GameContainer container;
    private EntityManager entityManager;
    private LevelManager levelManager;

    Body body;
    Body groundBody;

    int velocityIterations = 6;
    int positionIterations = 2;

    AbstractPhysicsEntity hero;
    private Vec2 cameraPosition;
    ResourceManager resourceManager;

    @Override
    public int getID() {
        return 0;
    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        this.container = container;
        cameraPosition =  new Vec2(0.0f, 0.0f);
        
        resourceManager = new ResourceManager();
        entityManager = new EntityManager();
        levelManager = new LevelManager();
        
               
//        map = new TiledMap("/data/levels/map04_48.tmx");
        Vec2 gravity = new Vec2(0.0f, -20.0f);
        boolean doSleep = true;
        world = new World(gravity, doSleep);
//        hero = new Hero(new Vec2(2.1f*PTM_SCALE, 4.1f*PTM_SCALE),
        
        hero = new Hero(new Vec2(50,53),
                        new Vec2(30, 60), "HERO");
        
        
//        System.out.println(hero.getPolygonPosition());
//        System.out.println(hero.getWorldPosition());
//        System.out.println("---");
        
        EntityManager.add(hero, true, true);

        levelManager.loadCurrentLevel();
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
//        g.scale(0.8f, 0.8f);
        cameraPosition.set(Util.clamp(-hero.getPolygon().getX() + container.getWidth()/2, 
                                      -100.0f, 0.0f), 0.0f);
        g.translate(cameraPosition.x, cameraPosition.y);
        
        
        LevelManager.getCurrentLevel().render();
        for (Entity entity: EntityManager.getRendableEntities().values()) {
            entity.render(g);
        }
        
    }

    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        
        world.step(delta/1000.0f, velocityIterations, positionIterations);
       
        for (Entity entity: EntityManager.getUpdatableEntities().values()) {
            entity.update(delta);            
//            System.out.println(entity.getMapPosition());
        }
        EntityManager.postUpdate();
    }

    public static World getWorld() {
        return world;        
    }

    public static GameContainer getContainer() {
        return container;
    }
}
