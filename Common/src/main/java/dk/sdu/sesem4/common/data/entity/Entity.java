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
import java.util.Set;

/**
 * The Entity class for all entities in the game.
 */
public abstract class Entity {
	/**
	* A Map to store all the EntityParts with their Class as key.
	*/
	private final Map<Class, EntityPart> entityParts = new HashMap<>();

	/**
	* A Map to store all the CollisionParts with their Class as key.
	*/
	private final Map<Class, CollisionPart> collisionParts = new HashMap<>();

	/**
	* The type of the entity.
	*/
	private EntityType entityType;
	public Entity(EntityType entityType) {
		this.entityType = entityType;
	}

     /**
      * Process the entity.
      *
      * @param gameData     The GameData object.
      * @param gameEntities The GameEntities object.
      */
     public void process(GameData gameData, GameEntities gameEntities) {
     }

     /**
      * Add a new EntityPart to the entity.
      *
      * @param part the type of EntityPart to be added
      */
     public void addEntityPart(EntityPart part) {
          this.entityParts.put(part.getClass(), part);
     }

     /**
      * Remove an EntityPart from the entity.
      *
      * @param partClass the EntityPart to be removed
      */
     public void removeEntityPart(Class partClass) {
          this.entityParts.remove(partClass);
     }

     /**
      * Get a specific EntityPart from the given part class.
      *
      * @param partClass The class of the EntityPart object to be returned.
      * @param <E>       The type of the EntityPart object to be returned.
      * @return The EntityPart object.
      */
     public <E extends EntityPart> E getEntityPart(Class partClass) {
          return (E) this.entityParts.get(partClass);
     }

     /**
      * Add a CollisionPart to the Entity.
      *
      * @param part the type of CollisionPart to be added
      */
     public void addCollisionPart(CollisionPart part) {
          this.collisionParts.put(part.getClass(), part);
     }

     /**
      * Remove a CollisionPart from the Entity.
      *
      * @param partClass the CollisionPart to be removed
      */
     public void removeCollisionPart(Class partClass) {
          this.collisionParts.remove(partClass);
     }

     /**
      * Get a specific CollisionPart based on the given ClassPart.
      *
      * @param partClass The class of the CollisionPart object to be returned.
      * @param <E>       The type of the CollisionPart object to be returned.
      * @return The CollisionPart object.
      */
     public <E extends CollisionPart> E getCollisionPart(Class partClass) {
          return (E) this.collisionParts.get(partClass);
     }

     /**
      * Returns the type of the entity.
      *
      * @return The type of the entity.
      */
     public EntityType getEntityType() {
          return this.entityType;
     }

     /**
      * Sets the type of the entity.
      *
      * @param entityType The type of the entity.
      */
     public void setEntityType(EntityType entityType) {
          this.entityType = entityType;
     }

	/**
	* Emit to entity that it has collided with another entity.
	*
	* @param other The other Entity which this entity has collided with.
	*/
	public void collided(Entity other) {
		HashMap<EntityType, Set<EntityType>> entityTypeCollisions = new HashMap<>() {{
			put(EntityType.Player,              Set.of(EntityType.Enemy, EntityType.EnemyProjectile, EntityType.Item));
			put(EntityType.Enemy,               Set.of(EntityType.PlayerProjectile));
			put(EntityType.PlayerProjectile,    Set.of(EntityType.Enemy));
			put(EntityType.EnemyProjectile,     Set.of(EntityType.Player));
			put(EntityType.Item,                Set.of(EntityType.Player));
		}};

		Set<EntityType> stuffWeShouldCollideWith = entityTypeCollisions.get(this.getEntityType());
		if (!stuffWeShouldCollideWith.contains(other.getEntityType()))
			return;

		doDamage(other);
		doKnockback(other);
	}

     /**
      * Do damage according to other's DamagePart
      *
      * @param other the Entity we're colliding with
      */
     private void doDamage(Entity other) {
          // Get the Life- and DamageParts
          LifePart ourLifePart = this.getEntityPart(LifePart.class);
          DamagePart othersDamagePart = other.getCollisionPart(DamagePart.class);

          // Null checks
          if (ourLifePart == null)
               return;
          if (othersDamagePart == null)
               return;

          // Apply the damage value of other to this Entity's LifePart
          ourLifePart.doDamage(othersDamagePart.getDamage());
     }

     /**
      * Do knock-back according to other's knockbackPart and Entity type, and type of
      * collision
      *
      * @param other the Entity we are colliding with
      */
     private void doKnockback(Entity other) {
          // Get the relevant EntityParts
          KnockbackPart othersKnockbackPart = other.getCollisionPart(KnockbackPart.class);
          PositionPart othersPositionPart = other.getEntityPart(PositionPart.class);
          MovingPart ourMovingPart = this.getEntityPart(MovingPart.class);

          // Null checks
          if (othersKnockbackPart == null)
               return;
          if (othersPositionPart == null)
               return;
          if (ourMovingPart == null)
               return;

          // Get the EntityType of other
          EntityType otherEntityType = other.getEntityType();

          Direction knockbackDirection;

          // [PROJECTILE COLLISION]
          // If other is a type of Projectile entity, we should be knocked back in the
          // direction the projectile flew
          if (otherEntityType == EntityType.PlayerProjectile || otherEntityType == EntityType.EnemyProjectile) {
               knockbackDirection = othersPositionPart.getDirection();
          }
          // [BUMP COLLISION]
          // Adversely, if other is simply a regular Enemy entity, we should be knocked
          // back, directly away from other
          else {
               PositionPart ourPositionPart = this.getEntityPart(PositionPart.class);
               if (ourPositionPart == null)
                    return;
               knockbackDirection = othersPositionPart.getPosition().getDirectionTo(ourPositionPart.getPosition());
          }

          // Create Knockback data according to the generated direction and the
          // KnockbackPart
          Knockback knockback = new Knockback(knockbackDirection, othersKnockbackPart.getDuration(), othersKnockbackPart.getSpeed());

          // Set the Knockback
          ourMovingPart.setKnockback(knockback);
     }
}
