package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.entity.Item;

public interface PickupSPI {
    /**
     /**
     * Spawn enemy into the map
     * <br><br>
     * Pre-condition: Item and player have collided.<br>
     * post-condition: Item has been picked up by player.
     *
     * @param gameData The game data
     * @param item Item to pickup
     *
     * @return The item that is to be picked up
     */
    Item pickup(GameData gameData, Entity item);
}
