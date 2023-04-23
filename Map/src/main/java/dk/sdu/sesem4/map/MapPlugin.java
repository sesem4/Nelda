package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.process.Priority;

/**
 * The MapPlugin class is the entry point for the map module.
 */
public class MapPlugin implements PluginServiceSPI, ProcessingServiceSPI, PostProcessingServiceSPI {

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
		this.mapProcessingService.loadWorld("overworld", 16, 8);
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


	/**
	 * This method is called after the process method.
	 * @param gameData The game data
	 * @param priority The priority, which is to be run for the current process round
	 */
	@Override
	public void postProcess(GameData gameData, Priority priority) {
		this.mapProcessingService.postProcess(gameData, priority);
	}

	/**
	 * This method sets the current map in the game data. If there is a MapTransition event,
	 * it changes the current map index to reflect that.
	 *
	 * @param gameData The game data.
	 * @param priority The priority of the process.
	 */
	@Override
	public void process(GameData gameData, Priority priority) {
		this.mapProcessingService.process(gameData, priority);
	}
}
