package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test makes sure that a map is loaded into the array and returns the array of maps.
 */
@RunWith(GdxTestRunner.class)
public class MapProcessingServiceTest extends MapProcessingService {
	int worldWidth = 16;
	int worldHeight = 8;

	public MapProcessingServiceTest() {
		super(new Map());
	}

	/**
	 * Before each test all the maps will be loaded into the world.
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
	 * ensure all the correct files exist
	 */
	@Test
	public void testCorrectFilesExist() {

	}

	/**
	 * Ensure the files are loaded by MapProcessingService
	 */
	@Test
	public void testLoadWorldLoadsFiles() {
		assertEquals(map.world.length, worldWidth*worldHeight);
		for (TiledMap tiledMap : map.world) {
			assertNotNull(tiledMap);
		}
	}

	/**
	 * ensure the file contents are correct
	 */
	@Test
	public void testCorrectFileContents() {
		TiledMap m = map.world[0];

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
	 * ensure the maps are loaded into the correct indices
	 */
	@Test
	public void testCorrectWorldIndices() {

	}

	// TODO: ensure that the map is changed when a mapTransition event is emitted
	// TODO: ensure that the map is changed correctly
}
