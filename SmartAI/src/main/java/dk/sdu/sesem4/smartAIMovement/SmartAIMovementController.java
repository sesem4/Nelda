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
	private Vector2 randomGoal;
	
	public static boolean[][] navGrid;
	
	@Override
	public Direction getMovement(GameData gameData, Entity entity) {
		// Get the position of the entity
		PositionPart positionPart = entity.getEntityPart(PositionPart.class);
		
		// Get the start position of the entity
		Vector2 startPosition = positionPart.getPosition();
		
		// Get the current region
		MapSPI mapSPI = SPILocator.locateAll(MapSPI.class).get(0);
		if (mapSPI == null) {
			System.out.println("MapSPI not found.");
			return null;
		}
		navGrid = mapSPI.getNavGrid(gameData);
//		System.out.println("Printing navGrid:");
//		for (int y = navGrid[0].length - 1; y >= 0; y--) {
//			for (int x = 0; x < navGrid.length; x++) {
//				System.out.print(navGrid[x][y] ? "." : "#");
//			}
//			System.out.println();
//		}
		if (randomGoal == null) {
			this.randomGoal = mapSPI.getRandomPassableTile(gameData);
		}
		
		int xStart = (int) startPosition.getX() / 16;
		int yStart = (int) startPosition.getY() / 16;
		
		int xGoal = (int) randomGoal.getX();
		int yGoal = (int) randomGoal.getY();
		
		
		if (xStart == xGoal && yStart == yGoal) {
			this.randomGoal = null;
			return null;
		}
		
		return weightedAStar(xStart, yStart, xGoal, yGoal);
	}
	
	/**
	 * - Calculates the optimal path between two points using the Weighted A* algorithm.
	 * - @param xStart the x-coordinate of the starting point
	 * - @param yStart the y-coordinate of the starting point
	 * - @param xGoal the x-coordinate of the goal point
	 * - @param yGoal the y-coordinate of the goal point
	 * - @return the optimal direction for the entity to move
	 */
	private Direction weightedAStar(int xStart, int yStart, int xGoal, int yGoal) {
		State initialState = new State(xStart, yStart, xGoal, yGoal);
		List<Node> path;
		try {
			path = treeSearch(initialState);
		} catch (NoPathFoundException e) {
			System.out.println(e);
			return null;
		}
		
		if (path.size() <= 1) {
			return null;
		}
		
		Node second = path.get(1);
		Vector2 startPos = new Vector2(xStart, yStart);
		Vector2 stepPos = new Vector2(second.state.x, second.state.y);
		
//		System.out.printf("X: %f Y: %f\n", stepPos.getX(), stepPos.getY());
		
		return startPos.getDirectionTo(stepPos);
	}
	
	private List<Node> treeSearch(State initialState) throws NoPathFoundException {
		Node initial_node = new Node(initialState, 0);
		PriorityQueue<Node> fringe = new PriorityQueue<>();
		fringe.add(initial_node);
		while (fringe.size() != 0) {
			Node node = fringe.poll();
			if (node.state.isGoal()) {
//				System.out.println("Completed with the following path:");
//				for (Node pathNode : node.path) {
//					System.out.printf("x: %d y: %d\n", pathNode.state.x, pathNode.state.y);
//				}
				return node.path;
			}
			List<Node> children = node.expand();
			fringe.addAll(children);
		}
//		System.out.println("no path found");
		throw new NoPathFoundException("Goal state is unreachable");
	}
}
