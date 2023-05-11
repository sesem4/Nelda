import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;

module Map {
	requires Common;
	requires ShadedLibGDX;
	
	provides PluginServiceSPI with dk.sdu.sesem4.map.MapPlugin;
	provides PostProcessingServiceSPI with dk.sdu.sesem4.map.MapPostProcessingService;

	exports dk.sdu.sesem4.map;
}