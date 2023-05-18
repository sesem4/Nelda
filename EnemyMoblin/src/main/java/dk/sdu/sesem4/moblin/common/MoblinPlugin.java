package dk.sdu.sesem4.moblin.common;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.CollisionParts.DamagePart;
import dk.sdu.sesem4.common.data.CollisionParts.KnockbackPart;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.data.rendering.SpriteData;
import dk.sdu.sesem4.common.util.ControllerLocator;
import dk.sdu.sesem4.common.util.Direction;
import dk.sdu.sesem4.moblin.red.RedMoblin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

abstract public class MoblinPlugin implements PluginServiceSPI {
	private static List<Moblin> moblins = new LinkedList<>();

	private final int defaultSpeed = 1;
	private final int defaultFrameRate = 10;

	private final float knockbackDuration = 0.1f;
	private final float knockbackSpeed = 5;

	private int hearts;
	private int damage;

	private final Direction defaultDirection = Direction.UP;
	private final Vector2 defaultSize = new Vector2(16, 16);

	@Override
	public void start(GameData gameData) {
		// Nothing on component start
	}

	@Override
	public void stop(GameData gameData) {
		moblins.forEach(moblin -> {
			gameData.getGameEntities().removeEntity(moblin);
		});
	}

	public Entity spawn(GameData gameData, Vector2 coordinate, Class<? extends Moblin> moblinClass) {
		try {
			Moblin moblin = this.createMoblin(coordinate, moblinClass);
			moblins.add(moblin);
			gameData.getGameEntities().addEntity(moblin);
			return moblin;
		} catch (Exception exception) {
			System.out.println("Could not spawn enemy - Moblin");
			System.out.println(exception.toString());
		}

		return null;
	}

	private Moblin createMoblin(Vector2 coordinate, Class<? extends Moblin> moblinClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Moblin moblin = moblinClass.getDeclaredConstructor().newInstance();

		List<String> textures = this.loadTextures(moblinClass);

		ControlSPI constrolSPI = ControllerLocator.locateController(ControlType.ROUGH_AI);
		MovementControllerSPI controller;

		// Check if a controller was found. If not set controller to null.
		if (constrolSPI == null) {
			controller = null;
		} else {
			controller = constrolSPI.getMovementController();
		}

		MovingPart movingPart = new MovingPart(
			this.defaultSpeed,
			this.defaultFrameRate,
			controller
		);

		// 0 og 1 venstre
		// 2 ned
		// 3 op
		// Set the different sprites foreach direction.
		List<SpriteData> up = List.of(
			new SpriteData(textures.get(3), false, false, moblinClass),
			new SpriteData(textures.get(3), true, false, moblinClass)
		);
		List<SpriteData> down = List.of(
			new SpriteData(textures.get(2), false, false, moblinClass),
			new SpriteData(textures.get(2), true, false, moblinClass)
		);
		List<SpriteData> left = List.of(
			new SpriteData(textures.get(0), true, false, moblinClass),
			new SpriteData(textures.get(1), true, false, moblinClass)
		);
		List<SpriteData> right = List.of(
			new SpriteData(textures.get(0), false, false, moblinClass),
			new SpriteData(textures.get(1), false, false, moblinClass)
		);

		movingPart.setSprites(Direction.UP, up);
		movingPart.setSprites(Direction.DOWN, down);
		movingPart.setSprites(Direction.LEFT, left);
		movingPart.setSprites(Direction.RIGHT, right);

		// Add movingPart to Entity
		moblin.addEntityPart(movingPart);

		// set the position of the Moblin
		moblin.addEntityPart(
			new PositionPart(
				coordinate,
				this.defaultSize,
				this.defaultDirection
			)
		);

		// set the life of the Moblin.
		moblin.addEntityPart(
			new LifePart(this.hearts)
		);

		// Set the first sprite which is shown, when the moblin is spawned.
		moblin.addEntityPart(
			new SpritePart(
				new SpriteData(textures.get(0), false, false, moblinClass)
			)
		);

		moblin.addCollisionPart(new KnockbackPart(this.knockbackDuration, this.knockbackSpeed));
		moblin.addCollisionPart(new DamagePart(this.damage));

		return moblin;
	}

	private List<String> loadTextures(Class<? extends Moblin> moblinClass) {
		List<String> textureList = new ArrayList<>();

		String prefix;
		if (moblinClass == RedMoblin.class) {
			prefix = "red";
		} else {
			prefix = "blue";
		}

		for (int i = 0; i < 4; i++) {
			String path = "/" + prefix + i + ".png";
			textureList.add(path);
		}

		return textureList;
	}

	public void setHearts(int hearts) {
		this.hearts = hearts;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
}
