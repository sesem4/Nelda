package dk.sdu.sesem4.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.nio.file.Path;

public class Utils {
	static TiledMap loadMap(Path path) {
		TmxMapLoader tmxMapLoader = new TmxMapLoader();
		return tmxMapLoader.load(path.toString());
	}
}
