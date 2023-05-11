package dk.sdu.sesem4.common.data.math;


import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

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
	 * We need to test the following scenarios:
	 * a.left is before b.left
	 * a.left is after b.left, but before b.right
	 * a.left is after b.right
	 * the same for a.right, but it cannot be less than a.left
	 * We also need to do all this in conjunction with Y
	 */
	@Test
	public void testCollidesWith() {
		Rectangle a = new Rectangle(new Vector2(1, 1), new Vector2(1, 1));
		for (AbstractMap.Entry<Float, Float> xRect : getRanges()) {
			for (AbstractMap.Entry<Float, Float> yRect : getRanges()) {
				Rectangle b = new Rectangle(new Vector2(xRect.getKey(), yRect.getKey()), new Vector2(xRect.getValue()-xRect.getKey(), yRect.getValue()-yRect.getKey()));
				boolean xCollision = !(b.getRightEdge() < a.getLeftEdge() || a.getRightEdge() < b.getLeftEdge());
				boolean yCollision = !(b.getTopEdge() < a.getBottomEdge() || a.getTopEdge() < b.getBottomEdge());
				boolean expected = xCollision && yCollision;
				assertEquals(expected, a.collidesWith(b));
			}
		}
	}
	
	private List<AbstractMap.Entry<Float, Float>> getRanges() {
		List<AbstractMap.Entry<Float, Float>> positions = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			float startPos = i + (1f/3f);
			for (int j = i; j < 3; j++) {
				float endPos = j + (2f/3f);
				positions.add(new AbstractMap.SimpleEntry<>(startPos, endPos));
			}
		}
		return positions;
	}
}