package dk.sdu.sesem4.SmoothRandomMovement;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;

public class SmoothRandomMovement implements ControlSPI {

    @Override
    public ControlType getType() {
        return ControlType.ROUGH_AI;
    }

    @Override
    public MovementControllerSPI getMovementController() {
        return new SmoothRandomMovementController();
    }
}
