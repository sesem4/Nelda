package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
<<<<<<< HEAD
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;

=======
>>>>>>> origin/feature/21/create-region-screens
/**
 * MapProcessingService loads the world from the .tmx files into an array of TiledMaps.
 * It is called from the MapPlugin class when the game is started.
 */
<<<<<<< HEAD
public class MapProcessingService implements PostProcessingServiceSPI {
=======
public class MapProcessingService {
>>>>>>> origin/feature/21/create-region-screens

    //Array of TiledMaps
    TiledMap[] world;
    //Tiled map loader
    TmxMapLoader tmxMapLoader = new TmxMapLoader();
    //World size

    public TiledMap[] loadWorld(String worldName, int worldWidth, int worldHeight) {
        world = new TiledMap[worldWidth * worldHeight];
        String[] columns = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 0; y < worldHeight; y++) {
                try {
                    String fileName = worldName + "/" + columns[x] + (y + 1) + ".tmx";
                    TiledMap map = tmxMapLoader.load(fileName);
                    world[x + y * worldWidth] = map;
                } catch (Exception e) {
                    world[x + y * worldWidth] = null;
                }
            }
        }
        return world;
    }
<<<<<<< HEAD

    @Override
    public void postProcess(GameData gameData, Priority priority) {

    }
=======
>>>>>>> origin/feature/21/create-region-screens
}
