package dk.sdu.sesem4.smartAIMovement;

import dk.sdu.sesem4.common.SPI.MapSPI;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;
import dk.sdu.sesem4.common.util.SPILocator;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SmartAIMovementControllerTest {

	@Test
	public void testGetMovement() {
		try (MockedStatic<SPILocator> dummy = Mockito.mockStatic(SPILocator.class)) {
			// Setup SPI
			ArrayList<MapSPI> mapSPIs = new ArrayList<>();
			MapSPI mapSPI = mock(MapSPI.class);
			when(mapSPI.isTilePassable(new Vector2(2, 1))).thenReturn(true);
			mapSPIs.add(mapSPI);
			dummy.when(() -> SPILocator.locateAll(MapSPI.class)).thenReturn(mapSPIs);
			
			GameWorld gameWorld = mock(GameWorld.class);
			when(gameWorld.getMapSize()).thenReturn(new Vector2(3 * GameWorld.TILE_SIZE, 3 * GameWorld.TILE_SIZE));
			
			PositionPart playerPositionPart = mock(PositionPart.class);
			when(playerPositionPart.getPosition()).thenReturn(new Vector2(2 * GameWorld.TILE_SIZE, 1 * GameWorld.TILE_SIZE));
			
			Entity playerEntity = mock(Entity.class);
			when(playerEntity.getEntityType()).thenReturn(EntityType.Player);
			when(playerEntity.getEntityPart(PositionPart.class)).thenReturn(playerPositionPart);
			
			List<Entity> entityList = new ArrayList<>();
			entityList.add(playerEntity);
			GameEntities gameEntities = mock(GameEntities.class);
			when(gameEntities.getEntities()).thenReturn(entityList);
			
			// Setup gameData
			GameData gameData = mock(GameData.class);
			when(gameData.getGameEntities()).thenReturn(gameEntities);
			when(gameData.getGameWorld()).thenReturn(gameWorld);
			
			// Setup NavGrid and random tile
			boolean[][] fullGrid = new boolean[][]{{true, true, true}, {true, true, true}, {true, true, true}};
			when(mapSPI.getNavGrid(gameData)).thenReturn(fullGrid);
			
			// Setup entity
			Entity enemyEntity = mock(Entity.class);
			PositionPart enemyPositionPart = mock(PositionPart.class);
			when(enemyEntity.getEntityPart(PositionPart.class)).thenReturn(enemyPositionPart);
			when(enemyPositionPart.getPosition()).thenReturn(new Vector2(1 * GameWorld.TILE_SIZE, 1 * GameWorld.TILE_SIZE));
			
			// Get and run controller
			SmartAIMovementController controller = new SmartAIMovementController();
			
			
			// Assert that next movement direction is then for next tile
			Direction direction = controller.getMovement(gameData, enemyEntity);
			assertEquals(Direction.RIGHT, direction);
		}
	}
}