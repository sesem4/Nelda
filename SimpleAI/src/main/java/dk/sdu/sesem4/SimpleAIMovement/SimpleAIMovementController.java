package dk.sdu.sesem4.SimpleAIMovement;

import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;
import dk.sdu.sesem4.common.util.SPILocator;

import java.util.*;

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
	//	private int lastGoalRow;
//	private int lastGoalCol;
	private Vector2 startPosition;

	// create a path hashmap to store the position and the neighbours of the position
	private HashMap<Vector2, List<Vector2>> path = new HashMap<>();

	private MapSPI mapSPI;
	private int lastCol;
	private int lastRow;


	/**
	 * The grid field stores the navigation grid for the game.
	 */
	private boolean[][] grid;


	public SimpleAIMovementController() {

	}

	@Override
	public Direction getMovement(GameData gameData, Entity entity) {
		PositionPart positionPart = entity.getEntityPart(PositionPart.class);
		this.startPosition = positionPart.getPosition();
		this.currentRow = ((int) startPosition.getX()) / 16;
		this.currentCol = ((int) startPosition.getY()) / 16;
		mapSPI = SPILocator.locateAll(MapSPI.class).get(0);
		this.grid = mapSPI.getNavGrid(gameData);

//		if (this.goalPosition == null) {
//			setGoalPosition(mapSPI.getRandomPassableTile(gameData));
//		}
//		if (isStuck(currentRow, currentCol)) {
//			System.out.println("Stuck");
//			setGoalPosition(mapSPI.getRandomPassableTile(gameData));
//		}
		// Update the last position
		this.lastRow = this.currentRow;
		this.lastCol = this.currentCol;

		// within 5 seconds if the entity is not at the goal position, it will find a new goal position




		return depth_first_search(currentRow, currentCol, goalRow, goalCol, gameData);
		// Find the goal position
	}

	public void setGoalPosition(Vector2 goalPosition) {
		this.goalPosition = goalPosition;
		this.goalRow = ((int) goalPosition.getX());
		this.goalCol = ((int) goalPosition.getY());
	}

	public boolean isTilePassible(int col, int row) {

		return mapSPI.isTilePassable(row, col);
	}
	private boolean isStuck(int currentRow, int currentCol) {
		return currentRow == lastRow && currentCol == lastCol;
	}
	private boolean isAtGoalPosition(int currentRow, int currentCol) {
		return currentRow == goalRow && currentCol == goalCol;
	}


	/**
	 * The simpleAI method returns the direction that the entity should move in based on the current position and the goal position.
	 *
	 * @param currentRow The current row.
	 * @param currentCol The current col.
	 * @param goalRow    The goal row.
	 * @param goalCol    The goal col.
	 * @return The direction to move in.
	 */
	private Direction depth_first_search(int currentRow, int currentCol, int goalRow, int goalCol, GameData gameData) {
		int rowDiff = goalRow - currentRow;
		int colDiff = goalCol - currentCol;
		// Get the possible paths from the current position
		HashMap<Vector2, List<Vector2>> path = createPossiblePaths(currentCol, currentRow);
		List<Vector2> possiblePaths = getPossiblePath(path.values());
		this.startPosition = new Vector2(currentRow, currentCol);
//		if (possiblePaths.size() == 4){
//			if (rowDiff < 0) {
//				return Direction.LEFT;
//			}
//			if (rowDiff > 0) {
//				return Direction.RIGHT;
//			}
//			if (colDiff < 0) {
//				return Direction.DOWN;
//			}
//			if (colDiff > 0) {
//				return Direction.UP;
//			}
//		}
//		else{
		// get the previous position
//		Vector2 previousPosition = new Vector2(currentRow, currentCol);

		// if the previous position is the same as the current position, then set a new goal position
//		if (previousPosition.equals(startPosition)) {
//			setGoalPosition(mapSPI.getRandomPassableTile(gameData));
//		}

			if (!possiblePaths.isEmpty()) {
//
				for (Vector2 possiblePath : possiblePaths) {
					if (possiblePath.getX() == currentRow + 1 && rowDiff > 0) {
//						this.startPosition = new Vector2(currentRow + 1, currentCol);
//
						return Direction.LEFT;
					} if (possiblePath.getX() == currentRow - 1 && rowDiff < 0) {
//						this.startPosition = new Vector2(currentRow - 1, currentCol);
//
						return Direction.RIGHT;



					} if ( possiblePath.getY() == currentCol + 1 && colDiff < 0) {
//
//						this.startPosition = new Vector2(currentRow, currentCol + 1);
//
						return Direction.UP;
					} if (possiblePath.getY() == currentCol - 1 && colDiff > 0) {
						return Direction.DOWN;
					}
				}
			}
		if (isAtGoalPosition(currentRow, currentCol)) {
			setGoalPosition(mapSPI.getRandomPassableTile(gameData));
			System.out.println("goal reached");

		}
//		else{
//			setGoalPosition(mapSPI.getRandomPassableTile(gameData));
//		}
		return Direction.NONE;


	}
	private HashMap<Vector2,List<Vector2>> createPossiblePaths(int currentCol, int currentRow){
		HashMap<Vector2, List<Vector2>> path = new HashMap<>();
		Vector2 currentPosition = new Vector2(currentRow, currentCol);
		List<Vector2> neighbours = new ArrayList<>();
		neighbours.add(new Vector2(currentRow - 1, currentCol));
		neighbours.add(new Vector2(currentRow + 1, currentCol));
		neighbours.add(new Vector2(currentRow, currentCol - 1));
		neighbours.add(new Vector2(currentRow, currentCol + 1));
		path.put(currentPosition, neighbours);
		return path;


	}
	private List<Vector2> getPossiblePath(Collection<List<Vector2>> nextPaths){
		List<Vector2> possiblePaths = new ArrayList<>();
		for (List<Vector2> vector2s : nextPaths) {
			for (Vector2 vector2 : vector2s) {
				if (isTilePassible((int) vector2.getY(), (int) vector2.getX())){

					possiblePaths.add(vector2);
				}
			}
		}
//		System.out.println(possiblePaths.size());
		return possiblePaths;
	}
	private Direction simpleAI(int currentRow, int currentCol, int goalRow, int goalCol, GameData gameData) {
		Direction direction = Direction.NONE;
//		if (rowDiff == 0 && colDiff == 0) {
//			System.out.println("on goal");
//			setGoalPosition(mapSPI.getRandomPassableTile(gameData));
////			return Direction.NONE;
//		}
		HashMap<Vector2,List<Vector2>> path = createPossiblePaths(currentRow, currentCol);
		List<Vector2> possiblePaths = getPossiblePath(path.values());
		if (!possiblePaths.isEmpty()) {
			if (possiblePaths.contains(new Vector2(currentRow + 1, currentCol))) {
				System.out.println(currentRow + " " + currentCol);
				currentRow = currentRow + 1;
			} else if (possiblePaths.contains(new Vector2(currentRow - 1, currentCol))) {
//				System.out.println(currentRow + " " + currentCol);
				currentRow = currentRow - 1;
//			} else if (possiblePaths.contains(new Vector2(currentRow, currentCol + 1))) {
				System.out.println(currentRow + " " + currentCol);
				currentCol = currentCol + 1;
//			} else if (possiblePaths.contains(new Vector2(currentRow, currentCol - 1))) {
				System.out.println(currentRow + " " + currentCol);
				currentCol = currentCol - 1;
			}
//			} else if (possiblePaths.contains(new Vector2(currentRow, currentCol + 1))) {
//				direction = Direction.UP;
//			} else if (possiblePaths.contains(new Vector2(currentRow, currentCol - 1))) {
//				direction = Direction.DOWN;
//			}
		}
		int rowDiff = goalRow - currentRow;
		int colDiff = goalCol - currentCol;
		// No possible paths, handle other cases
		if (rowDiff < 0) {
			return Direction.LEFT;
		}
		if (rowDiff > 0) {
			return Direction.RIGHT;
		}
		if (colDiff < 0) {
			return Direction.DOWN;
		}
		if (colDiff > 0) {
			return Direction.UP;
		}
		setGoalPosition(mapSPI.getRandomPassableTile(gameData));
		return Direction.NONE;
	}
//	private boolean checkIfPathIsPassible(HashMap<Vector2,List<Vector2>> path){
//		for (Map.Entry<Vector2, List<Vector2>> entry : path.entrySet()) {
//			Vector2 key = entry.getKey();
//			List<Vector2> value = entry.getValue();
//			for (Vector2 vector2 : value) {
//				if (isTilePassible((int) vector2.getX(), (int) vector2.getY())){
//					return true;
//				}
//			}
//		}
//		return false;
//	}
}
