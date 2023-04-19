package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.event.*;

/**
 * @author Jakob L.M. & Jon F.J.
 * The MapPlugin class is the entry point for the map module.
 */
public class MapPlugin implements PluginServiceSPI {


	Map map;
	MapProcessingService mapProcessingService;

	/**
	 * This method is called when the game is started.
	 * It creates a new Map object, a new MapProcessingService object and loads the world.
	 * It also subscribes to the MapTransitionEventType.
	 * @param gameData
	 */
	@Override
	public void start(GameData gameData) {
		map = new Map();
		mapProcessingService = new MapProcessingService(map);
		mapProcessingService.loadWorld("overworld", 16, 8);
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
	 * @param gameData
	 */
	@Override
	public void stop(GameData gameData) {
		mapProcessingService = null;
		map = null;
	}

	/**
	 * This method sets the current map in the game data. If there is a MapTransition event,
	 * it changes the current map index to reflect that.
	 *
	 * @param gameData The game data.
	 * @param priority The priority of the process.
	 */
	public void process(GameData gameData, Priority priority) {
		mapProcessingService.process(gameData, priority);
	}
}
