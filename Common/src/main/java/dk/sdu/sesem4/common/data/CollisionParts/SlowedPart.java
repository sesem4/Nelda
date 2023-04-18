package dk.sdu.sesem4.common.data.CollisionParts;

import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class SlowedPart implements CollisionPart {

    private boolean slowed;
    private int slowedTimer;
    private int slowedDuration;
    private int speed;

    public SlowedPart(int paralyzedDuration, int speed) {
        this.slowed = false;
        this.slowedTimer = 0;
        this.slowedDuration = paralyzedDuration;
        this.speed = speed;
    }

//    @Override
    public void process(GameData gameData, Entity entity) {
        if (this.slowed) {
            int defaultSpeed = this.speed;
            MovingPart movingPart = entity.getEntityPart(MovingPart.class);
            movingPart.setMoveSpeed(movingPart.getMoveSpeed() / 2);

            this.slowedTimer++;
            if (this.slowedTimer == this.slowedDuration) {
                movingPart.setMoveSpeed(defaultSpeed);
                this.slowedTimer = 0;
                this.slowed = false;
            }
        }
    }
}
