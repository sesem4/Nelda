package dk.sdu.sesem4.common.data.CollisionParts;

import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class DamagePart implements CollisionPart {

    private int damage;

    public DamagePart(int damage) {
        this.damage = damage;
    }

    public void process(GameData gameData, Entity entity) {
        LifePart lifePart = entity.getPrt(LifePart.class);
        int life = lifePart.getLife();

        lifePart.setLife(life - damage);
    }
}
