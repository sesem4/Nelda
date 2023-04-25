import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;

module Map {
    requires Common;
    requires ShadedLibGDX;

    provides PluginServiceSPI with dk.sdu.sesem4.map.MapPlugin;
    provides ProcessingServiceSPI with dk.sdu.sesem4.map.MapProcessingService;

    exports dk.sdu.sesem4.map;
}