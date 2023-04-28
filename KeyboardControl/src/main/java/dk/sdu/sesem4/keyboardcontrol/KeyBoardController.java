package dk.sdu.sesem4.keyboardcontrol;

public class KeyBoardController {
    private ControlType controlType;
    private KeyBoardMovementController movementController;

    public KeyBoardController(){
        this.controlType = ControlType.KEYBOARD;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public void setControlType(ControlType controlType) {
        this.controlType = controlType;
    }

    public KeyBoardMovementController getMovementController() {
        return movementController;
    }

    public void setMovementController(KeyBoardMovementController movementController) {
        this.movementController = movementController;
    }


}
