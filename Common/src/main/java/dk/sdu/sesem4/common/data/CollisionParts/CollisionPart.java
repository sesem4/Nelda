package dk.sdu.sesem4.common.data.CollisionParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public interface CollisionPart {
    public void process(GameData gameData, Entity entity);
}
