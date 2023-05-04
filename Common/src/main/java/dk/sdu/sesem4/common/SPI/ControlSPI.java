package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.controllerParts.ControlType;

/**
 * This SPI is used for define the type of the control and getting the movement controller from the MovementControllerSPI
 */
public interface ControlSPI {
/**
	 * Get the type of the control
	 * @return The type of the control
	 */
	ControlType getType();
	/**
	 * Get the movement controller from the MovementControllerSPI
	 * @return The movement controller
	 */
	MovementControllerSPI getMovementController();
}
