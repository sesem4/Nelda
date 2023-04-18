package dk.sdu.sesem4.common.data.gamedata;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.entity.classes.TestEntity;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameEntitiesTest {
   GameEntities gameEntities;

    @Before
    public void setUp() {
        gameEntities = new GameEntities();
    }
    @Test
    public void testAddEntity() {
        Entity entity = new TestEntity(EntityType.Player);
        gameEntities.addEntity(entity);
        assertEquals(1, gameEntities.getEntities(Entity.class).size());
    }
    @Test
    public void testRemoveEntity() {
        Entity entity = new TestEntity(EntityType.Player);
        gameEntities.addEntity(entity);
        gameEntities.removeEntity(entity);
        assertEquals(0, gameEntities.getEntities(Entity.class).size());
    }
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
    @Test
    public void testGetASpecificEntity(){
        Entity entity = new TestEntity(EntityType.Player);
        gameEntities.addEntity(entity);
        assertEquals(entity, gameEntities.getEntity(entity));
    }

}