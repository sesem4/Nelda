package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.event.*;
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
	}
}
