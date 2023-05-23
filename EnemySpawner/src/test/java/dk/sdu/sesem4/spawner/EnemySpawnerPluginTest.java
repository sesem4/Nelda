package dk.sdu.sesem4.spawner;

import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.event.EventManager;
import dk.sdu.sesem4.common.event.events.map.MapTransitionDoneEventType;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class EnemySpawnerPluginTest {

	@org.junit.jupiter.api.Test
	void start() {
		EventManager eventManager = mock(EventManager.class);
		try (MockedStatic<EventManager> dummy = Mockito.mockStatic(EventManager.class)) {
			dummy.when(() -> EventManager.getInstance()).thenReturn(eventManager);

			EnemySpawnerPlugin enemySpawnerPlugin = new EnemySpawnerPlugin();

			enemySpawnerPlugin.start(mock(GameData.class));

			verify(eventManager).subscribe(eq(MapTransitionDoneEventType.class), any(EnemySpawner.class));
		}
	}

	@org.junit.jupiter.api.Test
	void stop() {
		EventManager eventManager = mock(EventManager.class);
		try (MockedStatic<EventManager> dummy = Mockito.mockStatic(EventManager.class)) {
			dummy.when(() -> EventManager.getInstance()).thenReturn(eventManager);

			EnemySpawnerPlugin enemySpawnerPlugin = new EnemySpawnerPlugin();

			enemySpawnerPlugin.stop(mock(GameData.class));

			verify(eventManager).unsubscribe(eq(MapTransitionDoneEventType.class), any(EnemySpawner.class));
		}
	}
}