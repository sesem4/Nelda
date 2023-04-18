package dk.sdu.sesem4.common.data.math;
import static org.junit.Assert.*;
import org.junit.Test;

public class Vector2Test {
    private Vector2 a;
    private  Vector2 b;

    @Test
    public void testAdd() {
        a = new Vector2(2, 3);
        b = new Vector2(5, 1);

        Vector2 actual = a.plus(b);

        Vector2 expected = new Vector2(7, 4);
        assertEquals(expected, actual);
    }

    @Test
    public void testAddWorksForNegatives() {
        a = new Vector2(2, -3);
        b = new Vector2(-5, 1);

        Vector2 actual = a.plus(b);

        Vector2 expected = new Vector2(-3, -2);
        assertEquals(expected, actual);
    }
}
