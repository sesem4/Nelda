package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import static org.junit.Assert.*;

import org.junit.Before;
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
	 * This method loads the world before each test by calling the loadWorld method from the MapProcessingService class,
	 * passing in the parameters "overworld", worldWidth, and worldHeight as arguments.
	 */
	@Before
	public void loadWorld() {
		// world is inherited from MapProcessingService
		super.loadWorld("overworld", worldWidth, worldHeight);
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
		// TODO: make this.
	}

	/**
	 * Tests that the correct files are loaded into the TiledMap objects.
	 * It first checks that the length of the world array is equal to worldWidth times worldHeight.
	 * It then loops through each TiledMap in the world array, ensuring that it is not null.
	 */
	@Test
	public void testLoadWorldLoadsFiles() {
		assertEquals(map.getWorld().length, worldWidth*worldHeight);
		for (TiledMap tiledMap : map.getWorld()) {
			assertNotNull(tiledMap);
		}
	}

	/**
	 * Tests whether the correct file contents are loaded into the map.
	 * <p>
	 * This test method tests whether the correct file contents are loaded into the map. It does this by calling the
	 * {@link #loadWorld(String, int, int)} method of the {@link MapProcessingService} class to load the "test" world into
	 * memory. It then gets the TiledMap object for the first map in the world and checks the IDs of the tiles at (0, 0), (7,
	 * 4), and (15, 4) to ensure that they match the expected values.
	 * </p>
	 * <p>
	 * If the IDs do not match the expected values, an assertion error is thrown.
	 * </p>
	 */
	@Test
	public void testCorrectFileContents() {
		TiledMap m = map.getWorld()[0];

		// get the id of the tile at (0, 0)
		TiledMapTileLayer layer = (TiledMapTileLayer) m.getLayers().get(0);
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
	 * We do this by checking each corner of the map
	 */
	@Test
	public void testCorrectWorldIndices() {
		// TODO: Make this.
	}


	// TODO: ensure that the map is changed when a mapTransition event is emitted
	// TODO: ensure that the map is changed correctly
}
