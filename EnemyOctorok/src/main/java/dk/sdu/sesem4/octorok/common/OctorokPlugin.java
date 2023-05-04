package dk.sdu.sesem4.octorok.common;

import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.data.rendering.SpriteData;
import dk.sdu.sesem4.common.data.resource.Resource;
import dk.sdu.sesem4.common.util.Direction;
import dk.sdu.sesem4.octorok.red.RedOctorok;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

abstract public class OctorokPlugin implements PluginServiceSPI {
	private static List<Octorok> octoroks = new LinkedList<>();

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

	public void spawn(GameData gameData, Vector2 coordinate, Class<? extends Octorok> octorokClass) {
		try {
			Octorok octorok = this.createOctorok(coordinate, octorokClass);
			octoroks.add(octorok);
			gameData.getGameEntities().addEntity(octorok);
		} catch (Exception exception) {
			System.out.println("Could not spawn enemy - Octorok");
			System.out.println(exception.toString());
		}
	}

	private Octorok createOctorok(Vector2 coordinate, Class<? extends Octorok> octorokClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Octorok octorok = octorokClass.getDeclaredConstructor().newInstance();

		List<String> textures = this.loadTextures(octorokClass);

		MovingPart movingPart = new MovingPart(
				octorok.getSpeed(),
				octorok.getFrameRate(),
				null
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
						octorok.getSize(),
						octorok.getDirection()
				)
		);

		// set the life of the Octorok.
		octorok.addEntityPart(
				new LifePart(octorok.getHearts())
		);

		// Set the first sprite which is shown, when the octorok is spawned.
		octorok.addEntityPart(
				new SpritePart(
						new SpriteData(textures.get(0), false, false, octorokClass)
				)
		);

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
}
