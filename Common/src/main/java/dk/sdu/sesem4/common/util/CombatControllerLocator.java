package dk.sdu.sesem4.common.util;

import dk.sdu.sesem4.common.SPI.CombatControllerSPI;
import dk.sdu.sesem4.common.data.combat.WeaponType;

import java.util.List;

/**
 * CombatController Locator utility class
 *
 * @author: Michael Ringhus Gertz
 */
public class CombatControllerLocator {

	/**
	 * Locate a CombatController by its type.
	 *
	 * @param type Controller type.
	 * @return CombatControllerSPI that is of the provided type.
	 */
	public static CombatControllerSPI locateController(WeaponType type) {
		List<CombatControllerSPI> controllers = SPILocator.locateAll(CombatControllerSPI.class);

		for (CombatControllerSPI controller : controllers) {
			if (controller.getWeaponType() == type) {
				return controller;
			}
		}

		return null;
	}
}
