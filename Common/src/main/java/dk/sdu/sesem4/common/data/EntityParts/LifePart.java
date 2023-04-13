package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class LifePart implements EntityPart {
    private int life;
    public LifePart(int life){
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isAlive() {
        return getLife() > 0;
    }

    public boolean isDead() {
        return !this.isAlive();
    }

    public void kill() {
        this.setLife(0);
    }

    public void doDamage(int damage) {
        this.setLife(this.getLife() - damage);
    }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
