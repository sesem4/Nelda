package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class LifePart implements EntityPart {

    private int life;

    /**
     * Constructs a LifePart
     * @param life the amount of life the Entity should have
     */
    public LifePart(int life) {
        this.life = life;
    }

    /**
     * Gets life value of variable life
     * @return life
     */
    public int getLife() {
        return life;
    }

    /**
     * Sets life value of variable life
     * @param life the value life should be set to
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Checks that Entity is alive
     * @return Boolean
     */
    public boolean isAlive() {
        return getLife() > 0;
    }

    /**
     * Checks that Entity is dead
     * @return Boolean
     */
    public boolean isDead() {
        return !this.isAlive();
    }

    /**
     * Kills the Entity, regardless of previous life value
     */
    public void kill() {
        this.setLife(0);
    }

    /**
     * Applies provided damage value to life
     * @param damage amount of damage to be dealt
     */
    public void doDamage(int damage) {
        this.setLife(this.getLife() - damage);
    }

    @Override
    public void process(GameData gameData, Entity entity) {}
}
