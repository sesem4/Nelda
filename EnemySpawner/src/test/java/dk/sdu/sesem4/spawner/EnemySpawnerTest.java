package dk.sdu.sesem4.spawner;

import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.SPI.SpawnableEnemySPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.event.events.map.MapTransitionDoneEvent;
import dk.sdu.sesem4.common.event.events.map.MapTransitionDoneEventType;
import dk.sdu.sesem4.common.util.SPILocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
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
		// Mock
		MapTransitionDoneEvent eventData = mock(MapTransitionDoneEvent.class);
		GameData gameData = mock(GameData.class);
		when(eventData.getGameData()).thenReturn(gameData);
		GameWorld gameWorld = mock(GameWorld.class);
		when(gameData.getGameWorld()).thenReturn(gameWorld);
		when(gameWorld.getEnemyCount()).thenReturn(1);

		// Setup method calls on mocked enemy
		EnemySpawner mockedEnemySpawner = mock(EnemySpawner.class);
		doNothing().when(mockedEnemySpawner).despawn(isA(MapTransitionDoneEvent.class));
		doNothing().when(mockedEnemySpawner).spawn(isA(MapTransitionDoneEvent.class));
		doCallRealMethod().when(mockedEnemySpawner).processNotification(MapTransitionDoneEventType.class, eventData);

		// Call method
		mockedEnemySpawner.processNotification(MapTransitionDoneEventType.class, eventData);

		// Validate that the calls were correct
		verify(mockedEnemySpawner).despawn(eventData);
		verify(mockedEnemySpawner).spawn(eventData);
	}

	@Test
	void despawn() throws NoSuchFieldException, IllegalAccessException {
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
		enemySpawner.despawn(eventData);

		// Verify, that the enemy entity was endeed set to be removed
		verify(gameEntities).removeEntity(eq(entity));
	}

	@Test
	void spawn() {
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

			enemySpawner.spawn(eventData);

			verify(spawner).spawnEnemy(eq(gameData), any(Vector2.class), eq(1));
			verify(enemies).add(any(Entity.class));
		} catch (Exception exception) {
			fail();
		}
	}

	@Test
	void locateSpawnableEnemySPIByDifficulty() throws NoSuchMethodException {
		// Get method
		Method locateSpawnableEnemySPIByDifficulty = enemySpawner.getClass().getDeclaredMethod("locateSpawnableEnemySPIByDifficulty", int.class);
		locateSpawnableEnemySPIByDifficulty.setAccessible(true);

		// Generate spawners
		List<SpawnableEnemySPI> spawnableEnemySPIS = new ArrayList<>();
		int[][] data = {{1,2,3}, {1,3},{-1,0},{1,8},{2,8},{10000,2,64,-5}};
		SpawnableEnemySPI[] correct = new SpawnableEnemySPI[3];
		for (int[] datum : data) {
			SpawnableEnemySPI spawner = mock(SpawnableEnemySPI.class);
			when(spawner.getDifficulties()).thenReturn(datum);
			spawnableEnemySPIS.add(spawner);
		}

		// Overwrite SPI locator
		try (MockedStatic<SPILocator> dummy = Mockito.mockStatic(SPILocator.class)) {
			// Set output of SPI locator
			dummy.when(() -> SPILocator.locateAll(SpawnableEnemySPI.class)).thenReturn(spawnableEnemySPIS);

			// Run method
			int targetDifficulty = 2;
			List<SpawnableEnemySPI> spawnerList = (List<SpawnableEnemySPI>) locateSpawnableEnemySPIByDifficulty.invoke(enemySpawner, targetDifficulty);

			// Verify that the method returned the correct data
			assertEquals(3, spawnerList.size());
			spawnerList.forEach(spawner -> {
				int[] difficulties = spawner.getDifficulties();
				for (int i : difficulties) {
					if (i == targetDifficulty) {
						assertTrue(true);
						return;
					}
				}
				fail();
			});
		} catch (Exception exception) {
			fail();
		}
	}

	@Test
	void getRandomSpawnableLocation() throws NoSuchMethodException {
		// Get method
		Method getRandomSpawnableLocation = enemySpawner.getClass().getDeclaredMethod("getRandomSpawnableLocation", MapTransitionDoneEvent.class);
		getRandomSpawnableLocation.setAccessible(true);

		// Setup mocked data for eventData
		MapTransitionDoneEvent eventData = mock(MapTransitionDoneEvent.class);
		GameData gameData = mock(GameData.class);
		when(eventData.getGameData()).thenReturn(gameData);
		GameWorld gameWorld = mock(GameWorld.class);
		when(gameData.getGameWorld()).thenReturn(gameWorld);
		Vector2 mapSize = mock(Vector2.class);
		when(gameWorld.getMapSize()).thenReturn(mapSize);
		when(mapSize.getX()).thenReturn(100f);
		when(mapSize.getY()).thenReturn(100f);

		Vector2 vector2 = mock(Vector2.class);
		when(vector2.getX()).thenReturn(25f);
		when(vector2.getY()).thenReturn(50f);

		MapSPI mapUtility = mock(MapSPI.class);
		when(mapUtility.getRandomPassableTile()).thenReturn(vector2);

		// Overwrite SPI locator
		try (MockedStatic<SPILocator> dummy = Mockito.mockStatic(SPILocator.class)) {
			// Set output of SPI locator
			List<MapSPI> mapSPIS = new ArrayList<>();
			dummy.when(() -> SPILocator.locateAll(MapSPI.class)).thenReturn(mapSPIS);

			// Run method (1. type - No map utility)
			int targetDifficulty = 2;
			Vector2 position = (Vector2) getRandomSpawnableLocation.invoke(enemySpawner, eventData);
			assertEquals(Vector2.class, position.getClass());
			assertTrue(position.getX() >= 0 && position.getX() <= 100f);
			assertTrue(position.getY() >= 0 && position.getY() <= 100f);

			// Run method (2. type - Map utility present)
			mapSPIS.add(mapUtility);
			position = (Vector2) getRandomSpawnableLocation.invoke(enemySpawner, eventData);
			assertEquals(vector2.getX() * GameWorld.TILE_SIZE, position.getX());
			assertEquals(vector2.getY() * GameWorld.TILE_SIZE, position.getY());
		} catch (Exception exception) {
			fail();
		}
	}
}