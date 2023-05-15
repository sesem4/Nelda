package dk.sdu.sesem4.smartAIMovement;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;

public class SmartAIMovement implements ControlSPI {
    private ControlType controlType;
    private SmartAIMovementController smartAIMovementController;

    public SmartAIMovement(){
        this.controlType = ControlType.SMART_AI;
        this.smartAIMovementController = new SmartAIMovementController();
    }

    @Override
    public ControlType getType() {
        return this.controlType;
    }

    @Override
    public MovementControllerSPI getMovementController() {
        return this.smartAIMovementController;
    }
}
