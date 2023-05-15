package dk.sdu.sesem4.smartAIMovement;

import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.util.SPILocator;
import junit.framework.TestCase;
import org.mockito.Mock;

public class SmartAIMovementControllerTest extends SmartAIMovementController {

	@Mock
	private GameData gameData;

	@Mock
	private Entity entity;

	@Mock
	private PositionPart positionPart;

	@Mock
	private MapSPI mapSPI;

	@Mock
	private SPILocator locator;

	public SmartAIMovementControllerTest() {
		super();
		// Get the current region
		MapSPI mapSPI = SPILocator.locateAll(MapSPI.class).get(0);

		// Get the grid from the mapSPI
		SPILocator locator = SPILocator.locateAll(SPILocator.class).get(0);
	}

	public void testGetMovement() {
	}
}