package dk.sdu.sesem4.octorok.spawner;

//import dk.sdu.sesem4.common.SPI.EnemySpawnerSPI;

import dk.sdu.sesem4.common.SPI.SpawnableEnemySPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.octorok.blue.BlueOctorok;
import dk.sdu.sesem4.octorok.blue.BlueOctorokPlugin;
import dk.sdu.sesem4.octorok.red.RedOctorok;
import dk.sdu.sesem4.octorok.red.RedOctorokPlugin;

public class EnemySpawner implements SpawnableEnemySPI {

	@Override
	public Entity spawnEnemy(GameData gameData, Vector2 coordinate) {
		// Call the overload method with a difficulty (red Octorok)
		return spawnEnemy(gameData, coordinate, 1);
	}

	@Override
	public Entity spawnEnemy(GameData gameData, Vector2 coordinate, int difficulty) {
		switch (difficulty) {
			case 1: // red
				RedOctorokPlugin redOctorokPlugin = new RedOctorokPlugin();
				return redOctorokPlugin.spawn(gameData, coordinate, RedOctorok.class);
			case 2: // blue
				BlueOctorokPlugin blueOctorokPlugin = new BlueOctorokPlugin();
				return blueOctorokPlugin.spawn(gameData, coordinate, BlueOctorok.class);
		}

		return null;
	}

	@Override
	public int[] getDifficulties() {
		return new int[]{1, 2};
	}
}
