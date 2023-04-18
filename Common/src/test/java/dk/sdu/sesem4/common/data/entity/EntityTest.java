package dk.sdu.sesem4.common.data.entity;

import dk.sdu.sesem4.common.data.CollisionParts.DamagePart;
import dk.sdu.sesem4.common.data.CollisionParts.Knockback;
import dk.sdu.sesem4.common.data.CollisionParts.KnockbackPart;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import static org.junit.Assert.*;

import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.classes.TestEntity;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;
import org.junit.Before;
import org.junit.Test;

public class EntityTest {

	private TestEntity entity;
	private LifePart lifePart;
	private DamagePart damagePart;
	private PositionPart positionPart;
	private MovingPart movingPart;
	private KnockbackPart knockbackPart;

	private TestEntity otherEntity;
	private LifePart otherLifePart;
	private DamagePart otherDamagePart;
	private PositionPart otherPositionPart;
	private MovingPart otherMovingPart;
	private KnockbackPart otherKnockbackPart;

	/**
	 * Set up our TestingEntity with a LifePart and a DamagePart
	 */
	@Before
	public void setupEntity() {
		entity = new TestEntity(EntityType.Player);
		lifePart = new LifePart(3);
		positionPart = new PositionPart(new Vector2(0, 0), Direction.UP);
		movingPart = new MovingPart(5);
		damagePart = new DamagePart(1);
		knockbackPart = new KnockbackPart(30, 10);

		entity.addEntityPart(lifePart);
		entity.addEntityPart(positionPart);
		entity.addEntityPart(movingPart);
		entity.addCollisionPart(damagePart);
		entity.addCollisionPart(knockbackPart);
	}

	private void setupOtherEntity(EntityType entityType) {
		otherEntity = new TestEntity(entityType);
		otherLifePart = new LifePart(3);
		otherPositionPart = new PositionPart(new Vector2(0, 0), Direction.UP);
		otherMovingPart = new MovingPart(5);
		otherDamagePart = new DamagePart(1);
		otherKnockbackPart = new KnockbackPart(30, 10);

		otherEntity.addEntityPart(otherLifePart);
		otherEntity.addEntityPart(otherPositionPart);
		otherEntity.addEntityPart(otherMovingPart);
		otherEntity.addCollisionPart(otherDamagePart);
		otherEntity.addCollisionPart(otherKnockbackPart);
	}

	/**
	 * Test that the LifePart we added, is the same when we get it back
	 * This tests both Add- and Get- EntityPart
	 */
	@Test
	public void testGetEntityPart() {
		LifePart lifePartFromEntity = entity.getEntityPart(LifePart.class);
		assertEquals(lifePart, lifePartFromEntity);
	}

	/**
	 * Test that a LifePart is present at first, and null after removeEntityPart has been called.
	 */
	@Test
	public void testRemoveEntityPart() {
		assertNotNull(entity.getEntityPart(LifePart.class));
		entity.removeEntityPart(LifePart.class);
		assertNull(entity.getEntityPart(LifePart.class));
	}

	/**
	 * Test that the DamagePart we added, is the same when we get it back
	 * This tests both Add- and Get- CollisionPart
	 */
	@Test
	public void testGetCollisionPart() {
		DamagePart damagePartFromEntity = entity.getCollisionPart(DamagePart.class);
		assertEquals(damagePart, damagePartFromEntity);
	}

	/**
	 * Test that a DamagePart is present at first, and null after removeCollisionPart has been called.
	 */
	@Test
	public void testRemoveCollisionPart() {
		assertNotNull(entity.getCollisionPart(DamagePart.class));
		entity.removeCollisionPart(DamagePart.class);
		assertNull(entity.getCollisionPart(DamagePart.class));
	}

	/**
	 * Tests that when to entities collide, one Entity's DamagePart's damage value is given as damage to the other Entity's
	 * LifePart.
	 */
	@Test
	public void testCollidedDoesCorrectDamage() {
		TestEntity otherEntity = new TestEntity(EntityType.Enemy);
		DamagePart otherDamagePart = new DamagePart(1);
		otherEntity.addCollisionPart(otherDamagePart);
		lifePart = entity.getEntityPart(LifePart.class);

		int startingLife = lifePart.getLife();
		entity.collided(otherEntity);
		otherEntity.collided(entity);
		int lifeAfterCollision = lifePart.getLife();

		assertEquals(startingLife - otherDamagePart.getDamage(), lifeAfterCollision);
	}

	/**
	 * Tests that when to entities collide, one Entity's KnockbackPart knocks back the other Entity.
	 */
	@Test
	public void testCollidedDoesKnockback() {
		setupOtherEntity(EntityType.Enemy);
		entity.collided(otherEntity);
		otherEntity.collided(entity);

		MovingPart movingPart = entity.getEntityPart(MovingPart.class);

		assertTrue(movingPart.isKnockedBack());
	}

	/**
	 * Tests that when a Player entity and an Enemy entity with a KnockbackPart collides, the Player entity is knocked back,
	 * away from the Enemy entity.
	 */
	@Test
	public void testCollidedDoesKnockbackWithCorrectDirectionWithTypeEnemy() {
		setupOtherEntity(EntityType.Enemy);
		entity.collided(otherEntity);
		otherEntity.collided(entity);

		Knockback expectedKnockback = new Knockback(
				otherPositionPart.getPosition().getDirectionTo(positionPart.getPosition()),
				30,
				10
		);

		assertEquals(expectedKnockback, movingPart.getKnockback());
	}

	/**
	 * Tests that when a Player entity collides with an EnemyProjectile entity with a KnockbackPart, the Player entity is
	 * knocked back in the Direction of the EnemyProjectile entity.
	 */
	@Test
	public void testCollidedDoesKnockbackWithCorrectDirectionWithTypeEnemyProjectile() {
		setupOtherEntity(EntityType.EnemyProjectile);
		entity.collided(otherEntity);
		otherEntity.collided(entity);

		Knockback expectedKnockback = new Knockback(
				otherPositionPart.getDirection(),
				30,
				10
		);

		assertEquals(expectedKnockback, movingPart.getKnockback());
	}
}
