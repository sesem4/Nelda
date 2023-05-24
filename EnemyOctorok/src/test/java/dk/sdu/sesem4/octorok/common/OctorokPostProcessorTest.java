package dk.sdu.sesem4.octorok.common;

import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.data.process.Priority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OctorokPostProcessorTest {

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void testPostProcess() {
		// Mocked objects
		GameData gameData = mock(GameData.class);
		Priority priority = mock(Priority.class);
		OctorokPostProcessor octorokPostProcessor = mock(OctorokPostProcessor.class);
		GameEntities gameEntities = mock(GameEntities.class);


		// When
		when(gameData.getGameEntities()).thenReturn(gameEntities);

		// Process Octorok
		octorokPostProcessor.postProcess(gameData, priority);

		// Verify Octorok
		verify(octorokPostProcessor).postProcess(gameData, priority);

	}
}