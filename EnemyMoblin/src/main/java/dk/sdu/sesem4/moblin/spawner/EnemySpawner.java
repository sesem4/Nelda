package dk.sdu.sesem4.moblin.spawner;

//import dk.sdu.sesem4.common.SPI.EnemySpawnerSPI;

import dk.sdu.sesem4.common.SPI.SpawnableEnemySPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.moblin.blue.BlueMoblin;
import dk.sdu.sesem4.moblin.blue.BlueMoblinPlugin;
import dk.sdu.sesem4.moblin.red.RedMoblin;
import dk.sdu.sesem4.moblin.red.RedMoblinPlugin;

public class EnemySpawner implements SpawnableEnemySPI {

	@Override
	public void spawnEnemy(GameData gameData, Vector2 coordinate) {
		// Call the overload method with a difficulty (red Moblin)
		spawnEnemy(gameData, coordinate, 1);
	}

	@Override
	public void spawnEnemy(GameData gameData, Vector2 coordinate, int difficulty) {
		switch (difficulty) {
			case 1: // red
				RedMoblinPlugin redMoblinPlugin = new RedMoblinPlugin();
				redMoblinPlugin.spawn(gameData, coordinate, RedMoblin.class);
				break;
			case 2: // blue
				BlueMoblinPlugin blueMoblinPlugin = new BlueMoblinPlugin();
				blueMoblinPlugin.spawn(gameData, coordinate, BlueMoblin.class);
				break;
		}
	}

	@Override
	public int[] getDifficulties() {
		return new int[]{1, 2};
	}
}
