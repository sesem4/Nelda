package dk.sdu.sesem4.player;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class PlayerPlugin implements PluginServiceSPI {

	private PlayerProcessingService playerProcessingService;

	@Override
	public void start(GameData gameData) {

		this.playerProcessingService = new PlayerProcessingService();

		// create a new player into the game
		gameData.getGameEntities().addEntity(this.playerProcessingService.createPlayer());

		System.out.println("PlayerPlugin started");

	}

	@Override
	public void stop(GameData gameData) {
		// Remove the player from the game
		gameData.getGameEntities().removeEntity(this.playerProcessingService.getPlayer());
		System.out.println("PlayerPlugin stopped");

	}
}
