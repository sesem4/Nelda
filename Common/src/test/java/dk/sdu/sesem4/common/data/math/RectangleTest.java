package dk.sdu.sesem4.common.data.math;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RectangleTest {
	
	private Rectangle rectangle;
	
	float xPosition;
	float yPosition;
	float xSize;
	float ySize;
	
	/**
	 * sets up xPosition, yPosition, xSize, and ySize
	 * and generates rectangle from those
	 */
	@Before
	public void setUp() {
		xPosition = 7;
		yPosition = 3;
		xSize = 16;
		ySize = 8;
		
		rectangle = new Rectangle(new Vector2(xPosition, yPosition), new Vector2(xSize, ySize));
	}
	
	@Test
	public void testGetLeftEdge() {
		float expected = xPosition;
		float actual = rectangle.getLeftEdge();
		
		assertEquals(expected, actual, 0.0001);
	}
	
	@Test
	public void testGetRightEdge() {
		float expected = xPosition + xSize;
		float actual = rectangle.getRightEdge();
		
		assertEquals(expected, actual, 0.0001);
	}
	
	@Test
	public void testGetBottomEdge() {
		float expected = yPosition;
		float actual = rectangle.getBottomEdge();
		
		assertEquals(expected, actual, 0.0001);
	}
	
	@Test
	public void testGetTopEdge() {
		float expected = yPosition + ySize;
		float actual = rectangle.getTopEdge();
		
		assertEquals(expected, actual, 0.0001);
	}
	
	@Test
	public void testGetBottomLeftCorner() {
		Vector2 expected = new Vector2(xPosition, yPosition);
		Vector2 actual = rectangle.getBottomLeftCorner();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetBottomRightCorner() {
		Vector2 expected = new Vector2(xPosition + xSize, yPosition);
		Vector2 actual = rectangle.getBottomRightCorner();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetTopLeftCorner() {
		Vector2 expected = new Vector2(xPosition, yPosition + ySize);
		Vector2 actual = rectangle.getTopLeftCorner();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetTopRightCorner() {
		Vector2 expected = new Vector2(xPosition + xSize, yPosition + ySize);
		Vector2 actual = rectangle.getTopRightCorner();
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Test collisions between two rectangles
	 */
	@Test
	public void testCollidesWith() {
		// we need to test the following scenarios:
		// a.left is before b.left
		// a.left is after b.left, but before b.right
		// a.left is after b.right
		// the same for a.right, but it cannot be less than a.left
		
		// before, inside, after
		
		// all of this also needs to be tested in conjunction with the y-axis
	}
}