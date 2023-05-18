package dk.sdu.sesem4.octorok.common;

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
import dk.sdu.sesem4.octorok.red.RedOctorok;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

abstract public class OctorokPlugin implements PluginServiceSPI {
	private static List<Octorok> octoroks = new LinkedList<>();

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
		octoroks.forEach(octorok -> {
			gameData.getGameEntities().removeEntity(octorok);
		});
	}

	public Entity spawn(GameData gameData, Vector2 coordinate, Class<? extends Octorok> octorokClass) {
		try {
			Octorok octorok = this.createOctorok(coordinate, octorokClass);
			octoroks.add(octorok);
			gameData.getGameEntities().addEntity(octorok);
			return octorok;
		} catch (Exception exception) {
			System.out.println("Could not spawn enemy - Octorok");
			System.out.println(exception.toString());
		}
		return null;
	}

	private Octorok createOctorok(Vector2 coordinate, Class<? extends Octorok> octorokClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Octorok octorok = octorokClass.getDeclaredConstructor().newInstance();

		List<String> textures = this.loadTextures(octorokClass);

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

		// 1 og 2 ned
		// 3 og 4 venstre
		// 2 og 4 er næsen ind.
		// Set the different sprites foreach direction.
		List<SpriteData> up = List.of(
			new SpriteData(textures.get(0), false, true, octorokClass),
			new SpriteData(textures.get(1), false, true, octorokClass)
		);
		List<SpriteData> down = List.of(
			new SpriteData(textures.get(0), false, false, octorokClass),
			new SpriteData(textures.get(1), false, false, octorokClass)
		);
		List<SpriteData> left = List.of(
			new SpriteData(textures.get(2), false, false, octorokClass),
			new SpriteData(textures.get(3), false, false, octorokClass)
		);
		List<SpriteData> right = List.of(
			new SpriteData(textures.get(2), true, false, octorokClass),
			new SpriteData(textures.get(3), true, false, octorokClass)
		);

		movingPart.setSprites(Direction.UP, up);
		movingPart.setSprites(Direction.DOWN, down);
		movingPart.setSprites(Direction.LEFT, left);
		movingPart.setSprites(Direction.RIGHT, right);

		// Add movingPart to Entity
		octorok.addEntityPart(movingPart);

		// set the position of the Octorok
		octorok.addEntityPart(
			new PositionPart(
				coordinate,
				this.defaultSize,
				this.defaultDirection
			)
		);

		// set the life of the Octorok.
		octorok.addEntityPart(
			new LifePart(this.hearts)
		);

		// Set the first sprite which is shown, when the octorok is spawned.
		octorok.addEntityPart(
			new SpritePart(
				new SpriteData(textures.get(0), false, false, octorokClass)
			)
		);

		octorok.addCollisionPart(new KnockbackPart(this.knockbackDuration, this.knockbackSpeed));
		octorok.addCollisionPart(new DamagePart(this.damage));

		return octorok;
	}

	private List<String> loadTextures(Class<? extends Octorok> octorokClass) {
		List<String> textureList = new ArrayList<>();

		String prefix;
		if (octorokClass == RedOctorok.class) {
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
