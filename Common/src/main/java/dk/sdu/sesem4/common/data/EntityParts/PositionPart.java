package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

public class PositionPart implements EntityPart{
    
    private Vector2 position;
    private Direction direction;

    /**
     * Constructs a PositionPart
     * @param position the Vector2 (x and y coordinates) the Entity should be positioned at
     * @param direction the Direction the Entity should be going in
     */
    public PositionPart(Vector2 position, Direction direction){
        this.position = position;
        this.direction = direction;
    }

    /**
     * Gets the Vector2 position of the Entity
     * @return position
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Sets the Vector2 position of the Entity
     * @param position the Vector2 (x and y coordinates) the Entity should be positioned at
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Moves the entity by adding the values of deltaPosition to current position
     * @param deltaPosition Vector2 x and y values that should be added to the current position
     */
    public void move(Vector2 deltaPosition) {
        this.setPosition(this.getPosition().plus(deltaPosition));
    }

    /**
     * Gets the Direction of the Entity
     * @return direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the Direction of the Entity
     * @param direction the direction the Entity should be going in
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void process(GameData gameData, Entity entity) {}
}
