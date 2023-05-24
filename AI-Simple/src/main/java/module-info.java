import dk.sdu.sesem4.ai.simple.SimpleAIMovement;
import dk.sdu.sesem4.ai.simple.SimpleAiMovementController;

module AI.Simple {
    requires Common;
    requires java.desktop;

    exports dk.sdu.sesem4.ai.simple;
    provides dk.sdu.sesem4.common.SPI.MovementControllerSPI with SimpleAiMovementController;
    provides dk.sdu.sesem4.common.SPI.ControlSPI with SimpleAIMovement;
}