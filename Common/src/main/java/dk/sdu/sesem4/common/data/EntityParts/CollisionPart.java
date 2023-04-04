package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

/**
 * @author Muhammed and Anne Lærke
 */
public class CollisionPart implements EntityPart {

   public boolean collided(Entity entity1, Entity entity2) {

       PositionPart entity1Part = entity1.getPrt(PositionPart.class);
       PositionPart entity2Part = entity2.getPrt(PositionPart.class);

    throw new UnsupportedOperationException("Unsupported yet");
   }

    @Override
    public void process(GameData gameData, Entity entity) {

    }
}
