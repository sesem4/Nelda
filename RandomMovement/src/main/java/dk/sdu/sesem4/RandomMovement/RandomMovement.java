package dk.sdu.sesem4.RandomMovement;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;

/**
 * This class is responsible for creating a RandomMovement for the Enemey.
 */
public class RandomMovement implements ControlSPI {

    @Override
    public ControlType getType() {
		return ControlType.ROUGH_AI;
    }

    @Override
    public MovementControllerSPI getMovementController() {
        return new RandomMovementController();
    }
}
