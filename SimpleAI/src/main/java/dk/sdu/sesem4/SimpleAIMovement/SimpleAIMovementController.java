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
	private Vector2 goalPosition;

	private int goalRow;
	private int goalCol;

	private int currentRow;
	private int currentCol;

	private int lastRow;
	private int lastCol;

	private MapSPI mapSPI;

	/**
	 * The grid field stores the navigation grid for the game.
	 */
	private boolean[][] grid;

    @Override
    public Direction getMovement(GameData gameData, Entity entity) {

        PositionPart positionPart = entity.getEntityPart(PositionPart.class);

	    Vector2 startPosition = positionPart.getPosition();
	    this.currentRow = ((int) startPosition.getY() / 16);
	    this.currentCol = ((int) startPosition.getX() / 16);


//	    if(){
//		    setGoalPosition(mapSPI.getRandomPassableTile(gameData));
//		    simpleAI(this.currentRow, this.currentCol, this.goalRow, this.goalCol, this.grid, gameData);
//	    }

		mapSPI = SPILocator.locateAll(MapSPI.class).get(0);
		this.grid = mapSPI.getNavGrid(gameData);


	    if (this.goalPosition == null||
			    currentRow == this.goalRow && currentCol == this.goalCol
			    ) {
			setGoalPosition(mapSPI.getRandomPassableTile(gameData));
	    }

		return simpleAI(currentRow, currentCol, goalRow, goalCol, grid, gameData);
	    // Find the goal position
    }

	public void setGoalPosition(Vector2 goalPosition) {
		this.goalPosition = goalPosition;
		this.goalRow = ((int)goalPosition.getX());
		this.goalCol = ((int)goalPosition.getY());
	}

	public boolean isTilePassible(int row, int col) {

		return mapSPI.isTilePassable(row, col);
	}


	/**
	 * The simpleAI method returns the direction that the entity should move in based on the current position and the goal position.
	 * @param currentRow The current row.
	 * @param currentCol The current col.
	 * @param goalRow The goal row.
	 * @param goalCol The goal col.
	 * @param grid The navigation grid.
	 * @return The direction to move in.
	 */
	private Direction simpleAI(int currentRow, int currentCol, int goalRow, int goalCol, boolean[][] grid, GameData gameData) {
		Direction direction = Direction.NONE;



			if (currentRow > goalRow && isTilePassible(currentCol,currentRow - 1)) {
				direction = Direction.LEFT;
				return direction;
			}

			if (currentRow < goalRow && isTilePassible(currentCol,currentRow + 1)) {
				direction = Direction.RIGHT;
				return direction;
			}

			if (currentCol > goalCol && isTilePassible(currentCol - 1,currentRow)) {
				direction = Direction.DOWN;
				return direction;
			}

			if (currentCol < goalCol && isTilePassible(currentCol - 1,currentRow)) {
				direction = Direction.UP;
				return direction;
			}


		setGoalPosition(mapSPI.getRandomPassableTile(gameData));
//		simpleAI(this.currentRow, this.currentCol, this.goalRow, this.goalCol, this.grid, gameData);
		//this.lastRow = currentRow;
		//this.lastCol = currentCol;
		return direction;

	}


}
