package dk.sdu.sesem4.keyboardcontrol;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class KeyBoardControllerTest {
    private KeyBoardController keyBoardController;
    private KeyBoardMovementController keyBoardMovementController;

    @Before
    public void setUp() {
        this.keyBoardMovementController = new KeyBoardMovementController();
        this.keyBoardController = new KeyBoardController();
    }

    @Test
    public void testGetType() {
        assertEquals(this.keyBoardController.getType(), ControlType.KEYBOARD);
    }

    @Test
    public void testGetAndSetMovementController() {
        this.keyBoardController.setMovementController(keyBoardMovementController);
        assertEquals(this.keyBoardController.getMovementController(), this.keyBoardMovementController);
    }
}