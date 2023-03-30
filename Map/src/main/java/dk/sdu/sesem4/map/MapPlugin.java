package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class MapPlugin implements PluginServiceSPI {
    /**
     * The MapPlugin class is the entry point for the map module.
     */

    Map map;
    MapProcessingService mapProcessingService;

    public Map createNewMap() {
        map = new Map();
        mapProcessingService = new MapProcessingService();
        map.world = mapProcessingService.loadWorld("overworld", 16, 8);
        return map;
    }

    @Override
    public void start(GameData gameData) {
        mapProcessingService = new MapProcessingService();
        mapProcessingService.loadWorld("world", 10, 10);
    }

    @Override
    public void stop(GameData gameData) {
        mapProcessingService = null;
        map = null;
    }

}
