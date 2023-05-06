package dk.sdu.sesem4.common.util;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;

import java.util.List;

/**
 * Controller locator utility class
 *
 * @author Mikkel Albrechtsen
 */
public class ControllerLocator {

	/**
	 * Locate a controller by its type.
	 *
	 * @param type Controller type.
	 * @return ControlSPI that is of the provided type.
	 */
	public static ControlSPI locateController(ControlType type) {
		List<ControlSPI> controllers = SPILocator.locateAll(ControlSPI.class);

		for (ControlSPI controller : controllers) {
			if (controller.getType() == type) {
				return controller;
			}
		}

		return null;
	}
}
