package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * @author Jakob L.M. & Jon F.J.
 * The Map class represents a map in the game. It contains an array of TiledMaps,
 * the starting map index, the current world name, and the current map index.
 */
public class Map {
	//Array of TiledMaps
	private TiledMap[] world;
	private final int STARTING_MAP_INDEX = 119;
	private String currentWorldName = "overworld";
	private int currentMapIndex = STARTING_MAP_INDEX;

	public TiledMap[] getWorld() {
		return this.world;
	}

	public void setWorld(TiledMap[] world) {
		this.world = world;
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
}
