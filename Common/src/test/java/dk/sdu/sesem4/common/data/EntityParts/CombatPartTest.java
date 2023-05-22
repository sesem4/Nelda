package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.SPI.CombatControllerSPI;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.util.CombatControllerLocator;
import org.junit.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

public class CombatPartTest {

	@Test
	void process() {
		GameData gameData = mock(GameData.class);
		Entity entity = mock(Entity.class);
		CombatControllerSPI combatControllerSPI = mock(CombatControllerSPI.class);

		try (MockedStatic<CombatControllerLocator> locator = mockStatic(CombatControllerLocator.class)) {
			locator.when(() -> CombatControllerLocator.locateController(any())).thenReturn(locator);

		}
	}
}