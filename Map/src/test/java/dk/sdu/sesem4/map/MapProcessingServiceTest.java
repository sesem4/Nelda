package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.junit.jupiter.api.Test;

/**
 * This test makes sure that a map is loaded into the array and returns the array of maps.
 */
class MapProcessingServiceTest {
    @Test
    void loadWorld() {
        // load map
        String fileName = "A1.tmx";
        TmxMapLoader tmxMapLoader = new TmxMapLoader();
        TiledMap map = tmxMapLoader.load(fileName);

        // get the id of the tile at (0, 0)
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        TiledMapTileLayer.Cell cell = layer.getCell(0, 0);
        TiledMapTile tile = cell.getTile();
        int id = tile.getId();

        // make sure the id is correct
        assert id == 12;
    }
}