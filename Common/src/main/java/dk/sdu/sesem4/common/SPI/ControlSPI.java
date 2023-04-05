package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;

public interface ControlSPI {
	/**
	 * Move around on the map
	 * <br><br>
	 * Pre-condition: An entity has to be existing, with position and direction data.<br>
	 * post-condition: The entity has moved in the given direction.
	 *
	 * @param gameData The game data
	 * @param entity   The entity to move on the map
	 * @return dX and dY change in float array, that the entity has to move
	 */
	Vector2 move(GameData gameData, Entity entity);
}
