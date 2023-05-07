package dk.sdu.sesem4.spawner;

import dk.sdu.sesem4.common.event.Event;
import dk.sdu.sesem4.common.event.EventListener;
import dk.sdu.sesem4.common.event.EventType;

/**
 * Singleton class, that handles the process of de-spawning and spawning enemies on map change
 *
 * @author Mikkel Albrechtsen
 */
public class EnemySpawner implements EventListener {
	/** Enemy spawner instance - Singleton */
	private static EnemySpawner instance;

	private EnemySpawner() {}

	public static EnemySpawner getInstance() {
		if (instance == null) {
			instance = new EnemySpawner();
		}
		return instance;
	}

	@Override
	public void processNotification(Class<? extends EventType> eventType, Event data) {

	}
}
