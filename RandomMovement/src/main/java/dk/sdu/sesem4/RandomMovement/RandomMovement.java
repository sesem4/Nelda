package dk.sdu.sesem4.RandomMovement;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;

public class RandomMovement implements ControlSPI {
    private ControlType controlType;
    private RandomMovementController randomMovementController;

    public RandomMovement(){
        this.controlType = ControlType.AI;
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
