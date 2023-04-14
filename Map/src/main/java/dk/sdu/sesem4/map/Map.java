package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class Map {
	//Array of TiledMaps
	TiledMap[] world;
	final int STARTING_MAP_INDEX = 119;
	String currentWorldName = "overworld";
	int currentMapIndex = STARTING_MAP_INDEX;
}
