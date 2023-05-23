package dk.sdu.sesem4.common.util;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ControllerLocatorTest {

    @Test
    public void locateController() {
        try (MockedStatic<SPILocator> spiLocator = mockStatic(SPILocator.class)) {
            List<ControlSPI> controlSPIS = new LinkedList<>();
            ControlSPI controlSPI1 = mock(ControlSPI.class);
            ControlSPI controlSPI2 = mock(ControlSPI.class);
            ControlSPI controlSPI3 = mock(ControlSPI.class);

            controlSPIS.add(controlSPI1);
            controlSPIS.add(controlSPI2);
            controlSPIS.add(controlSPI3);

            spiLocator.when(() -> SPILocator.locateAll(ControlSPI.class)).thenReturn(controlSPIS);
            when(controlSPI1.getType()).thenReturn(ControlType.DUMB_AI);
            when(controlSPI2.getType()).thenReturn(ControlType.KEYBOARD);
            when(controlSPI3.getType()).thenReturn(ControlType.SMART_AI);

            ControlSPI locatedControlSPI = ControllerLocator.locateController(ControlType.KEYBOARD);

            assertEquals(controlSPI2, locatedControlSPI);
            verify(controlSPI1).getType();
            verify(controlSPI2).getType();
            verify(controlSPI3, never()).getType();
        }
    }
}