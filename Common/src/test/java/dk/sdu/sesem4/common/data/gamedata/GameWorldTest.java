package dk.sdu.sesem4.common.data.gamedata;

import dk.sdu.sesem4.common.data.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class GameWorldTest {
	@Test
	public void setAndGetMap() {
		GameWorld gameWorld = new GameWorld();

		Path path = Paths.get("");
		gameWorld.setMap(path);
		assertEquals(path, gameWorld.getMap());
	}
	@Test
	public void setAndGetMapSize() {
		GameWorld gameWorld = new GameWorld();
		assertEquals(GameWorld.TILE_SIZE * 16, gameWorld.getMapSize().getX(), 0.001);
		assertEquals(GameWorld.TILE_SIZE * 11, gameWorld.getMapSize().getY(), 0.001);

		gameWorld.setMapSize(1, 2);
		assertEquals(1, gameWorld.getMapSize().getX(), 0.001);
		assertEquals(2, gameWorld.getMapSize().getY(), 0.001);
	}
}