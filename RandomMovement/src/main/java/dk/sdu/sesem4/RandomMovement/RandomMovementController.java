package dk.sdu.sesem4.RandomMovement;

import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.util.Direction;

import java.util.Random;

/**
 * This class handles the random movement of the enemy.
 */
public class RandomMovementController implements MovementControllerSPI {
    /**
     * This is used for the random movement of the enemy.
     */
    protected Random random;
    public RandomMovementController(){
        this.random = new Random();
    }
    @Override
    public Direction getMovement(GameData gameData, Entity entity) {
        int randomInt = random.nextInt(5);
        // checks which random number is generated and returns the corresponding direction
        switch (randomInt){
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
            default:
                return null;
        }


    }
}
