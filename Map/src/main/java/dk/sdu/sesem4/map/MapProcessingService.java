package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;

/**
 * MapProcessingService loads the world from the .tmx files into an array of TiledMaps.
 * It is called from the MapPlugin class when the game is started.
 */

public class MapProcessingService implements ProcessingServiceSPI {

	//Array of TiledMaps
	TiledMap[] world;
	//Tiled map loader
	TmxMapLoader tmxMapLoader = new TmxMapLoader();
	int currentMapIndex = 119;

	/**
	 * This method just returns the current "resources" folder.
	 * It is needed, so we can do tests with a custom "resources" folder.
	 * @return the resources directory to get resource files from
	 */
	protected String getResourcesDirectory() {
		return "Map/src/main/resources/";
	}

	public TiledMap[] loadWorld(String worldName, int worldWidth, int worldHeight) {
		world = new TiledMap[worldWidth * worldHeight];
		String[] columns = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		for (int x = 0; x < worldWidth; x++) {
			for (int y = 0; y < worldHeight; y++) {
				String fileName = getResourcesDirectory() + worldName + "/" + columns[x] + (y + 1) + ".tmx";
				try {
					TiledMap map = tmxMapLoader.load(fileName);
					world[x + y * worldWidth] = map;
				} catch (Exception e) {
					world[x + y * worldWidth] = null;
				}
			}
		}
		return world;
	}

	public TiledMap getCurrentMap() {
		return world[currentMapIndex];
	}

	@Override
	public void process(GameData gameData, Priority priority) {
		// get events from gameData
		// if there is a MapTransition event, we should change the current map index to reflect that.
		// then we set the new map in gameData
	}
}
