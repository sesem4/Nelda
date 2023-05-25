package dk.sdu.sesem4.octorok.red;

import dk.sdu.sesem4.common.data.CollisionParts.DamagePart;
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

class RedOctorokPluginTest {

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

		RedOctorokPlugin redOctorokPlugin = new RedOctorokPlugin();
		Entity redOctorok = redOctorokPlugin.spawn(gameData, mock(Vector2.class), RedOctorok.class);

		verify(gameEntities).addEntity(any(RedOctorok.class));

		LifePart lifePart = redOctorok.getEntityPart(LifePart.class);

		assertEquals(1, lifePart.getLife());

		DamagePart damagePart = redOctorok.getCollisionPart(DamagePart.class);

		assertEquals(1, damagePart.getDamage());
	}
}