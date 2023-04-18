package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;

import java.nio.file.*;

/**
 * MapProcessingService loads the world from the .tmx files into an array of TiledMaps.
 * It is called from the MapPlugin class when the game is started.
 */

public class MapProcessingService implements ProcessingServiceSPI {
	Map map;

	public MapProcessingService(Map map) {
		this.map = map;
	}

	//Tiled map loader
	TmxMapLoader tmxMapLoader = new TmxMapLoader();

	/**
	 * This method just returns the current "resources" folder.
	 * It is needed, so we can do tests with a custom "resources" folder.
	 * @return the resources directory to get resource files from
	 */
	protected String getResourcesDirectory() {
		return "Map/src/main/resources/";
	}

	public void loadWorld(String worldName, int worldWidth, int worldHeight) {
		map.world = new TiledMap[worldWidth * worldHeight];

		for (int x = 0; x < worldWidth; x++) {
			for (int y = 0; y < worldHeight; y++) {
				String fileName = getFileNameForMap(worldName, x, y);
//				URL url = this.getClass().getClassLoader().getResource(worldName + "/" + columns[x] + (y + 1) + ".tmx");
//				String fileName = url.getPath();
				try {
					TiledMap loadedMap = tmxMapLoader.load(fileName);
					map.world[x + y * worldWidth] = loadedMap;
//					System.out.println("Successfully loaded " + fileName);
				} catch (Exception e) {
					map.world[x + y * worldWidth] = null;
//					System.out.println("ERROR while loading " + fileName);
				}
			}
		}
	}

	private String getFileNameForMap(String worldName, int x, int y) {
		String[] columns = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		return getResourcesDirectory() + worldName + "/" + columns[x] + (y + 1) + ".tmx";
	}

	public Path getCurrentMap() {
		String relativeFileName = getFileNameForMap(map.currentWorldName, map.currentMapIndex % 16, map.currentMapIndex / 16);
		return Paths.get(relativeFileName);
	}

	@Override
	public void process(GameData gameData, Priority priority) {
		gameData.getGameWorld().setMap(getCurrentMap());
		// get events from gameData
		// if there is a MapTransition event, we should change the current map index to reflect that.
		// then we set the new map in gameData
	}
}
