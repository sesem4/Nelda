package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * @author Jakob L.M. & Jon F.J.
 * The Map class represents a map in the game. It contains an array of TiledMaps,
 * the starting map index, the current world name, and the current map index.
 */
public class Map {
	//Array of TiledMaps
	TiledMap[] world;
	final int STARTING_MAP_INDEX = 119;
	String currentWorldName = "overworld";
	int currentMapIndex = STARTING_MAP_INDEX;
}
