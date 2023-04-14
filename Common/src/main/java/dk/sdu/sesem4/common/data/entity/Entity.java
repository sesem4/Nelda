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
 * @author Muhammed and Anne Lærke
 */
public abstract class Entity {
    private final Map<Class,EntityPart> entityParts = new HashMap<>();
    private final Map<Class, CollisionPart> collisionParts = new HashMap<>();
    private static final int width = 16;
    private static final int height = 16;
    private EntityType entityType;

//   private Texture texture;

//
//    public Texture getTexture() {
//        return texture;
//    }
//
//    public void setTexture(Texture texture) {
//        this.texture = texture;
//    }

    /**
     * Updates the gameData and gameEntities continuously
     * @param gameData
     * @param gameEntities
     */
    public void process(GameData gameData, GameEntities gameEntities) {

    }

    /**
     * Add a new EntityPart to the entity.
     * @param part
     */
    public void addEntityPart(EntityPart part){
        entityParts.put(part.getClass(),part);
    }

    /**
     * Remove an EntityPart from the entity.
     * @param partClass
     */
    public void removeEntityPart(Class partClass){
        entityParts.remove(partClass);
    }

    /**
     * Get a specific part based on the given ClassPart in the parameter
     * @param partClass
     * @return Class
     */
    public <E extends EntityPart>E getEntityPart(Class partClass) {
        return (E)entityParts.get(partClass);
    }


    public void addCollisionPart(CollisionPart part){
        collisionParts.put(part.getClass(),part);
    }
    public void removeCollisionPart(Class partClass){
        collisionParts.remove(partClass);
    }
    public <E extends CollisionPart>E getCollisionPart(Class partClass) {
        return (E)collisionParts.get(partClass);
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

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
