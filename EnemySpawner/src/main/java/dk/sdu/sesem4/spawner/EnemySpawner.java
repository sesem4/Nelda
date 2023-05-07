package dk.sdu.sesem4.spawner;

import dk.sdu.sesem4.common.SPI.SpawnableEnemySPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
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
	private List<Entity> enemies;

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

		if (eventData.getGameData().getGameWorld().getEnemyCount() > 0) {
			spawn(eventData);
		}
	}

	private void despawn(MapTransitionDoneEvent eventData) {
		GameEntities entities = eventData.getGameData().getGameEntities();
		this.enemies.forEach(enemy -> entities.removeEntity(enemy));
	}

	private void spawn(MapTransitionDoneEvent eventData) {
		int difficulty = eventData.getGameData().getGameWorld().getDifficulty();
		int enemyCount = eventData.getGameData().getGameWorld().getEnemyCount();

		List<SpawnableEnemySPI> enemySpawners = locateSpawnableEnemySPIByDifficulty(difficulty);

		for (int i = 0; i < enemyCount; i++) {
			SpawnableEnemySPI spawner = enemySpawners.get((int) (Math.random() * (enemySpawners.size() - 1)));
			Entity enemy = spawner.spawnEnemy(eventData.getGameData(), getRandomSpawnableLocation(eventData), difficulty);
			this.enemies.add(enemy);
		}
	}

	private List<SpawnableEnemySPI> locateSpawnableEnemySPIByDifficulty(int difficulty) {
		List<SpawnableEnemySPI> enemySpawners = new ArrayList<>();

		List<SpawnableEnemySPI> spawnerSPIs = SPILocator.locateAll(SpawnableEnemySPI.class);

		for (SpawnableEnemySPI spi : spawnerSPIs) {
			int[] difficulties = spi.getDifficulties();
			if (List.of(difficulties).contains(difficulty)) {
				enemySpawners.add(spi);
			}
		}

		return enemySpawners;
	}

	private Vector2 getRandomSpawnableLocation(MapTransitionDoneEvent eventData) {
		float height = eventData.getGameData().getGameWorld().getMapSize().getX();
		float width = eventData.getGameData().getGameWorld().getMapSize().getY();

		return new Vector2((float) (Math.random() * (width)), (float) (Math.random() * (height)));
	}
}
