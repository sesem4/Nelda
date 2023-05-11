package dk.sdu.sesem4.keyboardcontrol;


import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;
/**
 * This class is used for define the type of the control and the movement controller
 */
public class KeyBoardController implements ControlSPI {
    /**
     * The specific type of the control
     */
    private ControlType controlType;
    /**
     * The movement controller, which contains the input keys for the movement
     */
    private MovementControllerSPI movementController;

    /**
     * Constructor for the class, where the control type is set to KEYBOARD as default and the movement controller is initialized
     */
    public KeyBoardController(){
        this.controlType = ControlType.KEYBOARD;
        this.movementController = new KeyBoardMovementController();
    }

    @Override
    public ControlType getType() {
        return this.controlType;
    }

    @Override
    public MovementControllerSPI getMovementController() {
        return this.movementController;

    }

    public void setMovementController(MovementControllerSPI movementController) {
        this.movementController = movementController;
    }
}
