package dk.sdu.sesem4.common.data.CollisionParts;

import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class KnockBackPart implements CollisionPart {

    private Entity attacker;
    private int speed;
    private int defaultSpeed;
    private int knockBackDistance;
    private int knockBackSpeed;
    private int knockBackCounter;
    private boolean knockedBack;
    private boolean worldCollision;

    /**
     *
     * @param attacker
     * @param speed
     * @param defaultSpeed
     * @param knockBackSpeed
     * @param knockBackDistance
     */

    public KnockBackPart(Entity attacker, int speed, int defaultSpeed, int knockBackSpeed, int knockBackDistance) {
        this.attacker = attacker;
        this.speed = speed;
        this.defaultSpeed = defaultSpeed;
        this.knockBackSpeed = knockBackSpeed;
        this.knockBackDistance = knockBackDistance;
        this.knockBackCounter = 0;
        this.knockedBack = false;
        this.worldCollision = false;
    }

    public void knockBack(Entity entity) {
        entity.setDirection(attacker.getDirection());

        MovingPart movingPart = entity.getPrt(MovingPart.class); movingPart.setSpeed(speed + knockBackSpeed);
    }

    public void stopKnockBack(Entity entity) {
        knockBackCounter = 0;
        knockedBack = false;
        MovingPart movingPart = entity.getPrt(MovingPart.class); movingPart.setSpeed(defaultSpeed);
    }
    public void setKnockedBack(boolean knockedBack) {
        this.knockedBack = knockedBack;
    }

    public boolean isKnockedBack() {
        return knockedBack;
    }

    // COLLISION SYSTEM SHOULD CHECK THIS FLAG IF COLLIDED WITH WORLD ELEMENTS SUCH AS EDGES, TREES, ETC.
    public void setWorldCollision(boolean worldCollision) {
        this.worldCollision = worldCollision;
    }

    public boolean isWorldCollision() {
        return worldCollision;
    }

    public void process(GameData gameData, Entity entity) {
        if (knockedBack) {
            knockBack(entity);

// ---------------------------------------------------------------------------------------------------------------------------
// WE SOMEHOW NEED A CHECK FOR COLLISION WITH SURROUNDINGS, SUCH AS TREES, WHICH WILL STOP THE KNOCK BACK FROM GOING FURTHER
            if(worldCollision) {
                stopKnockBack(entity);
            }
            // OTHERWISE, MOVE ALONG
// ---------------------------------------------------------------------------------------------------------------------------
            //------> I HAVE QUESTIONS FOR THIS STUFF ^^

            // LASTLY, WE ARE GOING TO INCREASE THE [knockBackCounter] UNTIL THE SET KNOCK BACK DISTANCE HAS BEEN MET
            knockBackCounter++;
            if (knockBackCounter == knockBackDistance) {
                stopKnockBack(entity);
            }
        }
    }
}
