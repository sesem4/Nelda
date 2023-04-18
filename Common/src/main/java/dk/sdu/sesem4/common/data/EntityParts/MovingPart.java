package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.CollisionParts.Knockback;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

/**
 * Moving part, that handles and represents moving in the game
 * 
 * @author @AnneLærke & Muhammed & Jakob
 */
public class MovingPart implements EntityPart {

    private Knockback knockback;
    // The speed that the entity should move.
    private int moveSpeed;

    /**
     * The constructor for the MovingPart class.
     * 
     * @param moveSpeed the speed which the Entity should move with
     */
    public MovingPart(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    /**
     * Get the speed that the entity should move.
     * 
     * @return The speed that the entity should move.
     */
    public int getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * Set the speed that the entity should move.
     * 
     * @param moveSpeed The speed of which the entity should move.
     */
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    /**
     * Sets the Knockback of the Entity
     * 
     * @param knockback the Knockback which should be applied to the Entity's
     *                  MovingPart
     */
    public void setKnockback(Knockback knockback) {
        this.knockback = knockback;
    }

    /**
     * Gets the Knockback of the Entity
     * 
     * @return knockback
     */
    public Knockback getKnockback() {
        return this.knockback;
    }

    /**
     * Check if the entity is knocked back.
     * 
     * @return True if the entity is knocked back, false otherwise.
     */
    public boolean isKnockedBack() {
        return knockback != null;
    }

    /**
     * A method that processes the MovingPart.
     * 
     * @param gameData The GameData object.
     * @param entity   The Entity object.
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
                // Otherwise, move in the direction set in the Knockback, with the
                // knockback.speed and decrement the knockback duration
                Vector2 deltaPosition = new Vector2(knockback.getDirection()).times(knockback.getSpeed());
                positionPart.move(deltaPosition);
                knockback.decrementDuration();
                return;
            }
        }

        // Get the Entity Direction from the MovementController -- should be gotten from
        // MovementController
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
