package dk.sdu.sesem4.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.rendering.SpriteData;
import dk.sdu.sesem4.common.util.Direction;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PlayerPlugin implements PluginServiceSPI {

	private Player player;

	@Override
	public void start(GameData gameData) {
		// create a new player into the game
		this.player = createPlayer();
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
	private Player createPlayer() {
		Player player = new Player();

		List<Path> paths = this.loadTextures();

		MovingPart movingPart = new MovingPart(
				player.getSpeed(),
				player.getFrameRate(),
				new MovementControllerSPI() {
					@Override
					public Direction getMovement(GameData gameData, Entity entity) {
						if(Gdx.input.isKeyPressed(Input.Keys.W)) return Direction.UP;
						if(Gdx.input.isKeyPressed(Input.Keys.S)) return Direction.DOWN;
						if(Gdx.input.isKeyPressed(Input.Keys.A)) return Direction.LEFT;
						if(Gdx.input.isKeyPressed(Input.Keys.D)) return Direction.RIGHT;
						return null;
					}
				}
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
		player.addEntityPart(movingPart);

		//set the position of the player
		player.addEntityPart(
			new PositionPart(
				player.getStartPosition(),
				player.getSize(),
				player.getDirection()
			)
		);

		//set the life of player
		player.addEntityPart(
			new LifePart(player.getHearts())
		);


		player.addEntityPart(
			new SpritePart(
				new SpriteData(paths.get(0), false, false)
			)
		);

		return player;
	}

	/**
	 * Loads all player textures into a list of paths.
	 *
	 * @return A list of paths to the player textures.
	 */
	protected List<Path> loadTextures() {
		//load all player textures into a Path array
		List<Path> entityTexturesList = new ArrayList<>();

		// Generate folder for the textures
		String folder = "Player" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;

		//save each folder to the png files into an array of paths.
		for (int i = 1; i <= 5; i++) {
			Path file = Paths.get(folder + "Zelda" + i + ".png");
			entityTexturesList.add(file);
		}
		return entityTexturesList;
	}
}
