package dk.sdu.sesem4.ai.simple;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;

public class SimpleAIMovement implements ControlSPI {
    @Override
    public ControlType getType() {
        return ControlType.AI;
    }

    @Override
    public MovementControllerSPI getMovementController() {
        return new SimpleAiMovementController();
    }
}
