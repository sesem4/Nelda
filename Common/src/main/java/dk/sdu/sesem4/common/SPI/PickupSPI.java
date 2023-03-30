package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.entity.Item;

public interface PickupSPI {
    /**
     * Communicate that a item has been picked up
     * <br>
     * <br>
     * Pre-condition: Item and player have collided.<br>
     * post-condition: Item has been picked up by player.
     *
     * @param gameData The game data
     * @param item     Item to pickup
     */
    void pickup(GameData gameData, Entity item);
}
