package dk.sdu.sesem4.common.data.math;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * A class that tests the Vector2 class.
 */
public class Vector2Test {
    private Vector2 a;
    private Vector2 b;

    // Tests if two vectors can be added and if the result is correct.
    @Test
    public void testAdd() {
        this.a = new Vector2(2, 3);
        this.b = new Vector2(5, 1);

        Vector2 actual = a.plus(b);

        Vector2 expected = new Vector2(7, 4);
        assertEquals(expected, actual);
    }

    // Tests if two vectors can be subtracted and if the result is correct.
    @Test
    public void testAddWorksForNegatives() {
        this.a = new Vector2(2, -3);
        this.b = new Vector2(-5, 1);

        Vector2 actual = a.plus(b);

        Vector2 expected = new Vector2(-3, -2);
        assertEquals(expected, actual);
    }
}
