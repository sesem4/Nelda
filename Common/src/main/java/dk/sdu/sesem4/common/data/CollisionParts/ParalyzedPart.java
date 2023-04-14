package dk.sdu.sesem4.common.data.CollisionParts;

import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class ParalyzedPart implements CollisionPart {

    private boolean paralyzed;
    private int paralyzedTimer;
    private int paralyzedDuration;
    private int speed;

    public ParalyzedPart(int paralyzedDuration, int speed) {
        this.paralyzed = false;
        this.paralyzedTimer = 0;
        this.paralyzedDuration = paralyzedDuration;
        this.speed = speed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (paralyzed) {
            int defaultSpeed = speed;
            MovingPart movingPart = entity.getEntityPart(MovingPart.class); movingPart.setMoveSpeed(0);

            paralyzedTimer++;
            if (paralyzedTimer == paralyzedDuration) {
                movingPart.setMoveSpeed(defaultSpeed);
                paralyzedTimer = 0;
                paralyzed = false;
            }
        }
    }
}
