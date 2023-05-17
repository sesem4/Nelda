package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import org.junit.Before;
import org.junit.Test;

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
		GameWorld before = gameData.getGameWorld();
		
		mapPlugin.start(gameData);
		
		GameWorld after = gameData.getGameWorld();
		
		assertNotEquals(before.getMap(), after.getMap());
		assertNotEquals(before.getMapSize(), after.getMapSize());
	}
}