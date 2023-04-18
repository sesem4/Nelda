package dk.sdu.sesem4.common.data.EntityParts;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LifePartTest {

	private LifePart lifePart;

	@Before
	public void createLifePart() {
		this.lifePart = new LifePart(3);
	}

	// Get/Set Life
	@Test
	public void testGetSetLife() {
		int lifeToSet = 705;
		this.lifePart.setLife(lifeToSet);
		assertEquals(lifeToSet, this.lifePart.getLife());
	}
	@Test
	public void testGetSetLifeTwice() {
		int lifeToSet = 705;
		this.lifePart.setLife(lifeToSet);
		this.lifePart.setLife(5);
		assertNotEquals(lifeToSet, this.lifePart.getLife());
	}

	// isAlive()
	@Test
	public void testIsAliveWhenHealthIsPositive() {
		this.lifePart.setLife(1);
		assertTrue(this.lifePart.isAlive());
	}
	@Test
	public void testIsAliveWhenHealthIsNegative() {
		this.lifePart.setLife(-1);
		assertFalse(this.lifePart.isAlive());
	}
	@Test
	public void testIsAliveWhenHealthIsZero() {
		this.lifePart.setLife(0);
		assertFalse(this.lifePart.isAlive());
	}

	// isDead()
	@Test
	public void testIsDeadWhenHealthIsPositive() {
		this.lifePart.setLife(1);
		assertFalse(this.lifePart.isDead());
	}
	@Test
	public void testIsDeadWhenHealthIsNegative() {
		this.lifePart.setLife(-1);
		assertTrue(this.lifePart.isDead());
	}
	@Test
	public void testIsDeadWhenHealthIsZero() {
		this.lifePart.setLife(0);
		assertTrue(this.lifePart.isDead());
	}

	// isDead()
	@Test
	public void testIsDeadNeverEqualToIsAlive() {
		this.lifePart.setLife(1);
		assertNotEquals(this.lifePart.isDead(), this.lifePart.isAlive());
		this.lifePart.setLife(-1);
		assertNotEquals(this.lifePart.isDead(), this.lifePart.isAlive());
		this.lifePart.setLife(0);
		assertNotEquals(this.lifePart.isDead(), this.lifePart.isAlive());
	}

	// kill()
	@Test
	public void testKill() {
		this.lifePart.setLife(3);
		assertTrue(this.lifePart.isAlive());

		this.lifePart.kill();
		assertTrue(this.lifePart.isDead());
	}
	
	@Test
	public void testKillForMaxStartingLife() {
		this.lifePart.setLife(Integer.MAX_VALUE);
		assertTrue(this.lifePart.isAlive());

		this.lifePart.kill();
		assertTrue(this.lifePart.isDead());
	}

	// doDamage()
	@Test
	public void testDoDamage() {
		int life = 3;
		this.lifePart.setLife(life);
		int damage = 1;
		this.lifePart.doDamage(damage);
		assertEquals(this.lifePart.getLife(), life - damage);
	}

	@Test
	public void testNegativeDamage() {
		int life = 3;
		this.lifePart.setLife(life);
		int healing = 1;
		this.lifePart.doDamage(-healing);
		assertEquals(this.lifePart.getLife(), life + healing);
	}
}
