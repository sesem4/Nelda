package dk.sdu.sesem4.common.data.entity.EntityParts;

import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestLifePart {
	LifePart lifePart;
	@Before
	public void createLifePart() {
		this.lifePart = new LifePart(3);
	}

	// Get/Set Life
	@Test
	public void testGetSetLife() {
		int lifeToSet = 705;
		lifePart.setLife(lifeToSet);
		assertEquals(lifeToSet, lifePart.getLife());
	}
	@Test
	public void testGetSetLifeTwice() {
		int lifeToSet = 705;
		lifePart.setLife(lifeToSet);
		lifePart.setLife(5);
		assertNotEquals(lifeToSet, lifePart.getLife());
	}

	// isAlive()
	@Test
	public void testIsAliveWhenHealthIsPositive() {
		lifePart.setLife(1);
		assertTrue(lifePart.isAlive());
	}
	@Test
	public void testIsAliveWhenHealthIsNegative() {
		lifePart.setLife(-1);
		assertFalse(lifePart.isAlive());
	}
	@Test
	public void testIsAliveWhenHealthIsZero() {
		lifePart.setLife(0);
		assertFalse(lifePart.isAlive());
	}

	// isDead()
	@Test
	public void testIsDeadWhenHealthIsPositive() {
		lifePart.setLife(1);
		assertFalse(lifePart.isDead());
	}
	@Test
	public void testIsDeadWhenHealthIsNegative() {
		lifePart.setLife(-1);
		assertTrue(lifePart.isDead());
	}
	@Test
	public void testIsDeadWhenHealthIsZero() {
		lifePart.setLife(0);
		assertTrue(lifePart.isDead());
	}

	// isDead()
	@Test
	public void testIsDeadNeverEqualToIsAlive() {
		lifePart.setLife(1);
		assertNotEquals(lifePart.isDead(), lifePart.isAlive());
		lifePart.setLife(-1);
		assertNotEquals(lifePart.isDead(), lifePart.isAlive());
		lifePart.setLife(0);
		assertNotEquals(lifePart.isDead(), lifePart.isAlive());
	}

	// kill()
	@Test
	public void testKill() {
		lifePart.setLife(3);
		assertTrue(lifePart.isAlive());

		lifePart.kill();
		assertTrue(lifePart.isDead());
	}
	
	@Test
	public void testKillForMaxStartingLife() {
		lifePart.setLife(Integer.MAX_VALUE);
		assertTrue(lifePart.isAlive());

		lifePart.kill();
		assertTrue(lifePart.isDead());
	}

	// doDamage()
	@Test
	public void testDoDamage() {
		int life = 3;
		lifePart.setLife(life);
		int damage = 1;
		lifePart.doDamage(damage);
		assertEquals(lifePart.getLife(), life - damage);
	}

	@Test
	public void testNegativeDamage() {
		int life = 3;
		lifePart.setLife(life);
		int healing = 1;
		lifePart.doDamage(-healing);
		assertEquals(lifePart.getLife(), life + healing);
	}
}
