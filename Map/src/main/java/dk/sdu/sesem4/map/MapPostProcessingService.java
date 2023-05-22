package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Rectangle;
import dk.sdu.sesem4.common.data.process.Priority;

public class MapPostProcessingService implements PostProcessingServiceSPI {
	/**
	 * Do collision check for all entities. This is done by checking that all corners of the entity
	 * are on a passible tile. If at least one corner isn't, we undo the Entity's movement.
	 * @param gameData The game data
	 * @param priority The priority, which is to be run for the current process round
	 */
	@Override
	public void postProcess(GameData gameData, Priority priority) {
		checkMapCollisions(gameData);
	}

	/**
	 * Determines if a given entity can move on the map.
	 * @param gameData The game data.
	 */
	protected void checkMapCollisions(GameData gameData) {
		MapUtil mapUtil = new MapUtil();
		for (Entity entity : gameData.getGameEntities().getEntities()) {
			PositionPart positionPart = entity.getEntityPart(PositionPart.class);
			Rectangle entityRectangle = positionPart.getBoundingBox();

			if (!mapUtil.isRectangleValid(entityRectangle)) {
				entity.collidedWithMap(gameData);
			}
		}
	}
}
