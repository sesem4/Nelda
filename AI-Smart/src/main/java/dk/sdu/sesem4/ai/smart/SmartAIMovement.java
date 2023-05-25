package dk.sdu.sesem4.ai.smart;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;

public class SmartAIMovement implements ControlSPI {

    @Override
    public ControlType getType() {
        return ControlType.AI;
    }

    @Override
    public MovementControllerSPI getMovementController() {
        return new SmartAIMovementController();
    }
}
