package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.util.Direction;

public interface MovementControllerSPI {
	/**
	 * Get desired movement
	 *
	 * @param gameData Current gameData for game.
	 * @param entity Entity of which wish to get movement direction.
	 * @return Direction of which the entity should move. This can be Null, if the entity should not move.
	 */
	Direction getMovement(GameData gameData, Entity entity);
	
}
