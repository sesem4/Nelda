package dk.sdu.sesem4.common.data.entity.classes;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;

/**
 * A Test Class for the Entity class to be used in testing.
 */
public class TestEntity extends Entity {
    /**
     * Constructs a TestEntity
     * @param entityType the type of the entity to be created
     */
    public TestEntity(EntityType entityType) {
        super(entityType);
    }
}
