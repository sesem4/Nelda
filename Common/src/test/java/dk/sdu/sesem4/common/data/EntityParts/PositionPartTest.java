package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PositionPartTest {
	private PositionPart positionPart;

	@Before
	public void createPositionPart() {
		this.positionPart = new PositionPart(new Vector2(0, 0), new Vector2(16, 16), Direction.UP);
	}

	@Test
	public void testGetSetPosition() {
		Vector2 expected = new Vector2(189.6f, 4753.4f);
		positionPart.setPosition(expected);
		Vector2 actual = positionPart.getPosition();

		assertEquals(expected, actual);
	}

	@Test
	public void testGetSetSize() {
		Vector2 expected = new Vector2(189.6f, 4753.4f);
		positionPart.setSize(expected);
		Vector2 actual = positionPart.getSize();

		assertEquals(expected, actual);
	}

	@Test
	public void testGetSetDirection() {
		Direction expected = Direction.LEFT;
		positionPart.setDirection(expected);
		Direction actual = positionPart.getDirection();

		assertEquals(expected, actual);
	}
	
	/**
	 * tests moving the positionPart by using a startPosition and a deltaPosition.
	 * We then compute the expectedEndPosition by adding the two vectors together
	 * Finally, check that they're the same (with an epsilon)
	 */
	@Test
	public void testMove() {
		Vector2 startPosition = new Vector2(189.6f, 4753.4f);
		Vector2 deltaPosition = new Vector2(-456.1f, 897.7f);
		Vector2 expectedEndPosition = startPosition.plus(deltaPosition);
		positionPart.move(deltaPosition);
		Vector2 actualEndPosition = positionPart.getPosition();
		// we have to use an epsilon, because we're doing floating-point stuff
		Vector2 difference = actualEndPosition.minus(expectedEndPosition);
		assertTrue(difference.getX() + difference.getY() < 0.0001);
	}
}
