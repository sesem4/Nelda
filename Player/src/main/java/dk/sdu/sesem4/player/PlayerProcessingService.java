package dk.sdu.sesem4.player;

import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.data.rendering.SpriteData;
import dk.sdu.sesem4.common.util.Direction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class processes the player each game loop and changes its Movement, Position, LifePart, and Path to Texture.
 * It also includes methods for creating and getting the player entity.
 */
public class PlayerProcessingService implements ProcessingServiceSPI {

	/**
	 * Processes the player for each game loop, changing its Movement, Position, LifePart, and Path to Texture.
	 *
	 * @param gameData The {@link GameData} object for the game.
	 * @param priority The priority of the processing service.
	 */
	@Override
	public void process(GameData gameData, Priority priority) {
		//process the player for each game loop, change Movement, Position, LifePart, Path to Texture,
		for (Entity player : gameData.getGameEntities().getEntities(Player.class)) {
			PositionPart positionPart = player.getEntityPart(PositionPart.class);
			MovingPart movingPart = player.getEntityPart(MovingPart.class);
			LifePart lifePart = player.getEntityPart(LifePart.class);
			SpritePart spritePart = player.getEntityPart(SpritePart.class);

			//process all the parts
			positionPart.process(gameData, player);
			movingPart.process(gameData, player);
			lifePart.process(gameData, player);
			spritePart.process(gameData, player);
		}
	}
}


