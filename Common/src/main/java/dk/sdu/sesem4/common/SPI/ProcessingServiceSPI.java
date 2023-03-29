package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;

public interface ProcessingServiceSPI {
    /**
     * Main processing system
     * <br><br>
     * Pre-condition: A complete game loop has been completed.<br>
     * post-condition: The system has been processed.
     *
     * @param gameData The game data
     * @param priority The priority, which is to be run for the current process round
     */
    void process(GameData gameData, Priority priority);
}
