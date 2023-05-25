package dk.sdu.sesem4.ai.smart;

import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;
import dk.sdu.sesem4.common.util.SPILocator;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class SmartAIMovementTest extends TestCase {

	@Test
	public void testGetType() {
		assertEquals(ControlType.AI, new SmartAIMovement().getType());
	}

	@Test
	public void testGetMovementController() {
		Class<SmartAIMovementController> classType = SmartAIMovementController.class;
		try (MockedStatic<SPILocator> dummy = Mockito.mockStatic(SPILocator.class)){
			ArrayList<MapSPI> mapSPIS = new ArrayList<>();
			mapSPIS.add(mock(MapSPI.class));
			dummy.when(() -> SPILocator.locateAll(MapSPI.class)).thenReturn(mapSPIS);

			assertEquals(classType, new SmartAIMovement().getMovementController().getClass());
		}
	}
}