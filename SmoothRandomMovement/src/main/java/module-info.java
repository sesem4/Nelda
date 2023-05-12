module SmoothRandomMovement {
    requires Common;
    requires java.desktop;

    exports dk.sdu.sesem4.SmoothRandomMovement;
    provides dk.sdu.sesem4.common.SPI.MovementControllerSPI with dk.sdu.sesem4.SmoothRandomMovement.SmoothRandomMovementController;
    provides dk.sdu.sesem4.common.SPI.ControlSPI with dk.sdu.sesem4.SmoothRandomMovement.SmoothRandomMovement;
}