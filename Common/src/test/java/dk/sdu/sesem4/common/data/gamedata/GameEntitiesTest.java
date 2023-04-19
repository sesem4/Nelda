package dk.sdu.sesem4.common.data.gamedata;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.entity.classes.TestEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * A Test Class for the GameEntities class.
 */
public class GameEntitiesTest {
    private GameEntities gameEntities;

    // Sets up the GameEntities object to be used in the tests.
    @Before
    public void setUp() {
        gameEntities = new GameEntities();
    }

    // Tests the addEntity method.
    @Test
    public void testAddEntity() {
        Entity entity = new TestEntity(EntityType.Player);
        gameEntities.addEntity(entity);
        assertEquals(1, gameEntities.getEntities(Entity.class).size());
    }
    // Tests the removeEntity method.
    @Test
    public void testRemoveEntity() {
        Entity entity = new TestEntity(EntityType.Player);
        gameEntities.addEntity(entity);
        gameEntities.removeEntity(entity);
        assertEquals(0, gameEntities.getEntities(Entity.class).size());
    }
    // Tests the getEntities method.
    @Test
    public void testGetEntitiesByType() {
        Entity entity = new TestEntity(EntityType.Player);
        Entity entity1 = new TestEntity(EntityType.Player);
        Entity entity2 = new TestEntity(EntityType.Player);
        gameEntities.addEntity(entity);
        gameEntities.addEntity(entity1);
        gameEntities.addEntity(entity2);
        List<Entity> entities = gameEntities.getEntities(Entity.class);
        assertEquals(3, entities.size());
        assertEquals(entities, gameEntities.getEntities(Entity.class));
    }
    // Tests if we can get a specific entity from the GameEntities object.
    @Test
    public void testGetASpecificEntity(){
        Entity entity = new TestEntity(EntityType.Player);
        gameEntities.addEntity(entity);
        assertEquals(entity, gameEntities.getEntity(entity));
    }

}