package dk.sdu.sesem4.smartAIMovement;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;

public class SmartAIMovement implements ControlSPI {

    @Override
    public ControlType getType() {
        return ControlType.SMART_AI;
    }

    @Override
    public MovementControllerSPI getMovementController() {
        return new SmartAIMovementController();
    }
}
