package dk.sdu.sesem4.SmoothRandomMovement;

import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;
import dk.sdu.sesem4.common.util.SPILocator;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmoothRandomMovementController implements MovementControllerSPI {
    private Vector2 goalPosition;

    private int xGoal;
    private int yGoal;

    private int xStart;
    private int yStart;

    private int counter = 0;

    @Override
    public Direction getMovement(GameData gameData, Entity entity) {


        // Get the current position of the entity
        PositionPart positionPart = entity.getEntityPart(PositionPart.class);

        Vector2 startPosition = positionPart.getPosition();
        this.xStart = (int) startPosition.getX()/16;
        this.yStart = (int) startPosition.getY()/16;

        // Create a 2D array to represent the grid TODO: replace with SPILocator
        MapSPI mapSPI = SPILocator.locateAll(MapSPI.class).get(0);
        if (goalPosition == null || xStart == xGoal && yStart == yGoal || counter >= 600) {
            System.out.println(counter);
            this.goalPosition = mapSPI.getRandomPassableTile(gameData);
            this.xGoal = (int) goalPosition.getX();
            this.yGoal = (int) goalPosition.getY();
            System.out.println("New goal position: " + this.xGoal + ", " + this.yGoal);
            counter = 0;
        }

        counter++;
        // Find the goal position
        return smoothRandomMovement(xStart, yStart, xGoal, yGoal);
    }

    private Direction smoothRandomMovement(int xStart, int yStart, int xGoal, int yGoal) {


        if (yStart > yGoal) {
            return Direction.DOWN;
        }
        if (xStart > xGoal) {
            return Direction.LEFT;
        }
        if (xStart < xGoal) {
            return Direction.RIGHT;
        }
        if ( yStart < yGoal) {
            return Direction.UP;
        }

        // Prepend the current position to the path
        return Direction.NONE;
    }


}