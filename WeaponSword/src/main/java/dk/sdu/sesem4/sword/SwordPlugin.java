package dk.sdu.sesem4.sword;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.CollisionParts.DamagePart;
import dk.sdu.sesem4.common.data.EntityParts.CombatPart;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.combat.WeaponType;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.data.rendering.SpriteData;
import dk.sdu.sesem4.common.util.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * The SwordPlugin class is a plugin for the game. It creates a sword and adds it to the game.
 */
public class SwordPlugin implements PluginServiceSPI {
	/**
	 * Default life time of sword
	 */
	private final float defaultLifetime = 0.25f;
	/**
	 * Default damage of sword
	 */
	private final int defaultDamage = 2;
	/**
	 * Default size of sword
	 */
	private final Vector2 defaultSize = new Vector2(16, 16);


	@Override
	public void start(GameData gameData) {
	}

	@Override
	public void stop(GameData gameData) {
		// Loop all sword entities and remove them.
		for (Entity entity : gameData.getGameEntities().getEntities(Sword.class)) {
			gameData.getGameEntities().removeEntity(entity);
		}
	}

	public Sword spawn(GameData gameData, Entity owner) {
		Sword sword = this.createSword(owner);
		gameData.getGameEntities().addEntity(sword);
		return sword;
	}

	public Sword createSword(Entity owner) {
		Sword sword = new Sword(defaultLifetime);

		List<String> paths = this.loadTextures();

		// Get PositionPart for owner
		PositionPart ownerPositionPart = owner.getEntityPart(PositionPart.class);

		// Create position for sword, set to null to
		Vector2 position;

		// Set temporary values for calculations
		float ownerWidth = ownerPositionPart.getSize().getX();
		float ownerHeight = ownerPositionPart.getSize().getY();
		float ownerX = ownerPositionPart.getPosition().getX();
		float ownerY = ownerPositionPart.getPosition().getY();

		// Setup sprite data for visualization
		SpriteData spriteData;

		// Get position and sprite based on owner direction
		switch (ownerPositionPart.getDirection()) {
			default:
			case UP:
				position = new Vector2(ownerX, ownerY + ownerHeight);
				spriteData = new SpriteData(paths.get(1), false, false, Sword.class);
				break;
			case DOWN:
				position = new Vector2(ownerX, ownerY - ownerHeight);
				spriteData = new SpriteData(paths.get(1), false, true, Sword.class);
				break;
			case LEFT:
				position = new Vector2(ownerX - ownerWidth, ownerY);
				spriteData = new SpriteData(paths.get(0), true, false, Sword.class);
				break;
			case RIGHT:
				position = new Vector2(ownerX + ownerWidth, ownerY);
				spriteData = new SpriteData(paths.get(0), false, false, Sword.class);
				break;
		}

		// Add sprite part to sword, with information from owner.
		sword.addEntityPart(
			new SpritePart(spriteData)
		);


		// Add PositionPart to sword, with informations from owner.
		sword.addEntityPart(
			new PositionPart(
				position,
				this.defaultSize,
				ownerPositionPart.getDirection()
			)
		);

		sword.addCollisionPart(
			new DamagePart(this.defaultDamage)
		);

		sword.addEntityPart(
			new MovingPart(
				1,
				1,
				null
			)
		);

		return sword;
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
		for (int i = 1; i <= 2; i++) {
			String path = "/sword" + i + ".png";
			entityTexturesList.add(path);
		}
		return entityTexturesList;
	}
}
