package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.CollisionParts.Knockback;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

/**
 *
 * @author @AnneLærke & Muhammed
 */
public class MovingPart implements EntityPart {
    private Knockback knockback;
    private int moveSpeed;

    public MovingPart(int moveSpeed){
        this.moveSpeed = moveSpeed;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setKnockback(Knockback knockback) {
        this.knockback = knockback;
    }

    public boolean isKnockedBack() {
        return knockback != null;
    }

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
        switch (direction){
            case UP -> y += getMoveSpeed();
            case DOWN -> y -= getMoveSpeed();
            case LEFT -> x += getMoveSpeed();
            case RIGHT -> x -= getMoveSpeed();
        }
        positionPart.getPosition().setX(x);
        positionPart.getPosition().setY(y);
    }
}
