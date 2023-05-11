package dk.sdu.sesem4.spawner;

import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.SPI.SpawnableEnemySPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.event.Event;
import dk.sdu.sesem4.common.event.EventListener;
import dk.sdu.sesem4.common.event.EventType;
import dk.sdu.sesem4.common.event.events.map.MapTransitionDoneEvent;
import dk.sdu.sesem4.common.util.SPILocator;

import java.util.*;

/**
 * Singleton class, that handles the process of de-spawning and spawning enemies on map change
 *
 * @author Mikkel Albrechtsen
 */
public class EnemySpawner implements EventListener {
	/** Enemy spawner instance - Singleton */
	private static EnemySpawner instance;
	/** Spawned enemies */
	protected List<Entity> enemies;

	private EnemySpawner() {
		this.enemies = new LinkedList<>();
	}

	public static EnemySpawner getInstance() {
		if (instance == null) {
			instance = new EnemySpawner();
		}
		return instance;
	}

	@Override
	public void processNotification(Class<? extends EventType> eventType, Event data) {
		if (!(data instanceof MapTransitionDoneEvent)) {
			return;
		}

		MapTransitionDoneEvent eventData = (MapTransitionDoneEvent) data;

		despawn(eventData);

		System.out.println(eventData.getGameData().getGameWorld().getEnemyCount());
		if (eventData.getGameData().getGameWorld().getEnemyCount() > 0) {
			spawn(eventData);
		}
	}

	/**
	 * Despawn all enemies spawned through this system.
	 *
	 * @param eventData Event data, for the Map transition done event.
	 */
	protected void despawn(MapTransitionDoneEvent eventData) {
		GameEntities entities = eventData.getGameData().getGameEntities();
		this.enemies.forEach(enemy -> entities.removeEntity(enemy));
	}

	/**
	 * Spawn enemies based on the current world.
	 *
	 * @param eventData Event data, for the Map transition done event.
	 */
	protected void spawn(MapTransitionDoneEvent eventData) {
		int difficulty = eventData.getGameData().getGameWorld().getDifficulty();
		int enemyCount = eventData.getGameData().getGameWorld().getEnemyCount();

		List<SpawnableEnemySPI> enemySpawners = locateSpawnableEnemySPIByDifficulty(difficulty);

		System.out.println(Arrays.toString(enemySpawners.toArray()));
		if (enemySpawners.size() == 0) {
			return; // No enemy spawners found for the difficulty, and enemy spawning is therefore halted.
		}

		for (int i = 0; i < enemyCount; i++) {
			SpawnableEnemySPI spawner = enemySpawners.get((int) (Math.random() * (enemySpawners.size() - 1)));
			Entity enemy = spawner.spawnEnemy(eventData.getGameData(), getRandomSpawnableLocation(eventData), difficulty);
			this.enemies.add(enemy);
		}
	}

	/**
	 * Locate spawnable enemy SPI by their difficulty.
	 *
	 * @param difficulty The difficulty to search for.
	 *
	 * @return List of SPI's that allow for enemy spawning for the wanted difficulty
	 */
	protected List<SpawnableEnemySPI> locateSpawnableEnemySPIByDifficulty(int difficulty) {
		List<SpawnableEnemySPI> enemySpawners = new ArrayList<>();

		List<SpawnableEnemySPI> spawnerSPIs = SPILocator.locateAll(SpawnableEnemySPI.class);

		for (SpawnableEnemySPI spi : spawnerSPIs) {
			int[] difficulties = spi.getDifficulties();
			for (int i : difficulties) {
				if (i == difficulty) {
					enemySpawners.add(spi);
					break;
				}
			}
		}

		return enemySpawners;
	}

	/**
	 * Locate a random position in the world, to spawn the enemy at.
	 *
	 * @param eventData Event data, for the Map transition done event.
	 *
	 * @return Vector2 position, that correlates to a spawnable world position.
	 */
	protected Vector2 getRandomSpawnableLocation(MapTransitionDoneEvent eventData) {
		List<MapSPI> mapUtilities = SPILocator.locateAll(MapSPI.class);

		Vector2 mapSize = eventData.getGameData().getGameWorld().getMapSize();

		// If no map SPI is available, use just a random position inside the world.
		if (mapUtilities.size() == 0) {
			float x = (float) (Math.random() * (mapSize.getX()));
			float y = (float) (Math.random() * (mapSize.getY()));

			return new Vector2(x, y);
		}

		Vector2 randomTileCoordinate = mapUtilities.get(0).getRandomPassableTile(eventData.getGameData());

		float x = randomTileCoordinate.getX() * GameWorld.TILE_SIZE;
		float y = randomTileCoordinate.getY() * GameWorld.TILE_SIZE;

		return new Vector2(x, y);
	}
}
