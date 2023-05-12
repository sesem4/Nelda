package dk.sdu.sesem4.SmoothRandomMovement;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;

public class SmoothRandomMovement implements ControlSPI {
    private ControlType controlType;
    private SmoothRandomMovementController smoothRandomMovementController;

    public SmoothRandomMovement(){
        this.controlType = ControlType.ROUGHAI;
        this.smoothRandomMovementController = new SmoothRandomMovementController();
    }

    @Override
    public ControlType getType() {
        return this.controlType;
    }

    @Override
    public MovementControllerSPI getMovementController() {
        return this.smoothRandomMovementController;
    }
}
