package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;

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
        map = createNewMap();
        gameData.setGameWorld(new GameWorld(map.world[119]));
    }

    @Override
    public void stop(GameData gameData) {
        mapProcessingService = null;
        map = null;
    }

}
