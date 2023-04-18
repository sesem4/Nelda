package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.CollisionParts.Knockback;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

/**
 * @author @AnneLærke & Muhammed
 */

public class MovingPart implements EntityPart {

    private Knockback knockback;
    private int moveSpeed;

    /**
     * Constructs a MovingPart
     * @param moveSpeed the speed which the Entity should move with
     */
    public MovingPart(int moveSpeed){
        this.moveSpeed = moveSpeed;
    }

    /**
     * Gets the moving speed of Entity
     * @return moveSpeed
     */
    public int getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * Sets the moving speed of Entity
     * @param moveSpeed the speed which the Entity should move with
     */
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    /**
     * Sets the Knockback of the Entity
     * @param knockback the Knockback which should be applied to the Entity's MovingPart
     */
    public void setKnockback(Knockback knockback) {
        this.knockback = knockback;
    }

    /**
     * Gets the Knockback of the Entity
     * @return knockback
     */
    public Knockback getKnockback() {
        return this.knockback;
    }

    /**
     * Checks whether the Entity has been applied a Knockback
     * @return Boolean
     */
    public boolean isKnockedBack() {
        return knockback != null;
    }

    /**
     * Processes moving inputs
     * @param gameData
     * @param entity
     */
    @Override
    public void process(GameData gameData, Entity entity) {
        // Get the current position if the Entity
        PositionPart positionPart = entity.getEntityPart(PositionPart.class);

        // Check if the Entity is knocked back
        if (this.isKnockedBack()) {
            // If so, check whether the Knockback duration as run out
            if (knockback.hasRunOut()) {
                knockback = null;
            } else {
                // Otherwise, move in the direction set in the Knockback, with the knockback.speed and decrement the knockback duration
                Vector2 deltaPosition = new Vector2(knockback.getDirection()).times(knockback.getSpeed());
                positionPart.move(deltaPosition);
                knockback.decrementDuration();
                return;
            }
        }

        // Get the Entity Direction from the MovementController                -- should be gotten from MovementController
        Direction direction = positionPart.getDirection();
        positionPart.setDirection(direction);

        // Update Entity position
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
