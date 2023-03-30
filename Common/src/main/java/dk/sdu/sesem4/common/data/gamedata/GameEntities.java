package dk.sdu.sesem4.common.data.gamedata;

/**
* @author Muhammed and Anne Lærke
 */
public class GameEntities {
        private List<Entity> entitiees;
        /**
        <p> Add an entity to the list of entities
         */
        public boolean addEntity(Entity entity){
            return this.entitiees.add(entity);
        }
        /**
        <p> Remove an entity from the list of entities
         */
        public boolean removeEntity(Entity entity){
            return this.entitiees.remove(entity);
        }
        /**
        <p> Get an specfic entity from the list of entities
         */
        public Entity getEntity(Entity entity){
            return this.entitiees.get(entity);
        }
        /**
        <p> Get all the entities of the type that is given in the paramter.
         */
        public <E extends Entity> List<Entity> getEntities(Class<E> entityTypes){
            return this.entitiees.stream()
                    .filter(entityTypes::isInstance)
                    .collect(Collectors.toList());
            
        }
}
