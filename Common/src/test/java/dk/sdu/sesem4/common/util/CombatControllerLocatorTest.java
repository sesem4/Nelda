package dk.sdu.sesem4.common.util;

import dk.sdu.sesem4.common.SPI.CombatControllerSPI;
import dk.sdu.sesem4.common.SPI.CombatSPI;
import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.data.combat.WeaponType;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CombatControllerLocatorTest {

    @Test
    public void locateController() {
        try (MockedStatic<SPILocator> spiLocator = mockStatic(SPILocator.class)) {
            List<CombatSPI> combatSPIS = new LinkedList<>();
            CombatSPI combatSPI1 = mock(CombatSPI.class);
            CombatSPI combatSPI2 = mock(CombatSPI.class);
            CombatSPI combatSPI3 = mock(CombatSPI.class);
            CombatControllerSPI combatControllerSPI = mock(CombatControllerSPI.class);

            combatSPIS.add(combatSPI1);
            combatSPIS.add(combatSPI2);
            combatSPIS.add(combatSPI3);

            spiLocator.when(() -> SPILocator.locateAll(CombatSPI.class)).thenReturn(combatSPIS);
            when(combatSPI1.getType()).thenReturn(WeaponType.ROCK);
            when(combatSPI2.getType()).thenReturn(WeaponType.SWORD);
            when(combatSPI3.getType()).thenReturn(WeaponType.ARROW);
            when(combatSPI2.getCombatController()).thenReturn(combatControllerSPI);

            CombatControllerSPI locatedCombatControllerSPI = CombatControllerLocator.locateController(WeaponType.SWORD);

            assertEquals(combatControllerSPI, locatedCombatControllerSPI);
            verify(combatSPI1).getType();
            verify(combatSPI1, never()).getCombatController();
            verify(combatSPI2).getType();
            verify(combatSPI2).getCombatController();
            verify(combatSPI3, never()).getType();
            verify(combatSPI3, never()).getCombatController();
        }
    }
}