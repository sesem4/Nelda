package dk.sdu.sesem4.SimpleAIMovement;

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
    private Set<Vector2> states;
    private MapSPI mapSPI;
    private boolean[][] navGrid;

    public SimpleAiMovementController() throws RuntimeException {
        this.path = new LinkedList<>();
        this.states = new HashSet<>();

        List<MapSPI> mapSPIs =  SPILocator.locateAll(MapSPI.class);
        if (mapSPIs.size() == 0) {
            throw new RuntimeException("MapSPI not found");
        }
        mapSPI = mapSPIs.get(0);
    }

    @Override
    public Direction getMovement(GameData gameData, Entity entity) {
        if (goal == null || path.size() == 0) {
            this.navGrid = mapSPI.getNavGrid(gameData);

            generateRandomGoal(gameData);
            calculatePath(gameData, entity);
        }

        return getDirectionToNextTile(gameData, entity);
    }

    private void generateRandomGoal(GameData gameData) {
        Vector2 goal = mapSPI.getRandomPassableTile(gameData);
        this.goal = getState((int) goal.getX(), (int) goal.getY());
        this.path.add(goal);
    }

    /**
     * Get direction to next tile, such that the direction returned over time, will result in the entity provided being moved to the next tile.
     *
     * @param gameData Game data
     * @param entity The entity which has to be moved
     * @return The direction the entity should move to be moved to the next tile in the path
     */
    private Direction getDirectionToNextTile(GameData gameData, Entity entity) {
        PositionPart position = entity.getEntityPart(PositionPart.class);
        if (position == null) {
            return null;
        }

        Vector2 nextTile = path.get(0);
        Vector2 currentPosition = position.getPosition();

        int columnCurrent = (int) currentPosition.getX();
        int rowCurrent = (int) currentPosition.getY();

        int colGoal = (int) (nextTile.getX() * GameWorld.TILE_SIZE);
        int rowGoal = (int) (nextTile.getY() * GameWorld.TILE_SIZE);

        // y-coordinate
        int yDifference = rowGoal - rowCurrent;
        // x-coordinate
        int xDifference = colGoal - columnCurrent;

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

        if (direction == null) {
            path.remove(0);
        }

        return direction;
    }

    private void calculatePath(GameData gameData, Entity entity) {
        PositionPart position = entity.getEntityPart(PositionPart.class);
        if (position == null) {
            return;
        }

        Vector2 currentPosition = position.getPosition();

        int columnCurrent = (int) currentPosition.getX() / GameWorld.TILE_SIZE;
        int rowCurrent = (int) currentPosition.getY() / GameWorld.TILE_SIZE;

        Vector2 startPosition = getState(columnCurrent, rowCurrent);
        Vector2 endPosition = this.goal;

        Queue<Vector2> queue = new LinkedList<>();
        HashMap<Vector2, Vector2> visited = new HashMap<>();

        visited.put(startPosition, null);

        Vector2 current = startPosition;

        while (!current.equals(endPosition)) {
            LinkedList<Vector2> neighbors = getSuccessor(gameData, current);

            if (neighbors == null) {
                break;
            }

            for (Vector2 neighbor : neighbors) {
                if (!keyExists(visited.keySet(), neighbor)) {
                    visited.put(neighbor, current);
                    queue.add(neighbor);
                }
            }

            if (queue.size() == 0) {
                break;
            }

            current = queue.remove();
        }

        LinkedList<Vector2> newPath = new LinkedList<>();
        newPath.addFirst(current);

        while (!current.equals(startPosition)) {
            current = visited.get(current);
            newPath.addFirst(current);
        }

        this.path = newPath;
    }

    private boolean keyExists(Set<Vector2> keys, Vector2 key) {
        for (Vector2 currentKey : keys) {
            if (currentKey.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private LinkedList<Vector2> getSuccessor(GameData gameData, Vector2 current) {
        if (this.navGrid == null) {
            return new LinkedList<>();
        }

        int x = (int) current.getX();
        int y = (int) current.getY();

        if (x >= navGrid.length || y >= navGrid[x].length) {
            return null;
        }

        LinkedList<Vector2> neighbors = new LinkedList<>();

        if (navGrid.length > x + 1 && navGrid[x + 1][y]) {
            neighbors.add(getState(x + 1, y));
        }
        if (0 <= x - 1 && navGrid[x - 1][y]) {
            neighbors.add(getState(x - 1, y));
        }
        if (navGrid[x].length > y + 1 && navGrid[x][y + 1]) {
            neighbors.add(getState(x, y + 1));
        }
        if (0 <= y - 1 && navGrid[x][y - 1]) {
            neighbors.add(getState(x, y - 1));
        }

        return neighbors;
    }

    private Vector2 getState(int x, int y) {
        Vector2 lookup = new Vector2(x, y);

        for (Vector2 state : this.states) {
            if (state.equals(lookup)) {
                return lookup;
            }
        }

        this.states.add(lookup);

        return lookup;
    }
}
