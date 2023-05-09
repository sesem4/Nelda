package dk.sdu.sesem4.spawner;

import dk.sdu.sesem4.common.SPI.SpawnableEnemySPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.event.EventManager;
import dk.sdu.sesem4.common.event.events.map.MapTransitionDoneEvent;
import dk.sdu.sesem4.common.event.events.map.MapTransitionDoneEventType;
import dk.sdu.sesem4.common.event.events.map.MapTransitionEventType;
import dk.sdu.sesem4.common.util.SPILocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnemySpawnerTest {
	EnemySpawner enemySpawner;

	@BeforeEach
	void setUp() {
		enemySpawner = EnemySpawner.getInstance();
	}

	@Test
	void getInstance() {

	}

	@Test
	void processNotification() {
	}

	@Test
	void despawn() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		// Get method
		Method despawn = enemySpawner.getClass().getDeclaredMethod("despawn", MapTransitionDoneEvent.class);
		despawn.setAccessible(true);

		// Setup mocked data
		MapTransitionDoneEvent eventData = mock(MapTransitionDoneEvent.class);
		GameData gameData = mock(GameData.class);
		when(eventData.getGameData()).thenReturn(gameData);
		GameEntities gameEntities = mock(GameEntities.class);
		when(gameData.getGameEntities()).thenReturn(gameEntities);

		Entity entity = mock(Entity.class);
		List<Entity> enemies = new ArrayList<>();
		enemies.add(entity);

		// Set enemies
		Field enemiesField = enemySpawner.getClass().getDeclaredField("enemies");
		enemiesField.setAccessible(true);
		enemiesField.set(enemySpawner, enemies);

		// Run the despawn command
		despawn.invoke(enemySpawner, eventData);

		// Verify, that the enemy entity was endeed set to be removed
		verify(gameEntities).removeEntity(eq(entity));
	}

	@Test
	void spawn() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
		// Get method
		Method spawn = enemySpawner.getClass().getDeclaredMethod("spawn", MapTransitionDoneEvent.class);
		spawn.setAccessible(true);

		// Setup mocked data
		MapTransitionDoneEvent eventData = mock(MapTransitionDoneEvent.class);
		GameData gameData = mock(GameData.class);
		when(eventData.getGameData()).thenReturn(gameData);
		GameWorld gameWorld = mock(GameWorld.class);
		when(gameData.getGameWorld()).thenReturn(gameWorld);
		when(gameWorld.getDifficulty()).thenReturn(1);
		when(gameWorld.getEnemyCount()).thenReturn(1);
		Vector2 mapSize = mock(Vector2.class);
		when(gameWorld.getMapSize()).thenReturn(mapSize);
		when(mapSize.getX()).thenReturn(100f);
		when(mapSize.getY()).thenReturn(100f);

		ArrayList<Entity> enemies = mock(ArrayList.class);

		Entity enemy = mock(Entity.class);

		SpawnableEnemySPI spawner = mock(SpawnableEnemySPI.class);
		int[] data = {1};
		when(spawner.getDifficulties()).thenReturn(data);
		when(spawner.spawnEnemy(eq(gameData), any(Vector2.class), eq(1))).thenReturn(enemy);


		List<SpawnableEnemySPI> spawnableEnemySPIS = new ArrayList<>();
		spawnableEnemySPIS.add(spawner);

		try (MockedStatic<SPILocator> dummy = Mockito.mockStatic(SPILocator.class)) {
			dummy.when(() -> SPILocator.locateAll(SpawnableEnemySPI.class)).thenReturn(spawnableEnemySPIS);

			// Set enemies
			Field enemiesField = enemySpawner.getClass().getDeclaredField("enemies");
			enemiesField.setAccessible(true);
			enemiesField.set(enemySpawner, enemies);

			spawn.invoke(enemySpawner, eventData);

			verify(spawner).spawnEnemy(eq(gameData), any(Vector2.class), eq(1));
			verify(enemies).add(any(Entity.class));
		} catch (Exception exception) {
			fail();
		}
	}

	@Test
	void locateSpawnableEnemySPIByDifficulty() {
	}

	@Test
	void getRandomSpawnableLocation() {
	}
}