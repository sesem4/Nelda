package dk.sdu.sesem4.player;

import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.*;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.event.EventManager;
import dk.sdu.sesem4.common.event.events.map.MapTransitionEvent;
import dk.sdu.sesem4.common.event.events.map.MapTransitionEventType;
import dk.sdu.sesem4.common.util.Direction;


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
			CombatPart combatPart = player.getEntityPart(CombatPart.class);
			SpritePart spritePart = player.getEntityPart(SpritePart.class);

			//change map based on movement
			mapTransition(gameData, positionPart);

			//process all the parts
			positionPart.process(gameData, player);
			movingPart.process(gameData, player);
			lifePart.process(gameData, player);
			combatPart.process(gameData, player);
			spritePart.process(gameData, player);
		}
	}

	/**
	 * Transition the map based on the player's position
	 *
	 * @param gameData     The {@link GameData} object for the game.
	 * @param positionPart The {@link PositionPart} of the player.
	 */
	private void mapTransition(GameData gameData, PositionPart positionPart) {
		float bottomEdge = 0;
		float topEdge = gameData.getGameWorld().getMapSize().getY();
		float leftEdge = 0;
		float rightEdge = gameData.getGameWorld().getMapSize().getX();

		if (positionPart.getPosition().getY() + (positionPart.getSize().getY() / 2) < bottomEdge) {
			positionPart.setPosition(new Vector2(positionPart.getPosition().getX(), topEdge - positionPart.getSize().getY() / 2));
			EventManager.getInstance().notify(MapTransitionEventType.class, new MapTransitionEvent(Direction.DOWN, gameData));
		} else if (positionPart.getPosition().getY() + (positionPart.getSize().getY() / 2) > topEdge) {
			positionPart.setPosition(new Vector2(positionPart.getPosition().getX(), bottomEdge - positionPart.getSize().getY() / 2));
			EventManager.getInstance().notify(MapTransitionEventType.class, new MapTransitionEvent(Direction.UP, gameData));
		} else if (positionPart.getPosition().getX() + (positionPart.getSize().getX() / 2) < leftEdge) {
			positionPart.setPosition(new Vector2(rightEdge - positionPart.getSize().getX() / 2, positionPart.getPosition().getY()));
			EventManager.getInstance().notify(MapTransitionEventType.class, new MapTransitionEvent(Direction.LEFT, gameData));
		} else if (positionPart.getPosition().getX() + (positionPart.getSize().getX() / 2) > rightEdge) {
			positionPart.setPosition(new Vector2(leftEdge - positionPart.getSize().getX() / 2, positionPart.getPosition().getY()));
			EventManager.getInstance().notify(MapTransitionEventType.class, new MapTransitionEvent(Direction.RIGHT, gameData));
		}

	}
}