package dk.sdu.sesem4.common.data.gamedata;

import dk.sdu.sesem4.common.data.math.Vector2;

import java.nio.file.Path;

/**
 * A class that represents the game world.
 */
public class GameWorld {
	/**
	 * The path to the map
	 */
	private Path map;
	/**
	 * Size of current visible map
	 */
	private Vector2 mapSize;

	/**
	 * Construct an empty GameWorld
	 */
	public GameWorld() {
		this(null);
	}

	/**
	 * Constructs a GameWorld
	 *
	 * @param map the path to the map
	 */
	public GameWorld(Path map) {
		this(map, new Vector2());
	}
	/**
	 * Constructs a GameWorld with a start size for the current visible map
	 *
	 * @param map the path to the map
	 */
	public GameWorld(Path map, Vector2 mapSize) {
		if (mapSize == null) {
			mapSize = new Vector2();
		}

		this.map = map;
		this.mapSize = mapSize;
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
}
