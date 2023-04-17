package dk.sdu.sesem4.common.data.entity;

import dk.sdu.sesem4.common.data.CollisionParts.CollisionPart;
import dk.sdu.sesem4.common.data.CollisionParts.DamagePart;
import dk.sdu.sesem4.common.data.CollisionParts.Knockback;
import dk.sdu.sesem4.common.data.CollisionParts.KnockbackPart;
import dk.sdu.sesem4.common.data.EntityParts.*;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.util.Direction;

import java.util.HashMap;
import java.util.Map;


/**
 * The Entity class for all entities in the game.
 * @author Muhammed and Anne Lærke
 */
public abstract class Entity {
    // A Map to store all the EntityParts with their Class as key.
    private final Map<Class,EntityPart> entityParts = new HashMap<>();
    // A Map to store all the CollisionParts with their Class as key.
    private final Map<Class, CollisionPart> collisionParts = new HashMap<>();
    // Width and height of the entity.
    private static final int width = 16;
    private static final int height = 16;
    // The type of the entity.
    private EntityType entityType;

//   private Texture texture;


//    public Texture getTexture() {
//        return texture;
//    }

//    public void setTexture(Texture texture) {
//        this.texture = texture;
//    }

    /**
     * Process the entity.
     * @param gameData The GameData object.
     * @param gameEntities The GameEntities object.
     */
    public void process(GameData gameData, GameEntities gameEntities) {

    }

    /**
     * Adds an EntityPart object to the entityParts HashMap.
     *
     * @param part The EntityPart object to be added.
     */
    public void addEntityPart(EntityPart part){
        entityParts.put(part.getClass(),part);
    }

    /**
     * Removes an EntityPart object from the entityParts HashMap.
     * @param partClass The class of the EntityPart object to be removed.
     */
    public void removeEntityPart(Class partClass){
        entityParts.remove(partClass);
    }

    /**
     * Returns an EntityPart object from the entityParts HashMap.
     * @param partClass The class of the EntityPart object to be returned.
     * @param <E> The type of the EntityPart object to be returned.
     * @return The EntityPart object.
     */
    public <E extends EntityPart>E getEntityPart(Class partClass) {
        return (E)entityParts.get(partClass);
    }

    /**
     * Adds a CollisionPart object to the collisionParts HashMap.
     * @param part The CollisionPart object to be added.
     */
    public void addCollisionPart(CollisionPart part){
        collisionParts.put(part.getClass(),part);
    }

    /**
     * Removes a CollisionPart object from the collisionParts HashMap.
     * @param partClass The class of the CollisionPart object to be removed.
     */
    public void removeCollisionPart(Class partClass){
        collisionParts.remove(partClass);
    }

    /**
     * Returns a CollisionPart object from the collisionParts HashMap.
     * @param partClass The class of the CollisionPart object to be returned.
     * @param <E> The type of the CollisionPart object to be returned.
     * @return The CollisionPart object.
     */
    public <E extends CollisionPart>E getCollisionPart(Class partClass) {
        return (E)collisionParts.get(partClass);
    }

    /**
     * Returns the type of the entity.
     * @return The type of the entity.
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * Sets the type of the entity.
     * @param entityType The type of the entity.
     */
    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    /**
     * Checks if the entity is colliding with another entity.
     * @param other
     */
    public void collided(Entity other) {
        // only do this if we should collide with the entity:
        doDamage(other);
        doKnockback(other);
    }

    /**
     * Do damage according to other's DamagePart
     * @param other the Entity we're colliding with
     */
    private void doDamage(Entity other) {
        //TODO: add null-checks
        LifePart ourLifePart = this.getEntityPart(LifePart.class);
        DamagePart othersDamagePart = other.getCollisionPart(DamagePart.class);
        ourLifePart.doDamage(othersDamagePart.getDamage());
    }

    /**
     * Do knockback according to other's knockbackPart and maybe the positionParts
     * @param other the Entity we're colliding with
     */
    private void doKnockback(Entity other) {
        //TODO: add null-checks
        KnockbackPart othersKnockbackPart = other.getCollisionPart(KnockbackPart.class);
        PositionPart othersPositionPart = other.getEntityPart(PositionPart.class);

        Direction knockbackDirection;
        // if other is a projectile, we should be knocked back with its direction
        if (other.getEntityType() == EntityType.PlayerProjectile || other.getEntityType() == EntityType.EnemyProjectile) {
            knockbackDirection = othersPositionPart.getDirection();
        } else {
            // else, we should be knocked back directly away from other
            PositionPart ourPositionPart = this.getEntityPart(PositionPart.class);
            knockbackDirection = othersPositionPart.getPosition().getDirectionTo(ourPositionPart.getPosition());
        }

        // we create knockback data according to the generated direction and the knockbackPart
        Knockback knockback = new Knockback(knockbackDirection, othersKnockbackPart.getDuration(), othersKnockbackPart.getSpeed());

        // set the knockback
        MovingPart ourMovingPart = this.getEntityPart(MovingPart.class);
        ourMovingPart.setKnockback(knockback);
    }
}
