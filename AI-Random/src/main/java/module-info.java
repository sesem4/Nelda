module AI.Random {
    requires Common;
    requires java.desktop;

    exports dk.sdu.sesem4.RandomMovement;

    provides dk.sdu.sesem4.common.SPI.MovementControllerSPI with dk.sdu.sesem4.RandomMovement.RandomMovementController;
    provides dk.sdu.sesem4.common.SPI.ControlSPI with dk.sdu.sesem4.RandomMovement.RandomMovement;
}