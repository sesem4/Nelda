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

	public void x() {

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

	@Test
	public void testMove() {
		Vector2 startPosition = new Vector2(189.6f, 4753.4f);
		Vector2 deltaPosition = new Vector2(-456.1f, 897.7f);
		Vector2 expectedEndPosition = startPosition.plus(deltaPosition);
		positionPart.move(deltaPosition);
		Vector2 actualEndPosition = positionPart.getPosition();
		assertEquals(expectedEndPosition, actualEndPosition);
	}
}
