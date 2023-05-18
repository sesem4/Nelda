
module SimpleAI {
    requires Common;
    requires java.desktop;

    exports dk.sdu.sesem4.SimpleAIMovement;
    provides dk.sdu.sesem4.common.SPI.MovementControllerSPI with dk.sdu.sesem4.SimpleAIMovement.SimpleAiMovementController;
    provides dk.sdu.sesem4.common.SPI.ControlSPI with dk.sdu.sesem4.SimpleAIMovement.SimpleAIMovement;
}