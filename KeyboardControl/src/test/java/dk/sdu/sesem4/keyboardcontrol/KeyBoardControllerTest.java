package dk.sdu.sesem4.keyboardcontrol;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyBoardControllerTest {
    @Test
    public void testGetType() {
        KeyBoardController keyBoardController = new KeyBoardController();

        assertEquals(keyBoardController.getType(), ControlType.KEYBOARD);
    }

    @Test
    public void testGetAndSetMovementController() {
        KeyBoardController keyBoardController = new KeyBoardController();
        KeyBoardMovementController keyBoardMovementController = new KeyBoardMovementController();

        keyBoardController.setMovementController(keyBoardMovementController);
        assertEquals(keyBoardController.getMovementController(), keyBoardMovementController);
    }
}