package dk.sdu.sesem4.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.nio.file.Path;

/**
 * A class that is responsible for loading maps
 */
public class Utils {
	/**
	 * Loads a map from a path
	 * @param path the path to the map
	 * @return the loaded map
	 */
	static TiledMap loadMap(Path path) {
		TmxMapLoader tmxMapLoader = new TmxMapLoader();
		return tmxMapLoader.load(path.toString());
	}
}
