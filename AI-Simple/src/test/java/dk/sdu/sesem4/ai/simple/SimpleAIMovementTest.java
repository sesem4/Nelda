package dk.sdu.sesem4.ai.simple;

import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;
import dk.sdu.sesem4.common.util.SPILocator;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SimpleAIMovementTest {

	@Test
	void getType() {
		assertEquals(ControlType.AI, (new SimpleAIMovement()).getType());
	}

	@Test
	void getMovementController() {
		Class<SimpleAiMovementController> classType = SimpleAiMovementController.class;
		try (MockedStatic<SPILocator> dummy = Mockito.mockStatic(SPILocator.class)) {
			// Setup SPI for use in controller constructor
			ArrayList<MapSPI> mapSPIS = new ArrayList<>();
			mapSPIS.add(mock(MapSPI.class));
			dummy.when(() -> SPILocator.locateAll(MapSPI.class)).thenReturn(mapSPIS);

			// Test that the controller returned is of the correct type
			assertEquals(classType, (new SimpleAIMovement()).getMovementController().getClass());
		}
	}
}