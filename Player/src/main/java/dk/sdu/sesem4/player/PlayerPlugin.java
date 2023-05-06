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

		List<String> paths = this.loadTextures();

		MovingPart movingPart = new MovingPart(
			player.getSpeed(),
			player.getFrameRate(),
			new MovementControllerSPI() {
				@Override
				public Direction getMovement(GameData gameData, Entity entity) {
					if (Gdx.input.isKeyPressed(Input.Keys.W)) return Direction.UP;
					if (Gdx.input.isKeyPressed(Input.Keys.S)) return Direction.DOWN;
					if (Gdx.input.isKeyPressed(Input.Keys.A)) return Direction.LEFT;
					if (Gdx.input.isKeyPressed(Input.Keys.D)) return Direction.RIGHT;
					return null;
				}
			}
		);

		// Up uses same sprite but flips it.
		List<SpriteData> up = List.of(
			new SpriteData(paths.get(2), true, false, Player.class),
			new SpriteData(paths.get(2), false, false, Player.class)
		);
		// right, uses two different sprites.
		List<SpriteData> right = List.of(
			new SpriteData(paths.get(3), false, false, Player.class),
			new SpriteData(paths.get(4), false, false, Player.class)
		);
		// left uses sprits for going right, but flips them
		List<SpriteData> left = List.of(
			new SpriteData(paths.get(3), true, false, Player.class),
			new SpriteData(paths.get(4), true, false, Player.class)
		);
		// down uses two different sprites.
		List<SpriteData> down = List.of(
			new SpriteData(paths.get(0), false, false, Player.class),
			new SpriteData(paths.get(1), false, false, Player.class)
		);

		movingPart.setSprites(Direction.UP, up);
		movingPart.setSprites(Direction.RIGHT, right);
		movingPart.setSprites(Direction.LEFT, left);
		movingPart.setSprites(Direction.DOWN, down);

		// add MovingPart to the player
		player.addEntityPart(movingPart);

		// add PositionPart to the player
		player.addEntityPart(
			new PositionPart(
				player.getStartPosition(),
				player.getSize(),
				player.getDirection()
			)
		);

		// add LifePart to the player
		player.addEntityPart(
			new LifePart(player.getHearts())
		);

		// add SpritePart to the player
		player.addEntityPart(
			new SpritePart(
				new SpriteData(paths.get(0), false, false, Player.class)
			)
		);

		return player;
	}

	/**
	 * Loads all player textures into a list of paths.
	 *
	 * @return A list of paths to the player textures.
	 */
	protected List<String> loadTextures() {
		//load all player textures into a Path array
		List<String> entityTexturesList = new ArrayList<>();

		//save each folder to the png files into an array of paths.
		for (int i = 1; i <= 5; i++) {
			String path = "/Zelda" + i + ".png";
			entityTexturesList.add(path);
		}
		return entityTexturesList;
	}
}
