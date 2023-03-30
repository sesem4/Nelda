package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class MapPlugin implements PluginServiceSPI {

    /**
     * The MapPlugin class is the entry point for the map module.
     */
    MapProcessingService mapProcessingService;
    @Override
    public void start(GameData gameData) {
        mapProcessingService = new MapProcessingService();
        mapProcessingService.loadWorld("overworld", 16, 8);
    }

    @Override
    public void stop(GameData gameData) {

    }
}
