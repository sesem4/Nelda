package dk.sdu.sesem4.common.event.events.map;

import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.event.Event;

/**
 * Map transition finished event - Data
 */
public class MapTransitionDoneEvent extends Event {
	private final GameData gameData;

	public MapTransitionDoneEvent() {
		this(null);
	}

	public MapTransitionDoneEvent(GameData gameData) {
		this.gameData = gameData;
	}

	public GameData getGameData() {
		return gameData;
	}
}
