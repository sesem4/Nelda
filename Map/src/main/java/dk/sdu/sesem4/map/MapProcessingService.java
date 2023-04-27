package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Rectangle;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.event.Event;
import dk.sdu.sesem4.common.event.EventListener;
import dk.sdu.sesem4.common.event.EventManager;
import dk.sdu.sesem4.common.event.EventType;
import dk.sdu.sesem4.common.event.events.MapTransitionEvent;
import dk.sdu.sesem4.common.event.events.MapTransitionEventType;
import dk.sdu.sesem4.common.util.Direction;

import java.nio.file.*;
import java.util.Set;

/**
 * @author Jakob L.M. & Jon F.J.
 * MapProcessingService loads the world from the .tmx files into an array of TiledMaps.
 * It is called from the MapPlugin class when the game is started.
 */

<<<<<<<<< Temporary merge branch 1
public class MapProcessingService implements ProcessingServiceSPI, PostProcessingServiceSPI {
=========
public class MapProcessingService implements ProcessingServiceSPI, EventListener {
>>>>>>>>> Temporary merge branch 2
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

	public TiledMap getCurrentTiledMap() {
		return this.map.getWorld()[this.map.getCurrentMapIndex()];
	}

	public int getTileMapID(int x, int y){
		TiledMapTileLayer layer = (TiledMapTileLayer) getCurrentTiledMap().getLayers().get(0);
		TiledMapTileLayer.Cell cell = layer.getCell(x, y);
		return cell.getTile().getId();
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

	/**
	 * Do collision check for all entities. This is done by checking that all corners of the entity
	 * are on a passible tile. If at least one corner isn't, we undo the Entity's movement.
	 * @param gameData The game data
	 * @param priority The priority, which is to be run for the current process round
	 */
	@Override
	public void postProcess(GameData gameData, Priority priority) {
		walkable(gameData);

		for (Entity entity : gameData.getGameEntities().getEntities()) {
			PositionPart positionPart = entity.getEntityPart(PositionPart.class);
			Rectangle entityRectangle = positionPart.getBoundingBox();

			boolean bottomLeftPassible = isPositionPassible(currMap, entityRectangle.getBottomLeftCorner());
			boolean bottomRightPassible = isPositionPassible(currMap, entityRectangle.getBottomRightCorner());
			boolean topLeftPassible = isPositionPassible(currMap, entityRectangle.getTopLeftCorner());
			boolean topRightPassible = isPositionPassible(currMap, entityRectangle.getTopRightCorner());

			if (!(bottomLeftPassible && bottomRightPassible && topLeftPassible && topRightPassible)) {
				MovingPart movingPart = entity.getEntityPart(MovingPart.class);
				movingPart.undoMovement(entity);
			}
		}
	}
	/**
	 * This method checks whether an entity is on a solid tile. If it is not on a solid tile, it undoes the entity's movement.
	 * @param gameData The game data.
	 */
	private void walkable(GameData gameData) {
		for (Entity entity: gameData.getGameEntities().getEntities()) {
			PositionPart positionPart = entity.getEntityPart(PositionPart.class);
			if (positionPart != null) {
				//get the current position of the entity
				int x = (int) positionPart.getPosition().getX();
				int y = (int) positionPart.getPosition().getY();
				if (!checkIfOnSolidTile(x,y)){
					MovingPart movingPart = entity.getEntityPart(MovingPart.class);
					movingPart.undoMovement(entity);
				}
			}
		}
	}

	/**
	 * This method checks whether a tile is solid.
	 * @param x The x-coordinate of the tile.
	 * @param y The y-coordinate of the tile.
	 * @return Whether the tile is solid.
	 */
	private boolean checkIfOnSolidTile(int x, int y) {
		TiledMap currentMap = getCurrentTiledMap();

		//get the current tile
		// divide by the tile width and height
		int tileX = x / 16;
		int tileY = y / 16;

		//get the tile's id
		int tileID = getTileMapID(tileX, tileY);

	private boolean isPositionPassible(TiledMap map, Vector2 position) {
		Set<Integer> passibleTiles = Set.of(0);
		TiledMapTileLayer t = ((TiledMapTileLayer)map.getLayers().get(0));
		int tileId = t.getCell((int)position.times(8).getX(), (int)position.times(8).getY()).getTile().getId();
		return passibleTiles.contains(tileId % 42);
	}
}
