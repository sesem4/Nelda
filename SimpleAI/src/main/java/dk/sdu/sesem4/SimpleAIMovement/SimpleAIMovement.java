package dk.sdu.sesem4.SimpleAIMovement;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;

public class SimpleAIMovement implements ControlSPI {
    private ControlType controlType;
    private SimpleAIMovementController simpleAIMovementController;

    public SimpleAIMovement(){
        this.controlType = ControlType.MEDIUM_AI;
        this.simpleAIMovementController = new SimpleAIMovementController();
    }

    @Override
    public ControlType getType() {
        return this.controlType;
    }

    @Override
    public MovementControllerSPI getMovementController() {
        return this.simpleAIMovementController;
    }
}
