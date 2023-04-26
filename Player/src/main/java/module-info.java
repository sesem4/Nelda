import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;

module Player {
	/**
	 * Required packages
	 */
	requires Common;
	requires ShadedLibGDX;

	/**
	 * SPI's
	 */
	provides PluginServiceSPI with dk.sdu.sesem4.player.PlayerPlugin;
	provides ProcessingServiceSPI with dk.sdu.sesem4.player.PlayerProcessingService;
	provides PostProcessingServiceSPI with dk.sdu.sesem4.player.PlayerProcessingService;

}