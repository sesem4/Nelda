package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

public class PositionPart implements EntityPart{
    private Vector2 position;
    private Direction direction;

    public PositionPart(Vector2 position){
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        float dt = gameData.getDeltaTime();

    }
}
