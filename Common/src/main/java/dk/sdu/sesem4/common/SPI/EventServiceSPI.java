package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.GameData;
import dk.sdu.sesem4.common.data.events.Event;

public interface EventServiceSPI
{
    /**
     * Spawn enemy into the map
     * <br><br>
     * Pre-condition: A event has to be triggered.<br>
     * post-condition: The event has been handled.
     *
     * @param gameData The game data
     * @param event The triggered event
     */
    void eventHandler(GameData gameData, Event event);
}
