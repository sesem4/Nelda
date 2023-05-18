package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.*;

public class MapPluginTest {
	MapPlugin mapPlugin;
	
	@Before
	public void setup() {
		mapPlugin = new MapPlugin();
	}
	
	
	@Test
	public void testStart() {
		GameData gameData = new GameData();
		gameData.setGameWorld(new GameWorld(null, new Vector2(1, 1)));
		
		Path beforePath = gameData.getGameWorld().getMap();
		float beforeMapSizeX = gameData.getGameWorld().getMapSize().getX();
		float beforeMapSizeY = gameData.getGameWorld().getMapSize().getX();
		
		mapPlugin.start(gameData);
		
		GameWorld after = gameData.getGameWorld();
		
		assertNotEquals(beforePath, after.getMap());
		assertNotEquals(beforeMapSizeX, after.getMapSize().getX());
		assertNotEquals(beforeMapSizeY, after.getMapSize().getY());
	}
}