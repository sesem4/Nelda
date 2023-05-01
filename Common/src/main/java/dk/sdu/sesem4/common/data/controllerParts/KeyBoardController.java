package dk.sdu.sesem4.common.data.controllerParts;


public class KeyBoardController {
    private ControlType controlType;

    public KeyBoardController(){
        this.controlType = ControlType.KEYBOARD;
    }
    public KeyBoardController(ControlType controlType){
        this.controlType = controlType;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public void setControlType(ControlType controlType) {
        this.controlType = controlType;
    }


}
