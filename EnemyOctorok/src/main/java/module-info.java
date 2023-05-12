import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.SpawnableEnemySPI;
import dk.sdu.sesem4.octorok.blue.BlueOctorokPlugin;
import dk.sdu.sesem4.octorok.common.OctorokPostProcessor;
import dk.sdu.sesem4.octorok.common.OctorokProcessor;
import dk.sdu.sesem4.octorok.red.RedOctorokPlugin;
import dk.sdu.sesem4.octorok.spawner.EnemySpawner;

module Octorok {
	requires Common;

	provides PluginServiceSPI with
			RedOctorokPlugin,
			BlueOctorokPlugin;
	provides ProcessingServiceSPI with OctorokProcessor;
	provides PostProcessingServiceSPI with OctorokPostProcessor;
	provides SpawnableEnemySPI with EnemySpawner;

	exports dk.sdu.sesem4.octorok.blue;
	exports dk.sdu.sesem4.octorok.red;
	exports dk.sdu.sesem4.octorok.spawner;
}