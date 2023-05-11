package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;

public interface MapSPI {
	/**
	 * Check if a world position is a passable location.
	 * <br />
	 * <br />
	 * Pre-condition: A map is loaded into the world.<br />
	 * <br />
	 * post-condition: The passability of the world position is returned.
	 * 
	 * @param worldPosition The world position formatted as a `Vector2` that
	 *                      includes the x and y coordinate
	 * 
	 * @return `true` if the position is passable, `false` if not.
	 */
	boolean isPositionPassable(Vector2 worldPosition);

	/**
	 * Check if a tile coordinate is a passable location.
	 * <br />
	 * <br />
	 * Pre-condition: A map is loaded into the world.<br />
	 * <br />
	 * post-condition: The passability of the world position is returned.
	 * 
	 * @param tileCoordinate The tile position is formatted as a `Vector2` that
	 *                       includes the x and y coordinates. This position is
	 *                       related to a whole number tile location
	 * 
	 * @return `true` if the position is passable, `false` if not.
	 */
	boolean isTilePassable(Vector2 tileCoordinate);

	/**
	 * Check if a tile coordinate is a passable location.
	 * <br />
	 * <br />
	 * Pre-condition: A map is loaded into the world.<br />
	 * <br />
	 * post-condition: The passability of the world position is returned.
	 * 
	 * @param x The tile x position
	 * @param y The tile y position<
	 * 
	 * @return `true` if the position is passable, `false` if not.
	 */
	boolean isTilePassable(int x, int y);

	/**
	 * Get a random passable tile coordinate.
	 * <br />
	 * <br />
	 * Pre-condition: A map is loaded into the world.<br />
	 * <br />
	 * post-condition: A Vector2 coordinate representing a passable tile coordinate
	 * is returned.
	 *
	 * @param gameData The game data
	 * 
	 * @return A Vector2 coordinate representing a passable tile coordinate.
	 */
	Vector2 getRandomPassableTile(GameData gameData);

	/**
	 * Get a grid of booleans representing the passable states of all tiles
	 * currently in the world.
	 * <br />
	 * <br />
	 * Pre-condition: A map is loaded into the world.<br />
	 * <br />
	 * Post-condition: A 2D boolean array is returned, which represents the passable
	 * states of all tiles currently in the world. The origin is bottom left, with
	 * the x being represented by the first index and the y being represented by the
	 * second index.
	 *
	 * @param gameData The game data
	 * 
	 * @return A grid of booleans representing the passable states of all tiles
	 *         currently in the world.
	 * 
	 * @example <code>
	 *          getNavGrid()[x][y];
	 * </code>
	 * 
	 */
	boolean[][] getNavGrid(GameData gameData);
}
