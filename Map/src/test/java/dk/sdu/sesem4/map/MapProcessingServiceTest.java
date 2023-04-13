package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test makes sure that a map is loaded into the array and returns the array of maps.
 */
@RunWith(GdxTestRunner.class)
public class MapProcessingServiceTest {
	@Test
	public void loadWorld() {
		// load map
		String fileName = "overworld/A1.tmx";
		TmxMapLoader tmxMapLoader = new TmxMapLoader();
		TiledMap map = tmxMapLoader.load(fileName);

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
