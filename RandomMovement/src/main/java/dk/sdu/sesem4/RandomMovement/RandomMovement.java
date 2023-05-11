package dk.sdu.sesem4.RandomMovement;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;

/**
 * This class is responsible for creating a RandomMovement for the Enemey.
 */
public class RandomMovement implements ControlSPI {
    /**
     * The controlType of the RandomMovement
     */
    private final ControlType controlType;
    /**
     * The RandomMovementController of the RandomMovement
     */
    private final RandomMovementController randomMovementController;

    /**
     * Initializes the RandomMovementController and the ControlType in the constructor.
     */
    public RandomMovement(){
        this.controlType = ControlType.DUMBAI;
        this.randomMovementController = new RandomMovementController();
    }

    @Override
    public ControlType getType() {
        return this.controlType;
    }

    @Override
    public MovementControllerSPI getMovementController() {
        return this.randomMovementController;
    }
}
