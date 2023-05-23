package dk.sdu.sesem4.octorok.common;

import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.data.process.Priority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OctorokProcessorTest {

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testProcess() {
		// Mocked objects
		GameData gameData = mock(GameData.class);
		Priority priority = mock(Priority.class);
		GameEntities gameEntities = mock(GameEntities.class);
		Entity entity = mock(Entity.class);
		PositionPart positionPart = mock(PositionPart.class);
		MovingPart movingPart = mock(MovingPart.class);
		LifePart lifePart = mock(LifePart.class);
		SpritePart spritePart = mock(SpritePart.class);

		List<Entity> entities = new LinkedList<>();
		entities.add(entity);

		// When
		when(gameData.getGameEntities()).thenReturn(gameEntities);
		when(gameEntities.getEntities(any())).thenReturn(entities);
		when(entity.getEntityPart(PositionPart.class)).thenReturn(positionPart);
		when(entity.getEntityPart(MovingPart.class)).thenReturn(movingPart);
		when(entity.getEntityPart(LifePart.class)).thenReturn(lifePart);
		when(entity.getEntityPart(SpritePart.class)).thenReturn(spritePart);

		// Process Octorok
		OctorokProcessor octorokProcessor = new OctorokProcessor();
		octorokProcessor.process(gameData, priority);

		// Verify
		verify(positionPart).process(gameData, entity);
		verify(movingPart).process(gameData, entity);
		verify(lifePart).process(gameData, entity);
		verify(spritePart).process(gameData, entity);
	}
}