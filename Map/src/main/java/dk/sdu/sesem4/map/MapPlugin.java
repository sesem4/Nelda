package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.event.*;

public class MapPlugin implements PluginServiceSPI {
	/**
	 * The MapPlugin class is the entry point for the map module.
	 */

	Map map;
	MapProcessingService mapProcessingService;

	@Override
	public void start(GameData gameData) {
		map = new Map();
		mapProcessingService = new MapProcessingService(map);
		mapProcessingService.loadWorld("overworld", 16, 8);
		gameData.setGameWorld(new GameWorld(mapProcessingService.getCurrentMap()));
		EventManager.getInstance().subscribe(MapTransitionEventType.class, (eventType, data) -> {
			System.out.println("Got notified!");
			switch (((MapTransitionEvent)data).getDirection()) {
				case Up:
					map.currentMapIndex -= 16;
					break;
				case Down:
					map.currentMapIndex += 16;
					break;
				case Left:
					map.currentMapIndex -= 1;
					break;
				case Right:
					map.currentMapIndex += 1;
					break;
			}
		});
	}

	@Override
	public void stop(GameData gameData) {
		mapProcessingService = null;
		map = null;
	}

	public void process(GameData gameData, Priority priority) {
		mapProcessingService.process(gameData, priority);
	}
}
