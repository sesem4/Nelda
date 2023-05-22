package dk.sdu.sesem4.smartAIMovement;

import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;
import dk.sdu.sesem4.common.util.SPILocator;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SmartAIMovementControllerTest extends TestCase {

	@Test
	public void testGetMovement() {
		try (MockedStatic<SPILocator> dummy = Mockito.mockStatic(SPILocator.class)) {
			ArrayList<MapSPI> mapSPIS = new ArrayList<>();
			MapSPI mapSPI = mock(MapSPI.class);
			mapSPIS.add(mapSPI);
			dummy.when(() -> SPILocator.locateAll(MapSPI.class)).thenReturn(mapSPIS);

			GameData gameData = mock(GameData.class);
			GameEntities gameEntities = mock(GameEntities.class);
			when(gameData.getGameEntities()).thenReturn(gameEntities);
			when(gameEntities.getEntities()).thenReturn(new ArrayList<>());

			boolean[][] fullGrid = new boolean[][]{{true, true, true}, {true, true, true}, {true, true, true}};
			when(mapSPI.getNavGrid(gameData)).thenReturn(fullGrid);
			when(mapSPI.getRandomPassableTile(gameData)).thenReturn(new Vector2(2, 0));

			Entity entity = mock(Entity.class);
			PositionPart positionPart = mock(PositionPart.class);
			when(entity.getEntityPart(PositionPart.class)).thenReturn(positionPart);
			when(positionPart.getPosition()).thenReturn(new Vector2(GameWorld.TILE_SIZE, GameWorld.TILE_SIZE));

			SmartAIMovementController controller = new SmartAIMovementController();
			Direction direction = controller.getMovement(gameData, entity);
			assertNull(direction);

			direction = controller.getMovement(gameData, entity);
			when(direction).thenReturn(Direction.RIGHT);
			assertEquals(Direction.RIGHT, direction);

			when(positionPart.getPosition()).thenReturn(new Vector2(2 * GameWorld.TILE_SIZE, GameWorld.TILE_SIZE));

			direction = controller.getMovement(gameData, entity);
			assertNull(direction);

			direction = controller.getMovement(gameData, entity);
			assertEquals(Direction.DOWN, direction);

			when(positionPart.getPosition()).thenReturn(new Vector2(2 * GameWorld.TILE_SIZE, 0 * GameWorld.TILE_SIZE));

			direction = controller.getMovement(gameData, entity);
			assertNull(direction);
		}
	}
}