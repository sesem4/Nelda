package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
/**
 * An interface that represents the EntityParts.
 */
public interface EntityPart {
/**
     * A method that processes the EntityPart.
     * @param gameData The GameData object.
     * @param entity The Entity object.
     */
    void process(GameData gameData, Entity entity);
}
