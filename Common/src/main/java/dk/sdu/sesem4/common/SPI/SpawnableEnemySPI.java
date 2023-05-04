package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;

public interface SpawnableEnemySPI {
	/**
	 * Spawn enemy into the map with difficulty default.
	 * <br>
	 * <br>
	 * Pre-condition: A enemy spawn is requested, and the game has started and a suitable map and region are loaded.<br>
	 * post-condition: Enemy has been spawned at x and y with default difficulty.
	 *
	 * @param gameData The game data
	 * @param coordinate The x and y coordinate the enemy should spawn at, formatted as a Vector2
	 */
	void spawnEnemy(GameData gameData, Vector2 coordinate);

	/**
	 * Spawn enemy into the map with specified difficulty.
	 * <br>
	 * <br>
	 * Pre-condition: A enemy spawn is requested, and the game has started and a suitable map and region are loaded.<br>
	 * post-condition: Enemy has been spawned at x and y with specified difficulty if the difficulty is possible.
	 *
	 * @param gameData The game data
	 * @param coordinate The x and y coordinate the enemy should spawn at, formatted as a Vector2
	 * @param difficulty The difficulty of the enemy.
	 */
	void spawnEnemy(GameData gameData, Vector2 coordinate, int difficulty);

	/**
	 * Get the difficulties that are possible by the enemy.
	 * <br>
	 * <br>
	 * Pre-condition: A game is running, and the component is available.<br>
	 * post-condition: Difficulties for the enemy have been provided.
	 *
	 * @return Int array, that represents the difficulties possible by the enemy.
	 */
	int[] getDifficulties();
}
