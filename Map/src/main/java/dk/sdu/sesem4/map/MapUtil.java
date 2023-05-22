package dk.sdu.sesem4.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.math.Rectangle;
import dk.sdu.sesem4.common.data.math.Vector2;

public class MapUtil implements MapSPI {
	@Override
	public boolean isPositionPassable(Vector2 worldPosition) {
		// get the current tile
		// divide by the tile width and height
		int tileX = (int) worldPosition.getX() / 16;
		int tileY = (int) worldPosition.getY() / 16;

		return isTilePassable(tileX, tileY);
	}

	@Override
	public boolean isTilePassable(Vector2 tileCoordinate) {
		Map map = Map.getInstance();

		//get the tile cell properties on the layer
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getCurrentTiledMap().getLayers().get(0);
		TiledMapTileLayer.Cell cell = layer.getCell((int) tileCoordinate.getX(), (int) tileCoordinate.getY());
		// the cell is null if the player is out-of-bounds. This happens in-between maps, so just return true.
		if (cell == null) {
			return true;
		}
		MapProperties cellProperties = cell.getTile().getProperties();

		//check if the tile is solid,
		return cellProperties.get("solid", boolean.class);
	}

	@Override
	public boolean isTilePassable(int x, int y) {
		return isTilePassable(new Vector2(x, y));
	}

	@Override
	public Vector2 getRandomPassableTile(GameData gameData) {
		// Prepare data
		GameWorld gameWorld = gameData.getGameWorld();
		Vector2 mapSize = gameWorld.getMapSize();

		// Get grid size
		float xGrid = mapSize.getX() / GameWorld.TILE_SIZE;
		float yGrid = mapSize.getY() / GameWorld.TILE_SIZE;

		// Set iteration limit, if random position is not passible
		int maxIterations = (int) (xGrid * yGrid);
		int i = 0;

		// Brute force random passable tile
		while (true) {
			Vector2 position = new Vector2((int) (Math.random() * xGrid), (int) (Math.random() * yGrid));

			if (isTilePassable(position)) {
				return position;
			}

			if (i > maxIterations) {
				return position;
			}

			i++;
		}
	}

	@Override
	public boolean[][] getNavGrid(GameData gameData) {
		// Prepare data
		GameWorld gameWorld = gameData.getGameWorld();
		Vector2 mapSize = gameWorld.getMapSize();

		// Get grid size
		int xGrid = (int) mapSize.getX() / GameWorld.TILE_SIZE;
		int yGrid = (int) mapSize.getY() / GameWorld.TILE_SIZE;

		// Generate boolean 2D array
		boolean[][] navGrid = new boolean[xGrid][yGrid];
		for (int x = 0; x < xGrid; x++) {
			for (int y = 0; y < yGrid; y++) {
				navGrid[x][y] = isTilePassable(x, y);
			}
		}

		return navGrid;
	}

	/**
	 * Determines if an entity can pass through a given position.
	 * @param entityRectangle The entity's bounding box.
	 * @return Whether the entity can pass through the position.
	 */
	protected boolean isRectangleValid(Rectangle entityRectangle) {
		float epsilon = 2.0f;
		float yAxisEpsilon = 3.0f;
		boolean bottomLeftPassible = isPositionPassable(entityRectangle.getBottomLeftCorner().plus(new Vector2(epsilon, epsilon)));
		boolean bottomRightPassible = isPositionPassable(entityRectangle.getBottomRightCorner().plus(new Vector2(-epsilon, epsilon)));
		boolean topLeftPassible = isPositionPassable(entityRectangle.getTopLeftCorner().plus(new Vector2(epsilon * yAxisEpsilon, -epsilon * yAxisEpsilon)));
		boolean topRightPassible = isPositionPassable(entityRectangle.getTopRightCorner().plus(new Vector2(-epsilon * yAxisEpsilon, -epsilon * yAxisEpsilon)));
		return bottomLeftPassible && bottomRightPassible && topLeftPassible && topRightPassible;
	}
}
