package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.combat.Attack;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public interface CombatControllerSPI {

	/**
	 * Checks if the entity should attack
	 * <p>
	 * Pre-condition: The game has been started, and the SPI has been implemented in a class.
	 * post-condition: Returns if a combat should be initiated
	 *
	 * @param gameData The game data
	 * @param entity   The entity which is requesting combat
	 * @return the AttackType
	 */
	boolean shouldAttack(GameData gameData, Entity entity);

	/**
	 * Spawn an attack from the provided entity
	 * <p>
	 * Pre-condition: The game has been started, and the SPI has been implemented in a class.
	 * post-condition: Returns an Attack and spawns an attack in the world.
	 *
	 * @param gameData The game data
	 * @param entity   The entity which is requesting combat
	 * @return the AttackType
	 */
	Attack spawnAttack(GameData gameData, Entity entity);
}
