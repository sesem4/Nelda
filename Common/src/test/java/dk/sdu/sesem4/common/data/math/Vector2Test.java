package dk.sdu.sesem4.common.data.math;
import static org.junit.Assert.*;

import dk.sdu.sesem4.common.util.Direction;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A class that tests the Vector2 class.
 */
public class Vector2Test {
    private Vector2 a;
    private Vector2 b;
    
    /**
     * No-arg constructor returns (0,0)
     */
    @Test
    public void testDefaultConstructor() {
        a = new Vector2();
        b = new Vector2(0, 0);
        
        assertEquals(a, b);
    }
    
    /**
     * X,Y constructor returns (X,Y)
     */
    @Test
    public void testXYConstructor() {
        float x = 5;
        float y = 9;
        a = new Vector2(x, y);
        // test with an epsilon because of floating point
        assertTrue(Math.abs(a.getX() - x) < 0.0001);
        assertTrue(Math.abs(a.getY() - y) < 0.0001);
    }
    
    /**
     * Direction constructor returns vector with magnitude 1 in the correct direction
     */
    @Test
    public void testDirectionConstructor() {
        Map<Direction, Vector2> argumentToExpected = Map.of(
                Direction.UP, new Vector2(0, 1),
                Direction.DOWN, new Vector2(0, -1),
                Direction.LEFT, new Vector2(-1, 0),
                Direction.RIGHT, new Vector2(1, 0)
        );
        
        // for each direction, check that it matches the expected
        for (Map.Entry<Direction, Vector2> entry : argumentToExpected.entrySet()) {
            a = new Vector2(entry.getKey());
            b = entry.getValue();
            assertEquals(a, b);
        }
    }
    
    /**
     * Vectors can be added, and the result is correct.
     */
    @Test
    public void testAdd() {
        a = new Vector2(2, 3);
        b = new Vector2(5, 1);

        Vector2 actual = a.plus(b);

        Vector2 expected = new Vector2(7, 4);
        assertEquals(expected, actual);
    }
    
    /**
     * Vectors with negative values can be added, and the result is correct.
     */
    @Test
    public void testAddWorksForNegatives() {
        a = new Vector2(2, -3);
        b = new Vector2(-5, 1);

        Vector2 actual = a.plus(b);

        Vector2 expected = new Vector2(-3, -2);
        assertEquals(expected, actual);
    }
    
    /**
     * Vectors can be subtracted, and the result is correct
     */
    @Test
    public void testSubtract() {
        a = new Vector2(10, -3);
        b = new Vector2(7, 2);
        
        Vector2 actual = a.minus(b);
        
        Vector2 expected = new Vector2(3, -5);
        
        assertEquals(expected, actual);
    }
    
    /**
     * Subtracting also works with negative values
     */
    @Test
    public void testSubtractWithNegative() {
        a = new Vector2(10, -3);
        b = new Vector2(-2, -7);
        
        Vector2 actual = a.minus(b);
        
        Vector2 expected = new Vector2(12, 4);
        
        assertEquals(expected, actual);
    }
    
    /**
     * Vectors can be multiplied by a scalar, and the result is correct
     */
    @Test
    public void testTimes() {
        a = new Vector2(8, -3);
        float scalar = 10;
        
        Vector2 actual = a.times(scalar);
        
        Vector2 expected = new Vector2(80, -30);
        
        assertEquals(expected, actual);
    }
    
    /**
     * Multiplication works with a negative scalar
     */
    @Test
    public void testTimesWithNegativeScalar() {
        a = new Vector2(8, -3);
        float scalar = -10;
        
        Vector2 actual = a.times(scalar);
        
        Vector2 expected = new Vector2(-80, 30);
        
        assertEquals(expected, actual);
    }
    
    /**
     * Vectors can be divided by a scalar, and the result is correct
     */
    @Test
    public void testDivide() {
        a = new Vector2(8, -3);
        float scalar = 10;
        
        Vector2 actual = a.divide(scalar);
        
        Vector2 expected = new Vector2(0.8f, -0.3f);
        
        assertEquals(expected, actual);
    }
    
    /**
     * Division works with a negative scalar
     */
    @Test
    public void testDivideWithNegativeScalar() {
        a = new Vector2(8, -3);
        float scalar = -10;
        
        Vector2 actual = a.divide(scalar);
        
        Vector2 expected = new Vector2(-0.8f, 0.3f);
        
        assertEquals(expected, actual);
    }
    
    /**
     * Division by zero will result in infinity
     */
    @Test
    public void testDivideByZeroGivesInfinity() {
        a = new Vector2(8, -3);
        float scalar = 0;
        
        Vector2 actual = a.divide(scalar);
        
        Vector2 expected = new Vector2(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY);
        
        assertEquals(expected, actual);
    }
    
    /**
     * Division by negative zero will result in negative infinity
     */
    @Test
    public void testDivideByNegativeZeroGivesInfinity() {
        a = new Vector2(8, -3);
        float scalar = -0f;
        
        Vector2 actual = a.divide(scalar);
        
        Vector2 expected = new Vector2(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
        
        assertEquals(expected, actual);
    }
    
    /**
     * Vectors can be converted to Directions
     * This only tests on Vectors with magnitude 1 in each cardinal direction
     */
    @Test
    public void testToDirectionSimple() {
        Map<Vector2, Direction> argumentToExpected = Map.of(
                new Vector2(0, 1), Direction.UP,
                new Vector2(0, -1), Direction.DOWN,
                new Vector2(-1, 0), Direction.LEFT,
                new Vector2(1, 0), Direction.RIGHT
        );
    
        // for each case, check that it matches the expected
        for (Map.Entry<Vector2, Direction> entry : argumentToExpected.entrySet()) {
            Direction actual = entry.getKey().toDirection();
            Direction expected = entry.getValue();
            assertEquals(expected, actual);
        }
    }
    
    /**
     * Direction conversion will use the component with the larger magnitude
     */
    @Test
    public void testToDirectionComplex() {
        Map<Vector2, Direction> argumentToExpected = Map.of(
                // x component is bigger than x
                // positive x
                new Vector2(2, 1), Direction.RIGHT,
                new Vector2(2, -1), Direction.RIGHT,
                // negative x
                new Vector2(-2, 1), Direction.LEFT,
                new Vector2(-2, -1), Direction.LEFT,
        
                // y component bigger than x
                // positive y
                new Vector2(1, 2), Direction.UP,
                new Vector2(-1, 2), Direction.UP,
                // negative y
                new Vector2(1, -2), Direction.DOWN,
                new Vector2(-1, -2), Direction.DOWN
        );
    
        // for each case, check that it matches the expected
        for (Map.Entry<Vector2, Direction> entry : argumentToExpected.entrySet()) {
            Direction actual = entry.getKey().toDirection();
            Direction expected = entry.getValue();
            assertEquals(expected, actual);
        }
    }
    
    /**
     * Vectors can get direction to each other
     * From A to B will always be the exact opposite of From B to A
     */
    @Test
    public void testGetDirectionTo() {
        a = new Vector2(10, 10);
        b = new Vector2(10, 8);
        
        assertEquals(Direction.DOWN, a.getDirectionTo(b));
        assertEquals(Direction.UP, b.getDirectionTo(a));
        
        b = new Vector2(12, 10);
        
        assertEquals(Direction.RIGHT, a.getDirectionTo(b));
        assertEquals(Direction.LEFT, b.getDirectionTo(a));
    }
    
    /**
     * Vectors equal other vectors with the same components
     */
    @Test
    public void testEquals() {
        float x = 5;
        float y = 9;
        a = new Vector2(5, 9);
        b = new Vector2(5, 9);
        assertEquals(a, b);
    
        // test all vectors with x and y with differences -1 to +1
        for (int deltaX = -1; deltaX <= 1; deltaX++) {
            for (int deltaY = -1; deltaY <= 1; deltaY++) {
                b = new Vector2(x+deltaX, y+deltaY);
                if (deltaX == 0 && deltaY == 0) {
                    assertEquals(a, b);
                } else {
                    assertNotEquals(a, b);
                }
            }
        }
    }
    
    /**
     * Special cases for NaN, Infinities, Positive and Negative zero work as described in IEEE-754
     */
    @Test
    public void testEqualsAgainstFloatingPointCompare() {
        // All Vectors in this method will use the same X and Y for sake of simplicity.
        // Equality is unchanged by this
        List<Float> cases = List.of(Float.NaN, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, +0f, -0f);

        for (int i = 0; i < cases.size(); i++) {
            for (int j = i; j < cases.size(); j++) {
                Float a = cases.get(i);
                Float b = cases.get(j);

                boolean expected = Objects.equals(a, b);
                boolean actual = new Vector2(a, a).equals(new Vector2(b, b));

                // according to IEEE-754, "Comparisons shall ignore the sign of zero (so +0 = −0)."
                if (a == 0 && b == 0) {
                    expected = true;
                }
                // according to IEEE-754, "Every NaN shall compare unordered with everything, including itself."
                // unordered will mean it always results in false for all comparisons, even equality checks with itself
                if (Float.isNaN(a) || Float.isNaN(b)) {
                    expected = false;
                }

                assertEquals(expected, actual);
            }
        }
    }
}
