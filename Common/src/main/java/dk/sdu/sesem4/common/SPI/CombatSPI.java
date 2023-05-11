package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.weapon.AttackType;

public interface CombatSPI {
	/**
	 * Get type of combat, that the SPI provides
	 * <p>
	 * Pre-condition: The game has been started, and the SPI has been implemented in a class.
	 * post-condition: Combat type is returned.
	 *
	 * @return the WeaponType
	 */
	WeaponType getType();

	/**
	 * Get type of CombatControllers that the SPI provides
	 * <p>
	 * Pre-condition: The game has been started, and the SPI has been implemented in a class.
	 * post-condition: Combat controller is returned.
	 *
	 * @return the CombatController
	 */
	CombatControllerSPI getCombatController();
}
