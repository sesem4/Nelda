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
 * The MapProcessingService class is responsible for loading the world from the .tmx files into an array of TiledMaps.
 * It is called from the MapPlugin class when the game is started.
 */
public class MapProcessingService implements ProcessingServiceSPI, PostProcessingServiceSPI, EventListener {

	protected Map map;

	public MapProcessingService() {
		this.map = new Map();
		EventManager.getInstance().subscribe(MapTransitionEventType.class, this);
	}

	//Tiled map loader
	private TmxMapLoader tmxMapLoader = new TmxMapLoader();

	protected String getResourcesDirectory() {
		return "Map/src/main/resources/";
	}

	/**
	 * Loads the world from the .tmx files into an array of Paths.
	 * @param worldName The name of the world to load.
	 * @param worldWidth The width of the world.
	 * @param worldHeight The height of the world.
	 */
	public void loadWorld(String worldName, int worldWidth, int worldHeight) {
		Path[] world = new Path[worldWidth * worldHeight];

		for (int x = 0; x < worldWidth; x++) {
			for (int y = 0; y < worldHeight; y++) {
				String fileName = getFileNameForMap(worldName, x, y);
				try {
					Path loadedMap = Paths.get(getResourcesDirectory() + fileName);
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
	 * Gets the current tiled map.
	 * @return The current tiled map.
	 */
	public Path getCurrentMap() {
		String relativeFileName = getFileNameForMap(this.map.getCurrentWorldName(), this.map.getCurrentMapIndex() % 16, this.map.getCurrentMapIndex() / 16);
		return Paths.get(relativeFileName);
	}

	/**
	 * Loads the current tiled map.
	 * @param currentTiledMap The current tiled map to load.
	 * @return The loaded tiled map.
	 */
	public Path getCurrentTiledMap() {
		return this.map.getWorld()[this.map.getCurrentMapIndex()];
	}

	public TiledMap loadTiledMap(Path currentTiledMap) {
		return tmxMapLoader.load(getCurrentTiledMap().toString());
	}

	/**
	 * Gets the tile map ID.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @return The tile map ID.
	 */
	public int getTileMapID(int x, int y){
		TiledMap currentMap = loadTiledMap(getCurrentTiledMap());
		TiledMapTileLayer layer = (TiledMapTileLayer) currentMap.getLayers().get(0);
		TiledMapTileLayer.Cell cell = layer.getCell(x, y);
		return cell.getTile().getId();
	}

	/**
	 * Processes the game data.
	 * @param gameData The game data.
	 * @param priority The priority.
	 */
	@Override
	public void process(GameData gameData, Priority priority) {

	}

	/**
	 * Post-processes the game data.
	 * @param gameData The game data.
	 * @param priority The priority.
	 */
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
	 * Post-processes the game data.
	 * @param gameData The game data.
	 * @param priority The priority.
	 */
	@Override
	public void postProcess(GameData gameData, Priority priority) {
		walkable(gameData);
	}

	/**
	 * Determines if a given entity can move on the map.
	 * @param gameData The game data.
	 */
	private void walkable(GameData gameData) {
		for (Entity entity: gameData.getGameEntities().getEntities()) {
			PositionPart positionPart = entity.getEntityPart(PositionPart.class);
			Rectangle entityRectangle = positionPart.getBoundingBox();
			TiledMap currentMap = loadTiledMap(getCurrentTiledMap());

			if (!(passible(currentMap, entityRectangle))) {
				MovingPart movingPart = entity.getEntityPart(MovingPart.class);
				movingPart.undoMovement(entity);
			}

			//get the current position of the entity
			int x = (int) positionPart.getPosition().getX();
			int y = (int) positionPart.getPosition().getY();
			//check if the entity is on a solid tile
			if (checkIfOnSolidTile(x,y)){
				MovingPart movingPart = entity.getEntityPart(MovingPart.class);
				movingPart.undoMovement(entity);
			}

		}
	}

	/**
	 * Checks whether an entity is on a solid tile.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @return Whether the entity is on a solid tile.
	 */
	private boolean checkIfOnSolidTile(int x, int y) {
		TiledMap currentMap = loadTiledMap(getCurrentTiledMap());

		//get the current tile
		// divide by the tile width and height
		int tileX = x / 16;
		int tileY = y / 16;

		//get the tile's id
		int tileID = getTileMapID(tileX, tileY);

		//get the tile cell properties on the layer
		TiledMapTileLayer layer = (TiledMapTileLayer) currentMap.getLayers().get(0);
		TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
		MapProperties cellProperties = cell.getTile().getProperties();

		//check if the tile is solid,
		return !cellProperties.get("solid", boolean.class);
	}

	/**
	 * Determines if a particular position on the map is passable.
	 * @param map The map.
	 * @param position The position to check.
	 * @return Whether the position is passable.
	 */
	private boolean isPositionPassible(TiledMap map, Vector2 position){
		Set<Integer> passibleTiles = Set.of(0);
		TiledMapTileLayer t = ((TiledMapTileLayer) map.getLayers().get(0));
		int tileId = t.getCell((int) position.times(8).getX(), (int) position.times(8).getY()).getTile().getId();
		return passibleTiles.contains(tileId % 42);
	}

	/**
	 * Determines if an entity can pass through a given position.
	 * @param currentMap The current map.
	 * @param entityRectangle The entity's bounding box.
	 * @return Whether the entity can pass through the position.
	 */
	private boolean passible(TiledMap currentMap, Rectangle entityRectangle){
		boolean bottomLeftPassible = isPositionPassible(currentMap, entityRectangle.getBottomLeftCorner());
		boolean bottomRightPassible = isPositionPassible(currentMap, entityRectangle.getBottomRightCorner());
		boolean topLeftPassible = isPositionPassible(currentMap, entityRectangle.getTopLeftCorner());
		boolean topRightPassible = isPositionPassible(currentMap, entityRectangle.getTopRightCorner());

		return bottomLeftPassible && bottomRightPassible && topLeftPassible && topRightPassible;

	}
}
