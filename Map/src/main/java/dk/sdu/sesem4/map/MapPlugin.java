package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.event.EventManager;
import dk.sdu.sesem4.common.event.events.map.MapTransitionEventType;

/**
 * The MapPlugin class is the entry point for the map module.
 */
public class MapPlugin implements PluginServiceSPI {

	/**
	 * The MapProcessingService object that is used to process the map data.
	 */
	private MapTransitionHandler mapTransitionHandler;

	/**
	 * This method is called when the game is started.
	 * It creates a new Map object, a new MapProcessingService object and loads the world.
	 * It also subscribes to the MapTransitionEventType.
	 *
	 * @param gameData The game data.
	 */
	@Override
	public void start(GameData gameData) {
		EventManager eventManager = EventManager.getInstance();
		if (this.mapTransitionHandler == null) {
			this.mapTransitionHandler = new MapTransitionHandler();
		}
		eventManager.subscribe(MapTransitionEventType.class, mapTransitionHandler);

		Map map = Map.getInstance();
		gameData.getGameWorld().setMap(map.getCurrentMap());
		gameData.getGameWorld().setMapSize(GameWorld.TILE_SIZE * 16, GameWorld.TILE_SIZE * 11);
	}

	/**
	 * This method is called when the game is stopped.
	 * It sets the MapProcessingService and the Map object to null.
	 *
	 * @param gameData The game data.
	 */
	@Override
	public void stop(GameData gameData) {
		EventManager eventManager = EventManager.getInstance();
		eventManager.unsubscribe(MapTransitionEventType.class, mapTransitionHandler);

		this.mapTransitionHandler = null;
	}
}
