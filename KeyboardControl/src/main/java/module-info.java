import dk.sdu.sesem4.common.SPI.ControlSPI;

module KeyboardControl {
    requires Common;
    requires java.desktop;
    requires ShadedLibGDX;
    exports dk.sdu.sesem4.keyboardcontrol;
    provides dk.sdu.sesem4.common.SPI.MovementControllerSPI with dk.sdu.sesem4.keyboardcontrol.KeyBoardMovementController;
	provides ControlSPI with dk.sdu.sesem4.keyboardcontrol.KeyBoardController;
}