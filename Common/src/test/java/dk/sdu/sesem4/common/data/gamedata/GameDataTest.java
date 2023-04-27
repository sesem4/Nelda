package dk.sdu.sesem4.common.data.gamedata;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameDataTest {

	@Test
	public void testDeltaTime() {
		GameData gamedata = new GameData();

		gamedata.setDeltaTime(0);
		assertEquals(0, gamedata.getDeltaTime(), 0.001);


		gamedata.setDeltaTime(10);
		assertEquals(10, gamedata.getDeltaTime(), 0.001);
	}

	@Test
	public void testElapsedTime() {
		GameData gamedata = new GameData();

		gamedata.setDeltaTime(10);
		assertEquals(10, gamedata.getDeltaTime(), 0.001);

		assertEquals(0, gamedata.getElapsedTime(), 0.001);

		gamedata.processElapsedTime();

		assertEquals(10, gamedata.getElapsedTime(), 0.001);

		gamedata.setDeltaTime(0.5f);

		gamedata.processElapsedTime();

		assertEquals(10.5, gamedata.getElapsedTime(), 0.001);
	}
}