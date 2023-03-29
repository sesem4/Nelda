package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.gamedata.GameData;

public interface EnemySpawnerSPI {
    /**
     * Spawn enemy into the map
     * <br><br>
     * Pre-condition: A enemy spawn is requested, and that the game has started and a suitable map and region is loaded.<br>
     * post-condition: Enemy has been spawned at x and y.
     *
     * @param gameData The game data
     * @param x The x coordinate the enemy should spawn at
     * @param y The y coordinate the enemy should spawn at
     */
    void spawnEnemy(GameData gameData, float x, float y);
}
