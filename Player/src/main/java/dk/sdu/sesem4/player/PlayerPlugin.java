package dk.sdu.sesem4.player;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.data.process.Priority;

public class PlayerPlugin implements PluginServiceSPI, ProcessingServiceSPI, PostProcessingServiceSPI {

    PlayerProcessingService playerProcessingService = new PlayerProcessingService();
    @Override
    public void start(GameData gameData) {
        this.playerProcessingService = new PlayerProcessingService();
        this.playerProcessingService.loadEntity();
        System.out.println("Player has been loaded");
        gameData.setGameEntities(new GameEntities());

    }

    @Override
    public void stop(GameData gameData) {
        this.playerProcessingService = null;

    }

    @Override
    public void postProcess(GameData gameData, Priority priority) {
        playerProcessingService.postProcess(gameData, priority);
    }

    @Override
    public void process(GameData gameData, Priority priority) {
         playerProcessingService.process(gameData, priority);
    }
}
