package dk.sdu.sesem4.player;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.rendering.SpriteData;
import dk.sdu.sesem4.common.util.Direction;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PlayerPlugin implements PluginServiceSPI {

	protected Player player;

	@Override
	public void start(GameData gameData) {
		// create a new player into the game
		createPlayer();
		gameData.getGameEntities().addEntity(this.player);
	}

	@Override
	public void stop(GameData gameData) {
		// Remove the player from the game
		gameData.getGameEntities().removeEntity(this.player);
	}


	/**
	 * Creates a new player entity and sets its speed, position, and life.
	 */
	public void createPlayer() {
		this.player = new Player();

		List<Path> paths = this.loadTextures();

		MovingPart movingPart = new MovingPart(
			this.player.getSpeed(),
			this.player.getFrameRate(),
			(gameData, entity) -> null  // THIS LINE IS STILL MISSING, WAITING FOR KEYBOARDCOMPONENT TO WORK.
		);

		// Up uses same sprite but flips it.
		List<SpriteData> up = List.of(
			new SpriteData(paths.get(2), true, false),
			new SpriteData(paths.get(2), false, false)
		);
		// right, uses two different sprites.
		List<SpriteData> right = List.of(
			new SpriteData(paths.get(3), false, false),
			new SpriteData(paths.get(4), false, false)
		);
		// left uses sprits for going right, but flips them
		List<SpriteData> left = List.of(
			new SpriteData(paths.get(3), true, false),
			new SpriteData(paths.get(4), true, false)
		);
		// down uses two different sprites.
		List<SpriteData> down = List.of(
			new SpriteData(paths.get(0), false, false),
			new SpriteData(paths.get(1), false, false)
		);

		movingPart.setSprites(Direction.UP, up);
		movingPart.setSprites(Direction.RIGHT, right);
		movingPart.setSprites(Direction.LEFT, left);
		movingPart.setSprites(Direction.DOWN, down);

		//set the speed of the player
		this.player.addEntityPart(movingPart);

		//set the position of the player
		this.player.addEntityPart(
			new PositionPart(
				this.player.getStartPosition(),
				this.player.getSize(),
				this.player.getDirection()
			)
		);

		//set the life of player
		this.player.addEntityPart(
			new LifePart(this.player.getHearts())
		);


		this.player.addEntityPart(
			new SpritePart(
				new SpriteData(paths.get(0), false, false)
			)
		);
	}

	/**
	 * Loads all player textures into a list of paths.
	 *
	 * @return A list of paths to the player textures.
	 */
	private List<Path> loadTextures() {
		//load all player textures into a Path array
		List<Path> entityTexturesList = new ArrayList<Path>();

		//save each path to the png files into an array of paths.
		for (int i = 1; i <= 5; i++) {
			Path file = Paths.get("Player/src/main/resources/Zelda" + i + ".png");
			entityTexturesList.add(file);
		}
		return entityTexturesList;
	}
}
