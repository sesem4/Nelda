package dk.sdu.sesem4.common.data.gamedata;

import dk.sdu.sesem4.common.data.math.Vector2;

import java.nio.file.Path;

/**
 * A class that represents the game world.
 */
public class GameWorld {
	/**
	 * The size of a tile in pixels.
	 * All tiles are squares, so only one value is needed.
	 */
	public static final int TILE_SIZE = 16;
	/**
	 * The default height of the game world
	 */
	private static final int defaultHeight = 11 * TILE_SIZE;
	/**
	 * The default width of the game world
	 */
	private static final int defaultWidth = 16 * TILE_SIZE;
	/**
	 * The path to the map
	 */
	private Path map;
	/**
	 * Size of current visible map
	 */
	private Vector2 mapSize;
	/**
	 * The difficulty level of the world, and in extension, the difficulty level of the enemies
	 */
	private int difficulty;
	/**
	 * The number of enemies for the current world
	 */
	private int enemyCount;

	/**
	 * Construct an empty GameWorld
	 */
	public GameWorld() {
		this(null);
	}

	/**
	 * Constructs a GameWorld
	 *
	 * @param map The path to the map
	 */
	public GameWorld(Path map) {
		this(map, null);
	}

	/**
	 * Constructs a GameWorld with a start size for the current visible map
	 *
	 * @param map The path to the map
	 * @param mapSize The size of the map, formatted as Vector2
	 */
	public GameWorld(Path map, Vector2 mapSize) {
		this(map, mapSize, 0, 0);
	}

	/**
	 * Constructs a GameWorld with a start size for the current visible map as well as difficulty and the number of enemies
	 *
	 * @param map The path to the map
	 * @param mapSize The size of the map, formatted as Vector2
	 * @param difficulty The difficulty of the world
	 * @param enemyCount The number of enemies
	 */
	public GameWorld(Path map, Vector2 mapSize, int difficulty, int enemyCount) {
		if (mapSize == null) {
			mapSize = new Vector2(defaultWidth, defaultHeight);
		}

		this.map = map;
		this.mapSize = mapSize;
		this.difficulty = difficulty;
		this.enemyCount = enemyCount;
	}

	/**
	 * Gets the path to the map
	 *
	 * @return map
	 */
	public Path getMap() {
		return map;
	}

	/**
	 * Sets the path to the map
	 *
	 * @param map the path to the map
	 */
	public void setMap(Path map) {
		this.map = map;
	}

	/**
	 * Get current map size
	 *
	 * @return Vector2 representing the map size
	 */
	public Vector2 getMapSize() {
		return mapSize;
	}

	/**
	 * Set the map size
	 *
	 * @param x representing the height
	 * @param y representing the width
	 */
	public void setMapSize(float x, float y) {
		this.mapSize.setX(x);
		this.mapSize.setY(y);
	}

	/**
	 * Set the map size
	 *
	 * @param mapSize The total size of the new map, formatted as Vector2
	 */
	public void setMapSize(Vector2 mapSize) {
		if (mapSize == null) {
			mapSize = new Vector2(defaultWidth, defaultHeight);
		}

		this.mapSize.setX(mapSize.getX());
		this.mapSize.setY(mapSize.getY());
	}

	/**
	 * Get the difficulty of the world
	 * @return Current difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * Set the difficulty of the world
	 * @param difficulty The difficulty of the world represented as a number from 0
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Get the number of enemies
	 * 
	 * @return The number of enemies
	 */
	public int getEnemyCount() {
		return enemyCount;
	}

	/**
	 * Set the number of enemies
	 * 
	 * @param enemyCount The number of enemies represented as a number from 0
	 */
	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}
}
