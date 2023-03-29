package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.junit.jupiter.api.Test;

/**
 * This test makes sure that a map is loaded into the array and returns the array of maps.
 */
class MapProcessingServiceTest {

    @Test
    void loadWorld() {
        TiledMap[] world;
        TmxMapLoader tmxMapLoader = new TmxMapLoader();
        int worldWidth = 1;
        int worldHeight = 1;
        world = new TiledMap[worldWidth * worldHeight];
        String[] columns = new String[]{"A"};
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                try {
                    String fileName = columns[x] + (y + 1) + ".tmx";
                    TiledMap map = tmxMapLoader.load(fileName);
                    world[x + y * worldWidth] = map;
                } catch (Exception e) {
                    world[x + y * worldWidth] = null;
                }
            }
        }
        assert world[0] != null;


    }
}