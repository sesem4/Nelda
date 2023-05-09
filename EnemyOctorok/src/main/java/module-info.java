import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;

module Octorok {
	requires Common;

	provides PluginServiceSPI with
		dk.sdu.sesem4.octorok.red.RedOctorokPlugin,
		dk.sdu.sesem4.octorok.blue.BlueOctorokPlugin;
	provides ProcessingServiceSPI with
		dk.sdu.sesem4.octorok.red.RedOctorokProcessor,
		dk.sdu.sesem4.octorok.blue.BlueOctorokProcessor;
	provides PostProcessingServiceSPI with
		dk.sdu.sesem4.octorok.common.OctorokPostProcessor;

	exports dk.sdu.sesem4.octorok.blue;
	exports dk.sdu.sesem4.octorok.red;
	exports dk.sdu.sesem4.octorok.spawner;
}