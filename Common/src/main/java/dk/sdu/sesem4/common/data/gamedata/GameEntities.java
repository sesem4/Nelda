package dk.sdu.sesem4.common.data.gamedata;

import dk.sdu.sesem4.common.data.entity.Entity;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Muhammed and Anne Lærke
 */
public class GameEntities {
        private List<Entity> entities;

        public GameEntities(){
            entities = new LinkedList<>();
        }
        /**
        <p> Add an entity to the list of entities
         */
        public boolean addEntity(Entity entity){
            return this.entities.add(entity);
        }
        /**
        <p> Remove an entity from the list of entities
         */
        public boolean removeEntity(Entity entity){
            return this.entities.remove(entity);
        }
//        /**
//        <p> Get an specific entity from the list of entities
//         */
//        public Entity getEntity(Entity entity){
//            return this.entities.get();
//        }

    /**
     * Get all the entities of the type that is given in the parameter.
     * @param entityTypes
     * @return List
     * @param <E>
     */
    public <E extends Entity> List<Entity> getEntities(Class<E> entityTypes){
            return this.entities.stream()
                    .filter(entityTypes::isInstance)
                    .collect(Collectors.toList());
            
        }
}
