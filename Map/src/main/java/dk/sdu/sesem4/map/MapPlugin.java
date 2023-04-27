package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;

/**
 * The MapPlugin class is the entry point for the map module.
 */
public class MapPlugin implements PluginServiceSPI{

	/**
	 * The MapProcessingService object that is used to process the map data.
	 */
	private MapProcessingService mapProcessingService;

	/**
	 * This method is called when the game is started.
	 * It creates a new Map object, a new MapProcessingService object and loads the world.
	 * It also subscribes to the MapTransitionEventType.
	 * @param gameData The game data.
	 */
	@Override
	public void start(GameData gameData) {
		this.mapProcessingService = new MapProcessingService();
		gameData.setGameWorld(new GameWorld(mapProcessingService.getCurrentMap()));
	}

	/**
	 * This method is called when the game is stopped.
	 * It sets the MapProcessingService and the Map object to null.
	 * @param gameData The game data.
	 */
	@Override
	public void stop(GameData gameData) {
		this.mapProcessingService = null;
	}
}
