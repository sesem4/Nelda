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
import dk.sdu.sesem4.common.event.*;
import dk.sdu.sesem4.common.event.events.MapTransitionEvent;
import dk.sdu.sesem4.common.event.events.MapTransitionEventType;
import dk.sdu.sesem4.common.util.Direction;

import java.nio.file.*;
import java.util.HashMap;

/**
 * The MapProcessingService class is responsible for loading the world from the .tmx files into an array of TiledMaps.
 * It is called from the MapPlugin class when the game is started.
 */
public class MapProcessingService implements ProcessingServiceSPI, PostProcessingServiceSPI, EventListener {
	protected Map map;
	
	HashMap<Path, TiledMap> cachedTiledMaps = new HashMap<>();
	
	public MapProcessingService() {
		this.map = new Map();
		EventManager.getInstance().subscribe(MapTransitionEventType.class, this);
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
	private Path getPathForMap(String worldName, int x, int y) {
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
		return getPathForMap(this.map.getCurrentWorldName(), this.map.getCurrentMapIndex() % 16, this.map.getCurrentMapIndex() / 16);
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


	/**
	 * Processes the game data.
	 * @param gameData The game data.
	 * @param priority The priority.
	 */
	@Override
	public void process(GameData gameData, Priority priority) {
		gameData.getGameWorld().setMap(this.getCurrentMap());
	}
	
	/**
	 * Processes the MapTransition event.
	 * @param eventType Class for the event that is to be processed
	 * @param data Data for the event to be processed
	 */
	@Override
	public void processNotification(Class<? extends EventType> eventType, Event data) {
		if (!(data instanceof MapTransitionEvent)) {
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
		checkMapCollisions(gameData);
	}

	/**
	 * Determines if a given entity can move on the map.
	 * @param gameData The game data.
	 */
	protected void checkMapCollisions(GameData gameData) {
		TiledMap currentTiledMap = getCurrentTiledMap();
		for (Entity entity : gameData.getGameEntities().getEntities()) {
			PositionPart positionPart = entity.getEntityPart(PositionPart.class);
			Rectangle entityRectangle = positionPart.getBoundingBox();

			if (!isRectangleValid(entityRectangle, currentTiledMap)) {
				MovingPart movingPart = entity.getEntityPart(MovingPart.class);
				movingPart.undoMovement(entity);
			}
		}
	}
	
	/**
	 * Determines if an entity can pass through a given position.
	 * @param entityRectangle The entity's bounding box.
	 * @return Whether the entity can pass through the position.
	 */
	protected boolean isRectangleValid(Rectangle entityRectangle, TiledMap map) {
		float epsilon = 0.0001f;
		boolean bottomLeftPassible = isPositionPassible(entityRectangle.getBottomLeftCorner().plus(new Vector2(epsilon, epsilon)), map);
		boolean bottomRightPassible = isPositionPassible(entityRectangle.getBottomRightCorner().plus(new Vector2(-epsilon, epsilon)), map);
		boolean topLeftPassible = isPositionPassible(entityRectangle.getTopLeftCorner().plus(new Vector2(epsilon, -epsilon)), map);
		boolean topRightPassible = isPositionPassible(entityRectangle.getTopRightCorner().plus(new Vector2(-epsilon, -epsilon)), map);
		return bottomLeftPassible && bottomRightPassible && topLeftPassible && topRightPassible;
	}

	/**
	 * Determines if a particular position on the map is passable.
	 * @param position The position to check.
	 * @return Whether the position is passable.
	 */
	protected boolean isPositionPassible(Vector2 position, TiledMap map) {
		// get the current tile
		// divide by the tile width and height
		int tileX = (int)position.getX() / 16;
		int tileY = (int)position.getY() / 16;
		
		//get the tile cell properties on the layer
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
		// the cell is null if the player is out-of-bounds. This happens in-between maps, so just return true.
		if (cell == null) {
			return true;
		}
		MapProperties cellProperties = cell.getTile().getProperties();
		
		//check if the tile is solid,
		boolean isPassible = cellProperties.get("solid", boolean.class);
		
		return isPassible;
	}
}
