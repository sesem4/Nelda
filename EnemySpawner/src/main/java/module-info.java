import dk.sdu.sesem4.common.SPI.PluginServiceSPI;

module EnemySpawner {
	requires Common;

	provides PluginServiceSPI with dk.sdu.sesem4.spawner.EnemySpawnerPlugin;
}