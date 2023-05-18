package dk.sdu.sesem4.moblin.spawner;

//import dk.sdu.sesem4.common.SPI.EnemySpawnerSPI;

import dk.sdu.sesem4.common.SPI.SpawnableEnemySPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.moblin.blue.BlueMoblin;
import dk.sdu.sesem4.moblin.blue.BlueMoblinPlugin;
import dk.sdu.sesem4.moblin.red.RedMoblin;
import dk.sdu.sesem4.moblin.red.RedMoblinPlugin;

public class EnemySpawner implements SpawnableEnemySPI {

	@Override
	public Entity spawnEnemy(GameData gameData, Vector2 coordinate) {
		// Call the overload method with a difficulty (red Moblin)
		return spawnEnemy(gameData, coordinate, 3);
	}

	@Override
	public Entity spawnEnemy(GameData gameData, Vector2 coordinate, int difficulty) {
		switch (difficulty) {
			case 3: // red
				RedMoblinPlugin redMoblinPlugin = new RedMoblinPlugin();
				return redMoblinPlugin.spawn(gameData, coordinate, RedMoblin.class);
			case 4: // blue
				BlueMoblinPlugin blueMoblinPlugin = new BlueMoblinPlugin();
				return blueMoblinPlugin.spawn(gameData, coordinate, BlueMoblin.class);
		}

		return null;
	}

	@Override
	public int[] getDifficulties() {
		return new int[]{3, 4};
	}
}
