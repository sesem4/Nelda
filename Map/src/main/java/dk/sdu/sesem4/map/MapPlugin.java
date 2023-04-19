package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.event.*;

/**
 * The MapPlugin class is the entry point for the map module.
 */
public class MapPlugin implements PluginServiceSPI {
	/**
	 * The Map object that is used to store the map data.
	 */
	private Map map;

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
		this.map = new Map();
		this.mapProcessingService = new MapProcessingService(map);
		this.mapProcessingService.loadWorld("overworld", 16, 8);
		gameData.setGameWorld(new GameWorld(mapProcessingService.getCurrentMap()));
		EventManager.getInstance().subscribe(MapTransitionEventType.class, (eventType, data) -> {
			System.out.println("Got notified!");
			switch (((MapTransitionEvent)data).getDirection()) {
				case UP:
					map.currentMapIndex -= 16;
					break;
				case DOWN:
					map.currentMapIndex += 16;
					break;
				case LEFT:
					map.currentMapIndex -= 1;
					break;
				case RIGHT:
					map.currentMapIndex += 1;
					break;
			}
		});
	}

	/**
	 * This method is called when the game is stopped.
	 * It sets the MapProcessingService and the Map object to null.
	 * @param gameData The game data.
	 */
	@Override
	public void stop(GameData gameData) {
		this.mapProcessingService = null;
		this.map = null;
	}

	/**
	 * This method sets the current map in the game data. If there is a MapTransition event,
	 * it changes the current map index to reflect that.
	 *
	 * @param gameData The game data.
	 * @param priority The priority of the process.
	 */
	public void process(GameData gameData, Priority priority) {
		this.mapProcessingService.process(gameData, priority);
	}
}
