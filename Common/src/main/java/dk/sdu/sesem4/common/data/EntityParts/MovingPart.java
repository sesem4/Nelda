package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.util.Direction;

/**
 *
 * @author @AnneLærke & Muhammed
 */
public class MovingPart implements EntityPart{

    private int speed;

    public MovingPart(int speed){
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {

        PositionPart positionPart = entity.getPrt(PositionPart.class);
        float x = positionPart.getPosition().getX();
        float y = positionPart.getPosition().getY();
        Direction direction = entity.getDirection();
        switch (direction){
            case UP -> y += speed;
            case DOWN -> y -= speed;
            case LEFT -> x += speed;
            case RIGHT -> x -= speed;
        }
        positionPart.getPosition().setX(x);
        positionPart.getPosition().setY(y);
    }
}
