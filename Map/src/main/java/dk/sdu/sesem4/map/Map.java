package dk.sdu.sesem4.map;

/**
 * @author Jakob L.M. & Jon F.J.
 * The Map class represents a map in the game. It contains an array of TiledMaps,
 * the starting map index, the current world name, and the current map index.
 */
public class Map {
	private final int STARTING_MAP_INDEX = 119;
	private String currentWorldName = "overworld";
	private int currentMapIndex = STARTING_MAP_INDEX;

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
