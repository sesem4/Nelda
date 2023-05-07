package dk.sdu.sesem4.common.event.events.map;

import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.event.Event;
import dk.sdu.sesem4.common.util.Direction;

/**
 * @author Jakob L.M. & Jon F.J.
 * A class that represents the MapTransitionEvent, which is used to transition the map.
 */
public class MapTransitionEvent extends Event {

	private Direction direction;
	private GameData gameData;

	/**
	 * Constructs a MapTransitionEvent
	 * @param direction the direction of the MapTransitionEvent
	 */
	public MapTransitionEvent(Direction direction, GameData gameData) {
		this.direction = direction;
		this.gameData = gameData;
	}

	/**
	 * Gets the direction of the MapTransitionEvent
	 * @return direction of the MapTransitionEvent
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Sets the direction of the MapTransitionEvent
	 * @param direction the direction of the MapTransitionEvent
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public GameData getGameData() {
		return gameData;
	}

	public void setGameData(GameData gameData) {
		this.gameData = gameData;
	}
}
