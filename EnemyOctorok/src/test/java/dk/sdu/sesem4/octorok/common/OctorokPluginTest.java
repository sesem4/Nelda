package dk.sdu.sesem4.octorok.common;

import dk.sdu.sesem4.common.SPI.ControlSPI;
import dk.sdu.sesem4.common.data.CollisionParts.DamagePart;
import dk.sdu.sesem4.common.data.CollisionParts.KnockbackPart;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.controllerParts.ControlType;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.ControllerLocator;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OctorokPluginTest {
    @Test
    void spawn() {
        try (MockedStatic<ControllerLocator> controllerLocator = mockStatic(ControllerLocator.class)) {
            GameData gameData = mock(GameData.class);
            GameEntities gameEntities = mock(GameEntities.class);
            ControlSPI controlSPI = mock(ControlSPI.class);
            Vector2 coordinate = mock(Vector2.class);

            controllerLocator.when(() -> ControllerLocator.locateController(any())).thenReturn(controlSPI);
            when(gameData.getGameEntities()).thenReturn(gameEntities);

            OctorokPlugin octorokPlugin = new OctorokPlugin(){};
            Entity entity = octorokPlugin.spawn(gameData, coordinate, Octorok.class);

            assertNotNull(entity);
            verify(gameEntities).addEntity(entity);
            assertNotNull(entity.getEntityPart(LifePart.class));
            assertNotNull(entity.getEntityPart(MovingPart.class));
            assertNotNull(entity.getEntityPart(PositionPart.class));
            assertNotNull(entity.getEntityPart(SpritePart.class));

            assertNotNull(entity.getCollisionPart(KnockbackPart.class));
            assertNotNull(entity.getCollisionPart(DamagePart.class));

            controllerLocator.verify(() -> ControllerLocator.locateController(ControlType.AI));
        }
    }
}