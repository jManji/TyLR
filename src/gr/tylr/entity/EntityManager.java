package gr.tylr.entity;

import gr.tylr.state.GameplayState;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author manji
 */
public class EntityManager {

    private static LinkedHashMap<String, Entity> updatableEntities;
    private static LinkedHashMap<String, Entity> rendableEntities;
    private static LinkedHashMap<String, Entity> postUpdatableEntities;
    private static LinkedHashMap<String, Entity> postRendableEntities;
    private static List<Entity> removedEntities = null;
    
    public EntityManager() {
        updatableEntities = new LinkedHashMap<String, Entity>();
        rendableEntities = new LinkedHashMap<String, Entity>();
        postUpdatableEntities = new LinkedHashMap<String, Entity>();
        postRendableEntities = new LinkedHashMap<String, Entity>();
        removedEntities = new LinkedList<Entity>();
    }
    
    public static void add(Entity entity, boolean isUpdated, boolean isRendered) {
        if (isUpdated) {
            updatableEntities.put(entity.getName(), entity);
        }
        
        if (isRendered) {
            rendableEntities.put(entity.getName(), entity);
        }
    }    
    
    public static void addPost(Entity entity, boolean isUpdated, boolean isRendered) {
        if (isUpdated) {
            postUpdatableEntities.put(entity.getName(), entity);
        }
        
        if (isRendered) {
            postRendableEntities.put(entity.getName(), entity);
        }        
    }
    
    public static LinkedHashMap<String, Entity> getUpdatableEntities() {
        return updatableEntities;
    }
    
    public static LinkedHashMap<String, Entity> getRendableEntities() {
        return rendableEntities;
    }
        
    public static void postUpdate() {
        
        // Remove
        if (!removedEntities.isEmpty()) {
            for (Entity dyingEntity : removedEntities) {
                if (updatableEntities.containsKey(dyingEntity.getName())) {
                    updatableEntities.remove(dyingEntity.getName());
                }
				if (rendableEntities.containsKey(dyingEntity.getName())) {
                    rendableEntities.remove(dyingEntity.getName());
                }
				
				// Remove from box2d world, if needed (Depending on garbage
				// collector is not a good idea in Java, so we destroy the body
				// here explicitely
				if (AbstractPhysicsEntity.class.isAssignableFrom(
														dyingEntity.getClass())) {										
					
					GameplayState.getWorld().destroyBody(
							((AbstractPhysicsEntity)dyingEntity).getBody());
				}
            }
									
            // Take control of the object here
            removedEntities.clear();            
        }
        
        // Add
        if (!postUpdatableEntities.isEmpty()) {
            updatableEntities.putAll(postUpdatableEntities);            
            postUpdatableEntities.clear();
        }
        
        if (!postRendableEntities.isEmpty()) {
            rendableEntities.putAll(postRendableEntities);
            postRendableEntities.clear();
        }
                
    }
    
    public static void remove(List<Entity> removedEntities) {
        EntityManager.removedEntities.addAll(removedEntities);
    }
    
	// TODO only one of the remove methods makes sense
    public static void remove(Entity entity) {
		EntityManager.removedEntities.add(entity);		
    }

}
