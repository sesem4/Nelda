package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
/**
 * MapProcessingService loads the world from the .tmx files into an array of TiledMaps.
 * It is called from the MapPlugin class when the game is started.
 */
public class MapProcessingService {

    //Array of TiledMaps
    TiledMap[] world;
    //Tiled map loader
    TmxMapLoader tmxMapLoader = new TmxMapLoader();
    //World size

    public TiledMap[] loadWorld() {
        int worldWidth = 16;
        int worldHeight = 8;
        world = new TiledMap[worldWidth * worldHeight];
        String[] columns = new String[]{"A", "B", "C", "D", "E", "F", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
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
        return world;
    }
}
