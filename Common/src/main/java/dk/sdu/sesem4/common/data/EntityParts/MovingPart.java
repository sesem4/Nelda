package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.CollisionParts.Knockback;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

/**
 * A class that represents the MovingPart.
 * @author @AnneLærke & Muhammed & Jakob
 */
public class MovingPart implements EntityPart {

    private Knockback knockback;
    // The speed that the entity should move.
    private int moveSpeed;

    /**
     * The constructor for the MovingPart class.
     * @param moveSpeed The speed that the entity should move.
     */
    public MovingPart(int moveSpeed){
        this.moveSpeed = moveSpeed;
    }

    /**
     * Get the speed that the entity should move.
     * @return The speed that the entity should move.
     */
    public int getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * Set the speed that the entity should move.
     * @param moveSpeed The speed that the entity should move.
     */
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    /**
     * Set the knockback of the entity.
     * @param knockback The knockback of the entity.
     */
    public void setKnockback(Knockback knockback) {
        this.knockback = knockback;
    }

    /**
     * Check if the entity is knocked back.
     * @return True if the entity is knocked back, false otherwise.
     */
    public boolean isKnockedBack() {
        return knockback != null;
    }

    /**
     * A method that processes the MovingPart.
     * @param gameData The GameData object.
     * @param entity The Entity object.
     */
    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getEntityPart(PositionPart.class);
        if (this.isKnockedBack()) {
            if (knockback.hasRunOut()) {
                knockback = null;
            } else {
                // move in knockback direction with knockback.speed and decrement knockback timer
                // return
                Vector2 deltaPosition = new Vector2(knockback.getDirection()).times(knockback.getSpeed());
                positionPart.move(deltaPosition);
                knockback.decrementDuration();
                return;
            }
        }
        Direction direction = positionPart.getDirection(); // should be gotten from MovementController

        positionPart.setDirection(direction);

        float x = positionPart.getPosition().getX();
        float y = positionPart.getPosition().getY();
        switch (direction) {
            case UP:
                y += getMoveSpeed();
                break;
            case DOWN:
                y -= getMoveSpeed();
                break;
            case LEFT:
                x += getMoveSpeed();
                break;
            case RIGHT:
                x -= getMoveSpeed();
                break;
        }
        positionPart.getPosition().setX(x);
        positionPart.getPosition().setY(y);
    }
}
