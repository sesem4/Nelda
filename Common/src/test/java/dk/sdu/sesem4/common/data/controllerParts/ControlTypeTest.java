package dk.sdu.sesem4.common.data.controllerParts;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ControlTypeTest {
    private ControlType controlType;
    @Before
    public void setUp() throws Exception {
        controlType = ControlType.KEYBOARD;
    }
    @Test
    public void testGetControlType() {
        assertEquals(ControlType.KEYBOARD, controlType);
    }
    @Test
    public void testSetControlType(){
        controlType = ControlType.GAMEPAD;
        assertEquals(ControlType.GAMEPAD, controlType);
    }

}