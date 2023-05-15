package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * @author Jakob L.M. & Jon F.J.
 * The Map class represents a map in the game. It contains an array of TiledMaps,
 * the starting map index, the current world name, and the current map index.
 */
public class Map {
	protected final int STARTING_MAP_INDEX = 119;
	protected String currentWorldName = "overworld";
	protected int currentMapIndex = STARTING_MAP_INDEX;

	protected HashMap<Path, TiledMap> cachedTiledMaps;

	protected static Map instance;

	protected Map() {
		this.cachedTiledMaps = new HashMap<>();
	}

	public static Map getInstance() {
		if (instance == null) {
			instance = new Map();
		}
		return instance;
	}

	public String getCurrentWorldName() {
		return this.currentWorldName;
	}

	public void setCurrentWorldName(String currentWorldName) {
		this.currentWorldName = currentWorldName;
	}

	public int getCurrentMapIndex() {
		return this.currentMapIndex;
	}

	public void setCurrentMapIndex(int currentMapIndex) {
		this.currentMapIndex = currentMapIndex;
	}

	/**
	 * This method just returns the current "resources" folder.
	 * It is needed, so we can do tests with a custom "resources" folder.
	 * @return the resources directory to get resource files from
	 */
	protected String getResourcesDirectory() {
		return "Map/src/main/resources/";
	}

	/**
	 * This method is used to get the file name for a given map. It takes three parameters:
	 * The file name is constructed from the worldName parameter, the x and y coordinates,
	 * and an array of letters that are used to represent the columns of the map.
	 * The method first calls the getResourcesDirectory method to get the "resources" directory,
	 * and then appends the file name to it.
	 * @param worldName: a string that represents the name of the world to load.
	 * @param x: an integer that represents the x-coordinate of the map.
	 * @param y: an integer that represents the y-coordinate of the map.
	 * @return the path to the map's tmx file.
	 */
	protected Path getPathForMap(String worldName, int x, int y) {
		char[] columns = new char[26];
		for(int i = 0; i < columns.length; i++){
			columns[i] = (char) ('A' + i);
		}

		String fileName = getResourcesDirectory() + worldName + "/" + columns[x] + (y + 1) + ".tmx";
//		URL url = this.getClass().getClassLoader().getResource(worldName + "/" + columns[x] + (y + 1) + ".tmx");
//		String fileName = url.getPath();
		return Paths.get(fileName);
	}

	/**
	 * Gets the path to the current tmx file.
	 * @return Path to the current tmx file.
	 */
	public Path getCurrentMap() {
		return getPathForMap(this.getCurrentWorldName(), this.getCurrentMapIndex() % 16, this.getCurrentMapIndex() / 16);
	}

	/**
	 * Returns the TiledMap for the current map index
	 * @return TiledMap for the current map index
	 */
	public TiledMap getCurrentTiledMap() {
		Path path = getCurrentMap();

		if (cachedTiledMaps.containsKey(path)) {
			return cachedTiledMaps.get(path);
		}

		TiledMap map = new TmxMapLoader().load(path.toString());
		cachedTiledMaps.put(path, map);
		return map;
	}
}
