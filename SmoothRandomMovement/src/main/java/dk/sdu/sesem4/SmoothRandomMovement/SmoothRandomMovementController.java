package dk.sdu.sesem4.SmoothRandomMovement;

import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmoothRandomMovementController implements MovementControllerSPI {
    private Vector2 goalPosition;

    @Override
    public Direction getMovement(GameData gameData, Entity entity) {
        // Get the current position of the entity
        PositionPart positionPart = entity.getEntityPart(PositionPart.class);
//        Rectangle startPosition = new Rectangle(positionPart.getPosition(),positionPart.getSize());

        Vector2 startPosition = positionPart.getPosition();


//        MapSPI mapSPI = SPILocator.locateAll(MapSPI.class).get(0);

//        SPILocator locator = SPILocator.locateAll(SPILocator.class).get(0);
//        if(locator != null){
//            SPILocator.locateAll(MapSPI.class).get(0);
//            this.grid = mapSPI.getNavGrid();
//        }
        // Create a 2D array to represent the grid TODO: replace with SPILocator
        boolean[][] grid = new boolean[][]{
                {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false},
                {false,false,true,true,true,true,true,true,true,true,true,true,true,true,false,false},
                {false,false,true,true,true,true,true,true,true,true,true,true,true,true,false,false},
                {false,false,true,true,true,true,true,true,true,true,true,true,true,true,false,false},
                {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true},
                {false,true,true,true,true,true,true,true,true,true,true,false,false,false,false,false},
                {false,false,true,true,true,true,true,true,true,false,false,false,false,false,false,false},
                {false,false,false,true,true,true,true,true,true,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,true,true,true,false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false,true,true,false,false,false,false,false,false,false}
        };


        // Find the goal position
        return smoothRandomMovement(startPosition, grid);
    }

    private Vector2 createGoalPosition(boolean[][] grid){
        Random random = new Random();
        List<Vector2> obstacles = removeObstacles(grid);
        return obstacles.get(random.nextInt(obstacles.size()));

    }

    private List<Vector2> removeObstacles(boolean[][] grid) {
        List<Vector2> obstacles = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j]) {
                    obstacles.add(new Vector2((i * 16), (j * 16)));
                }
            }
        }
        return obstacles;
    }

    private Direction smoothRandomMovement(Vector2 currentPosition, boolean[][] grid) {

        Random random = new Random();

        // Base case: If the current position is the goal position, return an empty path
        if (currentPosition.equals(goalPosition) || goalPosition == null || random.nextInt(100) < 4) {
            this.goalPosition = createGoalPosition(grid);
        }

        if (currentPosition.getX() > goalPosition.getX()) {
            return Direction.LEFT;
        }
        if (currentPosition.getX() < goalPosition.getX()) {
            return Direction.RIGHT;
        }
        if (currentPosition.getY() > goalPosition.getY()) {
            return Direction.DOWN;
        }
        if ( currentPosition.getY() < goalPosition.getY()) {
            return Direction.UP;
        }
        this.goalPosition = createGoalPosition(grid);
        // Prepend the current position to the path
        return Direction.NONE;
    }


}