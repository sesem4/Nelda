package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

import static java.lang.Math.abs;

public class PositionPart implements EntityPart{
    private Vector2 position;
    private Direction direction;

    public PositionPart(Vector2 position){
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void move(Vector2 deltaPosition) {
        this.setPosition(this.getPosition().plus(deltaPosition));
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirectionTo(Vector2 position) {
        Vector2 deltaPosition = this.getPosition().minus(position);
        if (abs(deltaPosition.getX()) > abs(deltaPosition.getY())) {
            if (deltaPosition.getX() > 0) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        } else {
            if (deltaPosition.getY() > 0) {
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        }
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
