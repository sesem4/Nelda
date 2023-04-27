package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import static org.junit.Assert.*;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jakob L.M. & Jon F.J.
 * This test makes sure that a map is loaded into the array and returns the array of maps.
 * Constructs a new GdxTestRunner.
 * The constructor creates a new HeadlessApplicationConfiguration object and a new HeadlessApplication object,
 * passing in the (this) object and the conf object as parameters.
 * The Gdx.gl variable is then set to a mock GL20 object using the `Mockito.mock` method from the Mockito library.
 */
@RunWith(GdxTestRunner.class)
public class MapProcessingServiceTest extends MapProcessingService {

	// How many maps the world is wide
	int worldWidth = 16;

	// How many maps the world is high
	int worldHeight = 8;

	/**
	 * This constructor creates a new MapProcessingServiceTest.
	 * It calls the constructor of its superclass, ApplicationTest, passing in a new Map object as a parameter.
	 */
	public MapProcessingServiceTest() {
		super();
	}

	/**
	 * For some reason, when testing, resource file can use the direct path starting from the "resources" folder,
	 * while, when running the game, we must use "Map/src/main/resources" prefix.
	 * This is the best solution we could think of.
	 * @return the path to the "resources" directory. in this case, an empty string.
	 */
	@Override
	protected String getResourcesDirectory() {
		return "";
	}

	/**
	 * Tests all the correct files exist
	 */
	@Test
	public void testCorrectFilesExist() {
		for (int i = 0; i < worldWidth*worldHeight; i++) {
			this.map.setCurrentMapIndex(i);
			new TmxMapLoader().load(this.getCurrentMap().toString());
		}
	}

	/**
	 * Tests whether the correct file contents are loaded into the map.
	 * <p>
	 * This test method tests whether the correct file contents are loaded into the map.
	 * It does this by getting the TiledMap object for the first map in the world and checking the IDs of the tiles at
	 * (0, 0), (7, 4), and (15, 4) to ensure that they match the expected values.
	 * </p>
	 * <p>
	 * If the IDs do not match the expected values, an assertion error is thrown.
	 * </p>
	 */
	@Test
	public void testCorrectFileContents() {
		this.map.setCurrentMapIndex(0);
		TmxMapLoader loader = new TmxMapLoader();
		TiledMap map = loader.load(this.getCurrentMap().toString());

		// get the id of the tile at (0, 0)
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		TiledMapTileLayer.Cell cell = layer.getCell(0, 0);
		TiledMapTile tile = cell.getTile();
		int id = tile.getId();
		int id2 = layer.getCell(7, 4).getTile().getId();
		int id3 = layer.getCell(15, 4).getTile().getId();

		// make sure the ids are correct
		assertEquals(id, 12);
		assertEquals(id2, 34);
		assertEquals(id3, 3);
	}

	/**
	 * Tests that the correct world indices are loaded into the Map object.
	 * We do this by checking each corner of the world
	 */
	@Test
	public void testCorrectWorldIndices() {
		int topLeftCornerIndex = 0;
		int topRightCornerIndex = worldWidth-1;
		int bottomLeftCornerIndex = worldWidth*(worldHeight-1);
		int bottomRightCornerIndex = (worldWidth*worldHeight)-1;
		
		
		TmxMapLoader mapLoader = new TmxMapLoader();
		
		this.map.setCurrentMapIndex(topLeftCornerIndex);
		TiledMap topLeftMap = mapLoader.load(this.getCurrentMap().toString());
		assertEquals(12, getCellId(topLeftMap, 0, 0));
		assertEquals(34, getCellId(topLeftMap, 7, 4));
		assertEquals(3, getCellId(topLeftMap, 15, 4));
		
		this.map.setCurrentMapIndex(topRightCornerIndex);
		TiledMap topRightMap = mapLoader.load(this.getCurrentMap().toString());
		assertEquals(15, getCellId(topRightMap, 0, 0));
		assertEquals(41, getCellId(topRightMap, 8, 7));
		assertEquals(34, getCellId(topRightMap, 8, 0));
		
		this.map.setCurrentMapIndex(bottomLeftCornerIndex);
		TiledMap bottomLeftMap = mapLoader.load(this.getCurrentMap().toString());
		assertEquals(12, getCellId(bottomLeftMap, 0, 0));
		assertEquals(28, getCellId(bottomLeftMap, 8, 7));
		assertEquals(37, getCellId(bottomLeftMap, 6, 3));
		
		this.map.setCurrentMapIndex(bottomRightCornerIndex);
		TiledMap bottomRightMap = mapLoader.load(this.getCurrentMap().toString());
		assertEquals(15, getCellId(bottomRightMap, 0, 0));
		assertEquals(18, getCellId(bottomRightMap, 8, 5));
		assertEquals(11, getCellId(bottomRightMap, 1, 7));
	}
	
	private int getCellId(TiledMap map, int x, int y) {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		return layer.getCell(x, y).getTile().getId();
	}
	

	/**
	 * Tests that the `checkIfOnSolidTile` method works correctly.
	 * The method first saves the map as a `TiledMapTileLayer` object and gets the properties of the
	 * cells at `(0, 0)` and `(7, 0)`. It then asserts that the `solid` property of the first cell is `false`
	 * and the `solid` property of the second cell is `true`.
	 */
	@Test
	public void testTilesCanBeSolid(){
		this.map.setCurrentMapIndex(0);
		TiledMap map = new TmxMapLoader().load(this.getCurrentMap().toString());
		//save the map as a TiledMapTileLayer
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

		//get the properties of the map and the cells
		TiledMapTileLayer.Cell cell1 = layer.getCell(0, 0);
		TiledMapTileLayer.Cell cell2 = layer.getCell(7, 0);
		MapProperties cellProperties = cell1.getTile().getProperties();
		MapProperties cellProperties2 = cell2.getTile().getProperties();

		//assert that the cells are solid and not solid
		assertEquals(cellProperties.get("solid"), false);
		assertEquals(cellProperties2.get("solid"), true);
	}


	// TODO: ensure that the map is changed when a mapTransition event is emitted
	// TODO: ensure that the map is changed correctly
}
