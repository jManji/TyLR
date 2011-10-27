package gr.tylr.entity;

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
    private static List<String> removedEntities = null;
    
    public EntityManager() {
        updatableEntities = new LinkedHashMap<String, Entity>();
        rendableEntities = new LinkedHashMap<String, Entity>();
        postUpdatableEntities = new LinkedHashMap<String, Entity>();
        postRendableEntities = new LinkedHashMap<String, Entity>();
        removedEntities = new LinkedList<String>();
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
            for (String name : removedEntities) {
                if (updatableEntities.containsKey(name)) {
                    updatableEntities.remove(name);
                } else if (rendableEntities.containsKey(name)) {
                    rendableEntities.remove(name);
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
    
    public static void remove(List<String> removedEntities) {
        EntityManager.removedEntities.addAll(removedEntities);
    }
    
    public static void remove(Entity entity) {
        EntityManager.removedEntities.add(entity.getName());        
    }

}
