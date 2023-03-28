package dk.sdu.sesem4.map;

class MapPluginTest {

    /**
     * The test MapPlugin class is the entry point for the map module.
     * This test is to check if the map is created through this class correctly.
     */
    @org.junit.jupiter.api.Test
    void createNewWorld() {
        MapProcessingServiceTest mapProcessingServiceTest = new MapProcessingServiceTest();
        mapProcessingServiceTest.loadWorld();

    }
}