package dk.sdu.sesem4.common.data.gamedata;

import java.nio.file.Path;

/**
 * A class that represents the game world.
 */
public class GameWorld {
	Path map;

	/**
	 * Constructs a GameWorld
	 *
	 * @param map the path to the map
	 */

	public GameWorld(Path map) {
		this.map = map;
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
}
