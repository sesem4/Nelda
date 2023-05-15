package dk.sdu.sesem4.smartAIMovement;

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
 * The SmartAIMovementController class implements the MovementControllerSPI interface
 * and provides a simple AI movement algorithm for entities in a game.
 */
public class SmartAIMovementController implements MovementControllerSPI {
	private Vector2 goal;
	private boolean[][] grid;

	private int max_x;
	private int max_y;

	@Override
	public Direction getMovement(GameData gameData, Entity entity) {
		// Get the position of the entity
		PositionPart positionPart = entity.getEntityPart(PositionPart.class);

		// Get the start position of the entity
		Vector2 startPosition = positionPart.getPosition();

		// Get the current region
		MapSPI mapSPI = SPILocator.locateAll(MapSPI.class).get(0);

		this.grid = mapSPI.getNavGrid(gameData);

		int xStart = (int) startPosition.getX() / 16;
		int yStart = (int) startPosition.getY() / 16;

		// Get the goal position
		if (goal == null) {
			goal = mapSPI.getRandomPassableTile(gameData);
		}

		int xGoal = (int) goal.getX() / 16;
		int yGoal = (int) goal.getY() / 16;

		return weightedAStar(xStart, yStart, xGoal, yGoal);
	}

	/**
	 *- Calculates the optimal path between two points using the Weighted A* algorithm.
	 *- @param xStart the x-coordinate of the starting point
	 *- @param yStart the y-coordinate of the starting point
	 *- @param xGoal the x-coordinate of the goal point
	 *- @param yGoal the y-coordinate of the goal point
	 *- @return the optimal direction for the entity to move
	 */
	private Direction weightedAStar(int xStart, int yStart, int xGoal, int yGoal){

		// Initialize the grid
		max_x = grid.length - 1;
		max_y = grid[0].length - 1;

		// Initialize the values
		double[][] g = new double[max_x + 1][max_y + 1];
		double[][] rhs = new double[max_x + 1][max_y + 1];
		double[][] f = new double[max_x + 1][max_y + 1];
		int[][] came_from = new int[max_x + 1][max_y + 1];

		// Initialize all values to infinity
		for(int i = 0; i <= max_x; i++){
			for(int j = 0; j <= max_y; j++){
				g[i][j] = Double.MAX_VALUE;
				rhs[i][j] = Double.MAX_VALUE;
				f[i][j] = Double.MAX_VALUE;
			}
		}

		// Initialize the goal values
		came_from[xStart][yStart] = -1;
		g[xStart][yStart] = 0;
		rhs[xStart][yStart] = heuristic(xStart, yStart, xGoal, yGoal);

		// Initialize the priority queue
		PriorityQueue<State> q = new PriorityQueue<>();
		q.add(new State(xStart, yStart, rhs[xStart][yStart]));

		// While the queue is not empty
		while(!q.isEmpty()){
			State current = q.poll();

			// If the current state is the goal state, break
			if(current.x == xGoal && current.y == yGoal){
				break;
			}

			// Get the neighbours of the current state
			List<State> neighbours = getNeighbours(current.x, current.y);

			// For each neighbour
			for(State neighbour : neighbours){
				double g_new = g[current.x][current.y] + cost(current.x, current.y, neighbour.x, neighbour.y);

				// If the new cost is less than the current cost, update the values
				if(g_new < g[neighbour.x][neighbour.y]){
					came_from[neighbour.x][neighbour.y] = current.x + current.y * (max_x + 1);
					g[neighbour.x][neighbour.y] = g_new;
					f[neighbour.x][neighbour.y] = g_new + heuristic(neighbour.x, neighbour.y, xGoal, yGoal);

					// If the neighbour is in the queue, remove it
					if(q.contains(neighbour)){
						q.remove(neighbour);
					}

					// Add the neighbour to the queue
					neighbour.priority = f[neighbour.x][neighbour.y];
					q.add(neighbour);
				}
			}
		}

		// Get the path from the start to the goal
		List<Integer> path = new ArrayList<>();

		int current_x = xGoal;
		int current_y = yGoal;

		// While the current state is not the start state
		while(current_x != xStart || current_y != yStart){
			path.add(current_x + current_y * (max_x + 1));
			int came_from_index = came_from[current_x][current_y];
			current_x = came_from_index % (max_x + 1);
			current_y = came_from_index / (max_x + 1);
		}

		path.add(xStart + yStart * (max_x + 1));

		// Return the direction of the next tile
		if(path.size() < 2){
			return Direction.NONE;
		}

		int next = path.get(path.size() - 2);

		int xNext = next % (max_x + 1);
		int yNext = next / (max_x + 1);

		if (xStart < xNext) {
			return Direction.RIGHT;
		}
		if (xStart > xNext) {
			return Direction.LEFT;
		}
		if (yStart < yNext) {
			return Direction.DOWN;
		}
		if (yStart > yNext) {
			return Direction.UP;
		}
		return Direction.NONE;

	}


	/**
	 * Returns a list of neighboring states for the given tile coordinate.
	 *
	 * @param x The x-coordinate of the tile
	 * @param y The y-coordinate of the tile
	 * @return A list of neighboring states for the given tile coordinate
	 */
	private List<State> getNeighbours(int x, int y){
		List<State> neighbours = new ArrayList<>();

		int[] offsets = {-1, 0, 1};

		// For each offset
		for(int i : offsets){
			for(int j : offsets){
				// If the offset is 0,0, continue
				if(i == 0 && j == 0){
					continue;
				}

				int new_x = x + i;
				int new_y = y + j;

				// If the new coordinates are out of bounds or the tile is not traversable, continue
				if(new_x < 0 || new_x > max_x || new_y < 0 || new_y > max_y){
					continue;
				}

				// If the tile is not traversable, continue
				if(!grid[new_x][new_y]){
					continue;
				}

				// Add the new state to the list of neighbours
				neighbours.add(new State(new_x, new_y, 0));
			}
		}

		// Return the list of neighbours
		return neighbours;
	}

	/**
	 * Calculates the Manhattan distance between two points as the heuristic estimate of the remaining cost.
	 *
	 * @param x1 The x-coordinate of the first point
	 * @param y1 The y-coordinate of the first point
	 * @param x2 The x-coordinate of the second point
	 * @param y2 The y-coordinate of the second point
	 * @return The Manhattan distance between the two points
	 */
	private double heuristic(int x1, int y1, int x2, int y2){
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	/**
	 * Calculates the cost of moving from one tile to another.
	 *
	 * @param x1 The x-coordinate of the first tile
	 * @param y1 The y-coordinate of the first tile
	 * @param x2 The x-coordinate of the second tile
	 * @param y2 The y-coordinate of the second tile
	 * @return The cost of moving from the first tile to the second tile
	 */
	private double cost(int x1, int y1, int x2, int y2){
		// If the tiles are adjacent, the cost is 1
		if(x1 == x2 || y1 == y2){
			return 1;
		}
		return Math.sqrt(2);
	}


	private class State implements Comparable<State>{
		int x;
		int y;
		double priority;

		/**
		 * Creates a new state with the given x and y coordinates and priority.
		 *
		 * @param x The x-coordinate of the state
		 * @param y The y-coordinate of the state
		 * @param priority The priority of the state
		 */
		public State(int x, int y, double priority){
			this.x = x;
			this.y = y;
			this.priority = priority;
		}

		// States are compared based on their priority
		@Override
		public int compareTo(State o) {
			return (int)(priority - o.priority);
		}

		// Two states are equal if they have the same x and y coordinates
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof State)){
				return false;
			}

			State other = (State)obj;

			return x == other.x && y == other.y;
		}
	}
}
