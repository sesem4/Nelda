package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.event.*;
import dk.sdu.sesem4.common.event.events.MapTransitionEvent;
import dk.sdu.sesem4.common.event.events.MapTransitionEventType;
import dk.sdu.sesem4.common.util.Direction;

import java.nio.file.*;

/**
 * @author Jakob L.M. & Jon F.J.
 * MapProcessingService loads the world from the .tmx files into an array of TiledMaps.
 * It is called from the MapPlugin class when the game is started.
 */

public class MapProcessingService implements ProcessingServiceSPI, EventListener {
	protected Map map;

	public MapProcessingService() {
		this.map = new Map();
		EventManager.getInstance().subscribe(MapTransitionEventType.class, this);
	}

	//Tiled map loader
	private TmxMapLoader tmxMapLoader = new TmxMapLoader();

	/**
	 * This method just returns the current "resources" folder.
	 * It is needed, so we can do tests with a custom "resources" folder.
	 * @return the resources directory to get resource files from
	 */
	protected String getResourcesDirectory() {
		return "Map/src/main/resources/";
	}

	/**
	 * This method loads the world from the .tmx files into an array of TiledMaps.
	 * It takes three parameters:
	 *
	 * @param worldName A string that represents the name of the world to load.
	 * @param worldWidth An integer that represents the width of the world to load.
	 * @param worldHeight An integer that represents the height of the world to load.
	 */
	public void loadWorld(String worldName, int worldWidth, int worldHeight) {
		TiledMap[] world = new TiledMap[worldWidth * worldHeight];

		for (int x = 0; x < worldWidth; x++) {
			for (int y = 0; y < worldHeight; y++) {
				String fileName = getFileNameForMap(worldName, x, y);
				try {
					TiledMap loadedMap = tmxMapLoader.load(fileName);
					world[x + y * worldWidth] = loadedMap;
				} catch (Exception e) {
					world[x + y * worldWidth] = null;
					System.out.println("ERROR loading " + fileName);
				}
			}
		}

		this.map.setWorld(world);
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
	 * @return a string that represents the file name of the map.
	 */
	private String getFileNameForMap(String worldName, int x, int y) {
//		URL url = this.getClass().getClassLoader().getResource(worldName + "/" + columns[x] + (y + 1) + ".tmx");
//		String fileName = url.getPath();
		String[] columns = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		return getResourcesDirectory() + worldName + "/" + columns[x] + (y + 1) + ".tmx";
	}

	/**
	 * The method returns the current Path being used.
	 * It first calls the method getFileNameForMap to get the file name of the current map.
	 * The getFileNameForMap method takes three parameters: worldName, x, and y, a string that represents the file name of the map. The method then returns the Path object created from the file name.
	 * The method first calls the getCurrentMapIndex method to get the current map index,
	 * and then calls the getFileNameForMap method to get the file name for the map.
	 * @return String that represents the file name of the map
	 */
	public Path getCurrentMap() {
		String relativeFileName = getFileNameForMap(this.map.getCurrentWorldName(), this.map.getCurrentMapIndex() % 16, this.map.getCurrentMapIndex() / 16);
		return Paths.get(relativeFileName);
	}

	/**
	 * This method sets the current map in the game data.
	 *
	 * @param gameData The game data.
	 * @param priority The priority.
	 */
	@Override
	public void process(GameData gameData, Priority priority) {

	}

	@Override
	public void processNotification(Class<? extends EventType> eventType, Event data) {
		if (!(data instanceof  MapTransitionEvent)) {
			return;
		}
		MapTransitionEvent eventData = (MapTransitionEvent) data;

		Direction direction = eventData.getDirection();
		switch (direction) {
			case UP:
				this.map.setCurrentMapIndex(this.map.getCurrentMapIndex() - 16);
				break;
			case DOWN:
				this.map.setCurrentMapIndex(this.map.getCurrentMapIndex() + 16);
				break;
			case LEFT:
				this.map.setCurrentMapIndex(this.map.getCurrentMapIndex() - 1);
				break;
			case RIGHT:
				this.map.setCurrentMapIndex(this.map.getCurrentMapIndex() + 1);
				break;
		}

		eventData.getGameData().getGameWorld().setMap(getCurrentMap());
	}
}
