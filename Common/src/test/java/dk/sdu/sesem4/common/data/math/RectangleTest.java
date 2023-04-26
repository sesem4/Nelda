package dk.sdu.sesem4.common.data.math;
import junit.framework.TestCase;


public class RectangleTest extends TestCase {
	
	private Rectangle rectangle;
	
	float xPosition;
	float yPosition;
	float xSize;
	float ySize;
	
	@Override
	public void setUp() {
		xPosition = 7;
		yPosition = 3;
		xSize = 16;
		ySize = 8;
		
		rectangle = new Rectangle(new Vector2(xPosition, yPosition), new Vector2(xSize, ySize));
	}
	
	public void testGetLeftEdge() {
		float expected = xPosition;
		float actual = rectangle.getLeftEdge();
		
		assertEquals(expected, actual);
	}
	
	public void testGetRightEdge() {
		float expected = xPosition + xSize;
		float actual = rectangle.getRightEdge();
		
		assertEquals(expected, actual);
	}
	
	public void testGetBottomEdge() {
		float expected = yPosition;
		float actual = rectangle.getBottomEdge();
		
		assertEquals(expected, actual);
	}
	
	public void testGetTopEdge() {
		float expected = yPosition + ySize;
		float actual = rectangle.getTopEdge();
		
		assertEquals(expected, actual);
	}
	
	public void testGetBottomLeftCorner() {
		Vector2 expected = new Vector2(xPosition, yPosition);
		Vector2 actual = rectangle.getBottomLeftCorner();
		
		assertEquals(expected, actual);
	}
	
	public void testGetBottomRightCorner() {
		Vector2 expected = new Vector2(xPosition + xSize, yPosition);
		Vector2 actual = rectangle.getBottomRightCorner();
		
		assertEquals(expected, actual);
	}
	
	public void testGetTopLeftCorner() {
		Vector2 expected = new Vector2(xPosition, yPosition + ySize);
		Vector2 actual = rectangle.getTopLeftCorner();
		
		assertEquals(expected, actual);
	}
	
	public void testGetTopRightCorner() {
		Vector2 expected = new Vector2(xPosition + xSize, yPosition + ySize);
		Vector2 actual = rectangle.getTopRightCorner();
		
		assertEquals(expected, actual);
	}
	
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