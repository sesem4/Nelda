package dk.sdu.sesem4.SimpleAIMovement;

import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;
import dk.sdu.sesem4.common.util.SPILocator;

/**
 * The SimpleAIMovementController class implements the MovementControllerSPI interface
 * and provides a simple AI movement algorithm for entities in a game.
 */
public class SimpleAIMovementController implements MovementControllerSPI {
	/**
	 * The randomGoal field stores the destination coordinate for the entity.
	 */
	private Vector2 randomGoal;

	/**
	 * The grid field stores the navigation grid for the game.
	 */
	private boolean[][] grid;

    @Override
    public Direction getMovement(GameData gameData, Entity entity) {

        PositionPart positionPart = entity.getEntityPart(PositionPart.class);

        Vector2 startPosition = positionPart.getPosition();

        MapSPI mapSPI = SPILocator.locateAll(MapSPI.class).get(0);

        if(mapSPI != null){
            this.grid = mapSPI.getNavGrid(gameData);
			if (randomGoal == null){
				this.randomGoal = mapSPI.getRandomPassableTile(gameData);
			}
		}

        int xStart = (int)startPosition.getX() / 16;
        int yStart = (int)startPosition.getY() / 16;
		int xEnd = (int)this.randomGoal.getX();
		int yEnd = (int)this.randomGoal.getY();
        // Check if the goal position is reached
	    if (xStart == xEnd && yStart == yEnd) {
		    // Create a new goal position
		    this.randomGoal = mapSPI.getRandomPassableTile(gameData);
	    }
        return simpleAI(xStart, yStart,xEnd,yEnd, grid);
        // Find the goal position
    }


	/**
	 * The simpleAI method returns the direction that the entity should move in based on the current position and the goal position.
	 * @param xCurrent The current x-coordinate.
	 * @param yCurrent The current y-coordinate.
	 * @param xGoal The goal x-coordinate.
	 * @param yGoal The goal y-coordinate.
	 * @param grid The navigation grid.
	 * @return The direction to move in.
	 */
    private Direction simpleAI(int xCurrent, int yCurrent, int xGoal, int yGoal, boolean[][] grid) {

		if (xCurrent > xGoal) {
            if (grid[xCurrent -1][yCurrent]) {
	            return Direction.LEFT;
            }
        }
        if (xCurrent < xGoal) {
            if (grid[xCurrent + 1][yCurrent]) {
	            return Direction.RIGHT;
            }
        }
        if (yCurrent > yGoal) {
            // Check if the tile below is walkable
            if (grid[xCurrent][yCurrent - 1]){
	            return Direction.DOWN;
            }
        }
        if ( yCurrent < yGoal) {
            if (grid[xCurrent][yCurrent + 1] ) {
	            return Direction.UP;
            }
        }

        return null;
    }


}
