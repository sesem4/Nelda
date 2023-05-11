import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.moblin.blue.BlueMoblinPlugin;
import dk.sdu.sesem4.moblin.common.MoblinPostProcessor;
import dk.sdu.sesem4.moblin.common.MoblinProcessor;
import dk.sdu.sesem4.moblin.red.RedMoblinPlugin;

module Moblin {
	requires Common;

	provides PluginServiceSPI with
		RedMoblinPlugin,
		BlueMoblinPlugin;
	provides ProcessingServiceSPI with
		MoblinProcessor;
	provides PostProcessingServiceSPI with
		MoblinPostProcessor;

	exports dk.sdu.sesem4.moblin.blue;
	exports dk.sdu.sesem4.moblin.red;
	exports dk.sdu.sesem4.moblin.spawner;
}