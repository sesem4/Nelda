import dk.sdu.sesem4.ai.smart.SmartAIMovement;
import dk.sdu.sesem4.ai.smart.SmartAIMovementController;

module SmartAI {
    requires Common;
    requires java.desktop;

    exports dk.sdu.sesem4.ai.smart;
    provides dk.sdu.sesem4.common.SPI.MovementControllerSPI with SmartAIMovementController;
    provides dk.sdu.sesem4.common.SPI.ControlSPI with SmartAIMovement;
}