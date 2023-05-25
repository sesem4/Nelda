package dk.sdu.sesem4.ai.smart;

import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
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
	
	public static boolean[][] navGrid;
	
	private final TileMover tileMover = new TileMover();
	
	private MapSPI getMapSPI() throws SpiNotFoundException {
		MapSPI mapSPI = SPILocator.locateAll(MapSPI.class).get(0);
		if (mapSPI == null) {
			throw new SpiNotFoundException("MapSPI not found.");
		}
		return mapSPI;
	}
	
	private void updateNavGrid(GameData gameData) throws SpiNotFoundException {
		navGrid = getMapSPI().getNavGrid(gameData);
	}
	
	private Vector2 getGoal(GameData gameData) throws PlayerException, SpiNotFoundException {
		List<Entity> entities = gameData.getGameEntities().getEntities();
		Entity player = null;
		for (Entity entity : entities) {
			if (entity.getEntityType() == EntityType.Player) {
				player = entity;
				break;
			}
		}
		if (player == null) {
			throw new PlayerException("Player not found.");
		}
		PositionPart positionPart = player.getEntityPart(PositionPart.class);
		if (positionPart == null) {
			throw new PlayerException("No PositionPart on player.");
		}
		Vector2 playerPosition = positionPart.getPosition();
		
		Vector2 playerTile = playerPosition.divide(GameWorld.TILE_SIZE);
		
		if (playerTile.getX() < 0 || playerTile.getY() < 0) {
			throw new PlayerException("Player is outside map.");
		}
		Vector2 mapSize = gameData.getGameWorld().getMapSize().divide(GameWorld.TILE_SIZE);
		if (playerTile.getX() > mapSize.getX() || playerTile.getY() > mapSize.getY()) {
			throw new PlayerException("Player is outside map.");
		}
		
		MapSPI mapSPI = getMapSPI();
		if (!mapSPI.isTilePassable(playerTile)) {
			throw new PlayerException("Player is not on passable tile.");
		}
		
		return playerTile;
	}
	
	@Override
	public Direction getMovement(GameData gameData, Entity entity) {
		// Get the position of the entity
		PositionPart positionPart = entity.getEntityPart(PositionPart.class);
		
		// Get the start position of the entity
		Vector2 startPosition = positionPart.getPosition();
		tileMover.position = startPosition;
		if (tileMover.target == null) {
			tileMover.target = startPosition;
		}
		
		try {
			updateNavGrid(gameData);
		} catch (SpiNotFoundException e) {
			return null;
		}
		
		if (!tileMover.isDone()) {
			return tileMover.getDirection();
		}
		
		int xStart = (int) startPosition.getX() / 16;
		int yStart = (int) startPosition.getY() / 16;
		
		if (goal == null) {
			try {
				goal = getGoal(gameData);
			} catch (PlayerException | SpiNotFoundException e) {
				return null;
			}
		}
		int xGoal = (int) goal.getX();
		int yGoal = (int) goal.getY();
		
		Vector2 newTarget;
		try {
			newTarget = weightedAStar(xStart, yStart, xGoal, yGoal);
		} catch (NoPathFoundException e) {
			return null;
		}
		
		if (newTarget != null) {
			tileMover.target = newTarget.times(16);
		} else {
			try {
				goal = getGoal(gameData);
			} catch (PlayerException | SpiNotFoundException e) {
				return null;
			}
		}
		
		return tileMover.getDirection();
	}
	
	/**
	 * - Calculates the optimal path between two points using the Weighted A* algorithm.
	 * - @param xStart the x-coordinate of the starting point
	 * - @param yStart the y-coordinate of the starting point
	 * - @param xGoal the x-coordinate of the goal point
	 * - @param yGoal the y-coordinate of the goal point
	 * - @return the optimal direction for the entity to move
	 */
	private Vector2 weightedAStar(int xStart, int yStart, int xGoal, int yGoal) throws NoPathFoundException {
		State initialState = new State(xStart, yStart, xGoal, yGoal);
		List<Node> path = treeSearch(initialState);
		
		if (path.size() <= 1) {
			return null;
		}
		
		Node second = path.get(1);
		
		return new Vector2(second.state.x, second.state.y);
	}
	
	private List<Node> treeSearch(State initialState) throws NoPathFoundException {
		Node initial_node = new Node(initialState, 0, 1000);
		PriorityQueue<Node> fringe = new PriorityQueue<>();
		fringe.add(initial_node);
		while (fringe.size() != 0) {
			Node node = fringe.poll();
			if (node.state.isGoal()) {
				return node.path;
			}
			List<Node> children = node.expand();
			fringe.addAll(children);
		}
//		System.out.println("no path found");
		throw new NoPathFoundException("Goal state is unreachable");
	}
}
