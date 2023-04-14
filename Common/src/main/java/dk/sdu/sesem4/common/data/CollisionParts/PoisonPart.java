package dk.sdu.sesem4.common.data.CollisionParts;

import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class PoisonPart implements CollisionPart {

    private int ticDamage;
    private int poisonDuration;
    private int poisonCounter;
    private boolean poisoned;

    public PoisonPart(int ticDamage, int poisonDuration) {
        this.ticDamage = ticDamage;
        this.poisonDuration = poisonDuration;
        this.poisonCounter = 0;
        this.poisoned = false;
    }

    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (poisoned) {
            LifePart lifePart = entity.getEntityPart(LifePart.class);
            int life = lifePart.getLife();

            lifePart.setLife(life - ticDamage);

            poisonCounter++;
            if (poisonCounter == poisonDuration) {
                poisonCounter = 0;
                poisoned = false;
            }
        }
    }
}
