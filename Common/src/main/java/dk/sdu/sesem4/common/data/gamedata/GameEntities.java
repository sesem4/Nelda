package dk.sdu.sesem4.common.data.gamedata;

import dk.sdu.sesem4.common.data.entity.Entity;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class that represents the GameEntities.
* @author Muhammed and Anne Lærke
 */
public class GameEntities {
    // The list of entities.
    private List<Entity> entities;
    /**
     * The constructor for the GameEntities class, where the list of entities is initialized.
     */
    public GameEntities() {
        entities = new LinkedList<>();
    }
    /**
     * Add an entity to the list of entities.
     * @param entity The entity to be added.
     * @return boolean True if the entity was added, false otherwise.
     */
    public boolean addEntity(Entity entity) {
        return this.entities.add(entity);
    }

    /**
     * Remove an entity from the list of entities.
     * @param entity The entity to be removed.
     * @return boolean True if the entity was removed, false otherwise.
     */
    public boolean removeEntity(Entity entity) {
        return this.entities.remove(entity);
    }

    /**
     * Get a specific entity from the list of entities
     * @param entity The entity to be returned.
     * @return Entity The entity.
     */
    public Entity getEntity(Entity entity){
        return this.entities.stream()
                .filter(e -> e.equals(entity))
                .findFirst()
                .orElse(null);
    }
    /**
     * Get the list of all the entities by type.
     * @param entityTypes The type of the entities to be returned.
     * @return List<Entity> The list of entities.
     * @param <E> The type of the entities to be returned.
     */
    public <E extends Entity> List<Entity> getEntities(Class<E> entityTypes) {
        return this.entities.stream()
                .filter(entityTypes::isInstance)
                .collect(Collectors.toList());
    }
}
