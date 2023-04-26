package dk.sdu.sesem4.player;

import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.data.process.Priority;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PlayerProcessingService implements ProcessingServiceSPI, PostProcessingServiceSPI {

	Player player;

	@Override
	public void process(GameData gameData, Priority priority) {
		//process the player for each game loop, change Movement, Position, LifePart, Path to Texture,
		for (Entity player : gameData.getGameEntities().getEntities(Player.class)) {
			PositionPart positionPart = player.getEntityPart(PositionPart.class);
			MovingPart movingPart = player.getEntityPart(MovingPart.class);
			LifePart lifePart = player.getEntityPart(LifePart.class);

			//set the direction of the player

			//set the texture based on the direction

			//process all the parts
			positionPart.process(gameData, player);
			movingPart.process(gameData, player);
			lifePart.process(gameData, player);

		}
	}

	@Override
	public void postProcess(GameData gameData, Priority priority) {
		// postProcess check if the movement matches the direction set in process,
	}

	public Entity createPlayer() {
		//create a new player
		this.player = new Player();
		//set the speed of the player
		this.player.addEntityPart(new MovingPart(this.player.getSpeed()));
		//set the position of the player
		this.player.addEntityPart(new PositionPart(new Vector2(
				this.player.getStartPositionX(),
				this.player.getStartPositionY()),
				this.player.getDirection()));
		//set the life of player
		this.player.addEntityPart(new LifePart(this.player.getHearts()));
		//load all the player textures and save it in an array to be processed
		this.player.setTexturesPath(this.loadTextures());
		return this.player;
	}

	private List<Path> loadTextures() {
		//load all player textures into a Path array
		List<Path> entityTexturesList = new ArrayList<Path>();

		//save each path to the png files into an array of paths.
		for (int i = 1; i <= 5; i++) {
			try {
				Path path = Path.of("Zelda" + i + ".png");
				entityTexturesList.add(path);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return entityTexturesList;
	}

	public Entity getPlayer() {
		return this.player;
	}

}
