package dk.sdu.sesem4.RandomMovement;

import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.util.Direction;

import java.util.Random;

public class RandomMovementController implements MovementControllerSPI {
    private Random random;
    public RandomMovementController(){
        this.random = new Random();
    }
    @Override
    public Direction getMovement(GameData gameData, Entity entity) {
        int randomInt = random.nextInt(0,5);
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
