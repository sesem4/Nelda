package dk.sdu.sesem4.SimpleAIMovement;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;

public class SimpleAIMovement implements ControlSPI {
    @Override
    public ControlType getType() {
        return ControlType.MEDIUM_AI;
    }

    @Override
    public MovementControllerSPI getMovementController() {
        return new SimpleAiMovementController();
    }
}
