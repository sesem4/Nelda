package dk.sdu.sesem4.ai.simple;

import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;
import dk.sdu.sesem4.common.util.SPILocator;

import java.util.*;

public class SimpleAiMovementController implements MovementControllerSPI {
    /** The final goal for the movement, stored in Tile coordinates */
    private Vector2 goal;
    /** The current path of tiles to the goal, stored in Tile coordinates */
    private List<Vector2> path;
    /** Map SPI for map utilities */
    private MapSPI mapSPI;
    /** Cached navigation grid containing which tiles is passable. Used for path search. */
    private boolean[][] navGrid;

    public SimpleAiMovementController() throws RuntimeException {
        this.path = new LinkedList<>();

        /// Get MapSPI for map utilities
        List<MapSPI> mapSPIs =  SPILocator.locateAll(MapSPI.class);
        if (mapSPIs.size() == 0) {
            throw new RuntimeException("MapSPI not found");
        }
        mapSPI = mapSPIs.get(0);
    }

    @Override
    public Direction getMovement(GameData gameData, Entity entity) {
        // Set a new goal, if either no goal is currently set, or the path is completed
        if (goal == null || path.size() == 0) {
            // Get navigation grid, to ensure up-to-date information about map
            this.navGrid = mapSPI.getNavGrid(gameData);

            // Generate path
            generateRandomGoal(gameData);
            calculatePath(gameData, entity);
        }

        // Return the current direction to get to the next tile in path
        return getDirectionToNextTile(gameData, entity);
    }

    private void generateRandomGoal(GameData gameData) {
        Vector2 goal = mapSPI.getRandomPassableTile(gameData);
        this.goal = new Vector2((int) goal.getX(), (int) goal.getY());
    }

    /**
     * Get direction to next tile, such that the direction returned over time, will result in the entity provided being moved to the next tile.
     *
     * @param gameData Game data
     * @param entity The entity which has to be moved
     * @return The direction the entity should move to be moved to the next tile in the path
     */
    private Direction getDirectionToNextTile(GameData gameData, Entity entity) {
        // Get position of entity
        PositionPart position = entity.getEntityPart(PositionPart.class);
        if (position == null) {
            return null;
        }

        // Get Vector2 positions for where to go from and where to go to
        Vector2 nextTile = path.get(0);
        Vector2 currentPosition = position.getPosition();

        // World position extraction for current position of entity
        int columnCurrent = (int) currentPosition.getX();
        int rowCurrent = (int) currentPosition.getY();

        // World position extraction for goal tile
        int colGoal = (int) (nextTile.getX() * GameWorld.TILE_SIZE);
        int rowGoal = (int) (nextTile.getY() * GameWorld.TILE_SIZE);

        // x-coordinate difference
        int xDifference = colGoal - columnCurrent;
        // y-coordinate difference
        int yDifference = rowGoal - rowCurrent;

        Direction direction = null;
        // if y-goal position is bigger than y-current position, go UP
        if (yDifference > 0){
            direction =  Direction.UP;
        }
        // if y-goal position  is less than y-current position, go DOWN
        if (yDifference < 0){
            direction =  Direction.DOWN;
        }
        // if x-goal position is bigger than x-current position, go RIGHT
        if (xDifference > 0){
            direction =  Direction.RIGHT;
        }
        // if x-goal position is less than x-current position, go LEFT
        if (xDifference < 0){
            direction =  Direction.LEFT;
        }

        // If direction is null, the current position and goal position must be the same.
        // We can now remove the goal tile from the path.
        if (direction == null) {
            path.remove(0);
        }

        return direction;
    }

    /**
     * Calculate the path from current entity position to the set goal position
     *
     * @param gameData Game data
     * @param entity The entity which has to be moved
     */
    private void calculatePath(GameData gameData, Entity entity) {
        // Get position of entity
        PositionPart position = entity.getEntityPart(PositionPart.class);
        if (position == null) {
            return;
        }

        // Set start and end position
        Vector2 startPosition = new Vector2(position.getPosition().getX() / GameWorld.TILE_SIZE, position.getPosition().getY() / GameWorld.TILE_SIZE);
        Vector2 endPosition = this.goal;

        // Setup data structures
        Queue<Vector2> queue = new LinkedList<>();
        HashMap<Vector2, Vector2> visited = new HashMap<>();

        // Set start parameters
        visited.put(startPosition, null);
        Vector2 current = startPosition;

        // Go through the tile network until the end position is reached in breath-first approach
        while (!current.equals(endPosition)) {
            // Get successors to current node
            LinkedList<Vector2> neighbors = getSuccessor(gameData, current);

            // If no neighbors is present, skip the search
            if (neighbors == null) {
                continue;
            }

            // Go through neighbors and add them to the queue to be visited in breath-first approach
            for (Vector2 neighbor : neighbors) {
                if (!keyExists(visited.keySet(), neighbor)) {
                    visited.put(neighbor, current);
                    queue.add(neighbor);
                }
            }

            // If nothing is left in the queue, end the search
            if (queue.size() == 0) {
                break;
            }

            current = queue.remove();
        }

        // Setup data for reverse traversal
        LinkedList<Vector2> newPath = new LinkedList<>();
        newPath.addFirst(current);

        // Go through path from the end position to the start position
        while (!current.equals(startPosition)) {
            current = visited.get(current);
            newPath.addFirst(current);
        }

        this.path = newPath;
    }

    /**
     * Check if Vector2 exist in set
     *
     * @param keys Set to search
     * @param key Key so search for
     * @return Boolean, true if key is in key set, false if not.
     */
    private boolean keyExists(Set<Vector2> keys, Vector2 key) {
        for (Vector2 currentKey : keys) {
            if (currentKey.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get successors to the current tile
     *
     * @param gameData Game data
     * @param current Vector2 to get successors for
     * @return List of Vector2 which is successor i node graph to the provided Vector2. The list may be empty, but not null.
     */
    private LinkedList<Vector2> getSuccessor(GameData gameData, Vector2 current) {
        // Ensure nav grid exists
        if (this.navGrid == null) {
            return new LinkedList<>();
        }

        // Get current tile coordinate in x and y position
        int x = (int) current.getX();
        int y = (int) current.getY();

        // Ensure the current node is inside nav grid
        if (x >= navGrid.length || y >= navGrid[x].length) {
            return new LinkedList<>();
        }

        LinkedList<Vector2> neighbors = new LinkedList<>();

        // Generate successors which may be on all four sides
        if (navGrid.length > x + 1 && navGrid[x + 1][y]) {
            neighbors.add(new Vector2(x + 1, y));
        }
        if (0 <= x - 1 && navGrid[x - 1][y]) {
            neighbors.add(new Vector2(x - 1, y));
        }
        if (navGrid[x].length > y + 1 && navGrid[x][y + 1]) {
            neighbors.add(new Vector2(x, y + 1));
        }
        if (0 <= y - 1 && navGrid[x][y - 1]) {
            neighbors.add(new Vector2(x, y - 1));
        }

        return neighbors;
    }
}
