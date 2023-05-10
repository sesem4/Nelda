package dk.sdu.sesem4.octorok.red;

import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.octorok.common.OctorokPlugin;
import dk.sdu.sesem4.octorok.spawner.EnemySpawner;

public class RedOctorokPlugin extends OctorokPlugin {
	public RedOctorokPlugin() {
		super();

		// Set the red Octorok's stats
		super.setHearts(1);
		super.setDamage(1);
	}

//	@Override
//	public void start(GameData gameData) {
//		EnemySpawner enemySpawner = new EnemySpawner();
//		enemySpawner.spawnEnemy(gameData, new Vector2(5 * 16 + 1, 6 * 16 + 1));
//	}
}
