import dk.sdu.sesem4.common.SPI.PluginServiceSPI;

module Octorok {
	requires Common;

	provides PluginServiceSPI with
			dk.sdu.sesem4.octorok.red.RedOctorokPlugin,
			dk.sdu.sesem4.octorok.blue.BlueOctorokPlugin;

	exports dk.sdu.sesem4.octorok.blue;
	exports dk.sdu.sesem4.octorok.red;
	exports dk.sdu.sesem4.octorok.spawner;
}