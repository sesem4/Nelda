package dk.sdu.sesem4.map;

<<<<<<< HEAD
import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class MapPlugin implements PluginServiceSPI {
=======
public class MapPlugin {
>>>>>>> origin/feature/21/create-region-screens

    /**
     * The MapPlugin class is the entry point for the map module.
     */
    MapProcessingService mapProcessingService;
<<<<<<< HEAD
    @Override
    public void start(GameData gameData) {
=======
    public void createNewWorld() {
>>>>>>> origin/feature/21/create-region-screens
        mapProcessingService = new MapProcessingService();
        mapProcessingService.loadWorld("overworld", 16, 8);
    }

<<<<<<< HEAD
    @Override
    public void stop(GameData gameData) {

    }
=======
>>>>>>> origin/feature/21/create-region-screens
}
