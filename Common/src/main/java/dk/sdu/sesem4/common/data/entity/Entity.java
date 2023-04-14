package dk.sdu.sesem4.common.data.entity;

import dk.sdu.sesem4.common.data.CollisionParts.CollisionPart;
import dk.sdu.sesem4.common.data.CollisionParts.DamagePart;
import dk.sdu.sesem4.common.data.CollisionParts.Knockback;
import dk.sdu.sesem4.common.data.CollisionParts.KnockbackPart;
import dk.sdu.sesem4.common.data.EntityParts.*;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;

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
        // if should collide with the entity:
        LifePart lp = this.getEntityPart(LifePart.class);
        DamagePart dp = other.getCollisionPart(DamagePart.class);
        lp.doDamage(dp.getDamage());

        PositionPart thisPp = this.getEntityPart(PositionPart.class);
        PositionPart otherPp = other.getEntityPart(PositionPart.class);
        MovingPart mp = this.getEntityPart(MovingPart.class);
        KnockbackPart kp = other.getCollisionPart(KnockbackPart.class);
        Knockback knockback;
        if (other.getEntityType() == EntityType.PlayerProjectile || other.getEntityType() == EntityType.EnemyProjectile) {
            knockback = new Knockback(otherPp.getDirection(), kp.getDuration(), kp.getSpeed());
        } else {
            knockback = new Knockback(otherPp.getDirectionTo(thisPp.getPosition()), kp.getDuration(), kp.getSpeed());
        }
        mp.setKnockback(knockback);
    }
}
