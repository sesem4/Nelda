package dk.sdu.sesem4.map;

public class MapPlugin {

    /**
     * The MapPlugin class is the entry point for the map module.
     */
    MapProcessingService mapProcessingService;
    public void createNewWorld() {
        mapProcessingService = new MapProcessingService();
        mapProcessingService.loadWorld();
    }

}
