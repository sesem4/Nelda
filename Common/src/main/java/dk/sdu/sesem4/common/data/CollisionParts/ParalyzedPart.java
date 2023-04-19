package dk.sdu.sesem4.common.data.CollisionParts;

import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

/**
 * The ParalyzedPart is a CollisionPart that is applied to an Entity upon collision with another Entity.
 */
public class ParalyzedPart implements CollisionPart {
    /**
     * A boolean that checks if the Entity is paralyzed or not.
     */
    private boolean paralyzed;
    /**
     * A timer that counts how long the Entity has been paralyzed.
     */
    private int paralyzedTimer;
    /**
     * The duration for how long the Entity should be paralyzed upon collision.
     */
    private int paralyzedDuration;

    /**
     * The speed of the Entity.
     */
    private int speed;

    /**
     * Constructs a ParalyzedPart
     * @param paralyzedDuration for how long the Entity should be paralyzed upon collision
     * @param speed the speed of the Entity
     */
    public ParalyzedPart(int paralyzedDuration, int speed) {
        this.paralyzed = false;
        this.paralyzedTimer = 0;
        this.paralyzedDuration = paralyzedDuration;
        this.speed = speed;
    }

    /**
     * Process the ParalyzedPart. Here the Entity is paralyzed for a set duration if it collides with another Entity.
     * @param gameData the GameData
     * @param entity the Entity
     */
    public void process(GameData gameData, Entity entity) {
        if (paralyzed) {
            int defaultSpeed = this.speed;
            MovingPart movingPart = entity.getEntityPart(MovingPart.class);
            movingPart.setMoveSpeed(0);

            paralyzedTimer++;
            if (this.paralyzedTimer == this.paralyzedDuration) {
                movingPart.setMoveSpeed(defaultSpeed);
                this.paralyzedTimer = 0;
                this.paralyzed = false;
            }
        }
    }
}
