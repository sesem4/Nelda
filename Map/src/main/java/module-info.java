import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.map.MapUtil;

module Map {
	requires Common;
	requires ShadedLibGDX;
	
	provides PluginServiceSPI with dk.sdu.sesem4.map.MapPlugin;
	provides PostProcessingServiceSPI with dk.sdu.sesem4.map.MapPostProcessingService;
	provides MapSPI with MapUtil;

	exports dk.sdu.sesem4.map;
}