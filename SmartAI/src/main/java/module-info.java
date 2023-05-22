
module SmartAI {
    requires Common;
    requires java.desktop;

    exports dk.sdu.sesem4.smartAIMovement;
    provides dk.sdu.sesem4.common.SPI.MovementControllerSPI with dk.sdu.sesem4.smartAIMovement.SmartAIMovementController;
    provides dk.sdu.sesem4.common.SPI.ControlSPI with dk.sdu.sesem4.smartAIMovement.SmartAIMovement;
}