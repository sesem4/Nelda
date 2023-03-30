package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;

public interface PostProcessingServiceSPI {
    /**
     * Processing after the main processing has completed
     * <br><br>
     * Pre-condition: ProcessingService has finished the main processing load.<br>
     * post-condition: The system has been post processed.
     *
     * @param gameData The game data
     * @param priority The priority, which is to be run for the current process round
     */
    void postProcess(GameData gameData, Priority priority);
}
