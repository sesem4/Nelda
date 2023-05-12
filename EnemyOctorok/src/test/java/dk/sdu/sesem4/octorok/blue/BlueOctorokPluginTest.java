package dk.sdu.sesem4.octorok.blue;

import dk.sdu.sesem4.common.data.CollisionParts.CollisionPart;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.data.math.Vector2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlueOctorokPluginTest {

	@BeforeEach
	void setUp() {

	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void spawn() {
		GameData gameData = mock(GameData.class);
		GameEntities gameEntities = mock(GameEntities.class);

		when(gameData.getGameEntities()).thenReturn(gameEntities);


		BlueOctorokPlugin blueOctorokPlugin = new BlueOctorokPlugin();
		Entity blueOctorok = blueOctorokPlugin.spawn(gameData, mock(Vector2.class), BlueOctorok.class);


		verify(gameEntities).addEntity(any(BlueOctorok.class));

		LifePart lifePart = blueOctorok.getEntityPart(LifePart.class);

		assertEquals(2, lifePart.getLife());

		CollisionPart collisionPart = blueOctorok.getEntityPart(CollisionPart.class);

		//assertEquals(1, collisionPart.

	}
}