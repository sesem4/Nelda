package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dk.sdu.sesem4.common.data.math.Rectangle;
import dk.sdu.sesem4.common.data.math.Vector2;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class MapUtilTest {
	// How many maps the world is wide
	int worldWidth = 16;
	
	// How many maps the world is high
	int worldHeight = 8;
	
	Map map;
	
	/**
	 * This constructor creates a new MapProcessingServiceTest.
	 * It calls the constructor of its superclass, ApplicationTest, passing in a new Map object as a parameter.
	 */
	public MapUtilTest() {
		super();
		this.map = TestMap.getInstance();
	}
	
	@Test
	public void testIsRectangleValid() {
		java.util.Map<Vector2, Boolean> positions = java.util.Map.of(
				new Vector2(0*16, 0*16), false,
				new Vector2(7*16, 0*16), true,
				new Vector2(7*16, 5*16), true,
				new Vector2(7*16, 5.5f*16), false
		);
		
		map.setCurrentMapIndex(0);
		TiledMap tiledMap = this.map.getCurrentTiledMap();
		
		MapUtil mapUtil = new MapUtil();
		
		for (java.util.Map.Entry<Vector2, Boolean> entrySet : positions.entrySet()) {
			Rectangle rectangle = new Rectangle(entrySet.getKey(), new Vector2(16, 16));
			boolean actual = mapUtil.isRectangleValid(rectangle, tiledMap);
			
			boolean expected = entrySet.getValue();
			
			assertEquals(expected, actual);
		}
	}
	
	/**
	 * Tests that the `checkIfOnSolidTile` method works correctly.
	 * The method first saves the map as a `TiledMapTileLayer` object and gets the properties of the
	 * cells at `(0, 0)` and `(7, 0)`. It then asserts that the `solid` property of the first cell is `false`
	 * and the `solid` property of the second cell is `true`.
	 */
	@Test
	public void testTilesCanBeSolid() {
		this.map.setCurrentMapIndex(0);
		TiledMap map = new TmxMapLoader().load(this.map.getCurrentMap().toString());
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
		TiledMap topLeftMap = mapLoader.load(this.map.getCurrentMap().toString());
		assertEquals(12, getCellId(topLeftMap, 0, 0));
		assertEquals(34, getCellId(topLeftMap, 7, 4));
		assertEquals(3, getCellId(topLeftMap, 15, 4));
		
		this.map.setCurrentMapIndex(topRightCornerIndex);
		TiledMap topRightMap = mapLoader.load(this.map.getCurrentMap().toString());
		assertEquals(15, getCellId(topRightMap, 0, 0));
		assertEquals(41, getCellId(topRightMap, 8, 7));
		assertEquals(34, getCellId(topRightMap, 8, 0));
		
		this.map.setCurrentMapIndex(bottomLeftCornerIndex);
		TiledMap bottomLeftMap = mapLoader.load(this.map.getCurrentMap().toString());
		assertEquals(12, getCellId(bottomLeftMap, 0, 0));
		assertEquals(28, getCellId(bottomLeftMap, 8, 7));
		assertEquals(37, getCellId(bottomLeftMap, 6, 3));
		
		this.map.setCurrentMapIndex(bottomRightCornerIndex);
		TiledMap bottomRightMap = mapLoader.load(this.map.getCurrentMap().toString());
		assertEquals(15, getCellId(bottomRightMap, 0, 0));
		assertEquals(18, getCellId(bottomRightMap, 8, 5));
		assertEquals(11, getCellId(bottomRightMap, 1, 7));
	}
	
	private int getCellId(TiledMap map, int x, int y) {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		return layer.getCell(x, y).getTile().getId();
	}
	
	/**
	 * Tests all the correct files exist
	 */
	@Test
	public void testCorrectFilesExist() {
		for (int i = 0; i < worldWidth*worldHeight; i++) {
			this.map.setCurrentMapIndex(i);
			new TmxMapLoader().load(map.getCurrentMap().toString());
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
		TiledMap map = loader.load(this.map.getCurrentMap().toString());
		
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
}
