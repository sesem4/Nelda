package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.MapProperties;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.event.*;
import dk.sdu.sesem4.common.event.events.map.MapTransitionDoneEvent;
import dk.sdu.sesem4.common.event.events.map.MapTransitionDoneEventType;
import dk.sdu.sesem4.common.event.events.map.MapTransitionEvent;
import dk.sdu.sesem4.common.util.Direction;

/**
 * The MapProcessingService class is responsible for loading the world from the .tmx files into an array of TiledMaps.
 * It is called from the MapPlugin class when the game is started.
 */
public class MapTransitionHandler implements EventListener {
	/**
	 * Processes the MapTransition event.
	 * @param eventType Class for the event that is to be processed
	 * @param data Data for the event to be processed
	 */
	@Override
	public void processNotification(Class<? extends EventType> eventType, Event data) {
		if (!(data instanceof MapTransitionEvent)) {
			return;
		}
		MapTransitionEvent eventData = (MapTransitionEvent) data;

		Map map = Map.getInstance();
		
		Direction direction = eventData.getDirection();
		switch (direction) {
			case UP:
				map.setCurrentMapIndex(map.getCurrentMapIndex() - 16);
				break;
			case DOWN:
				map.setCurrentMapIndex(map.getCurrentMapIndex() + 16);
				break;
			case LEFT:
				map.setCurrentMapIndex(map.getCurrentMapIndex() - 1);
				break;
			case RIGHT:
				map.setCurrentMapIndex(map.getCurrentMapIndex() + 1);
				break;
		}

		eventData.getGameData().getGameWorld().setMap(map.getCurrentMap());

		loadMapData(map, eventData);

		EventManager.getInstance().notify(MapTransitionDoneEventType.class, new MapTransitionDoneEvent(eventData.getGameData()));
	}

	private void loadMapData(Map map, MapTransitionEvent eventData) {
		if (map.getCurrentTiledMap() == null) {
			return;
		}
		MapProperties properties = map.getCurrentTiledMap().getProperties();
		if (properties == null) {
			return;
		}
		try {
			eventData.getGameData().getGameWorld().setEnemyCount((int) properties.get("enemyCount"));
			eventData.getGameData().getGameWorld().setDifficulty((int) properties.get("enemyStrength"));
		} catch (Exception exception) {
			System.out.println("Could not parse map data");
			System.out.println(exception);
		}
	}
}
