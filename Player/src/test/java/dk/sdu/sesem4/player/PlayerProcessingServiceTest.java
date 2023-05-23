package dk.sdu.sesem4.player;

import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.*;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.data.gamedata.GameWorld;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.common.event.EventManager;
import dk.sdu.sesem4.common.event.events.map.MapTransitionDoneEventType;
import dk.sdu.sesem4.common.event.events.map.MapTransitionEvent;
import dk.sdu.sesem4.common.event.events.map.MapTransitionEventType;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerProcessingServiceTest {

	@Test
	void process() {
		// Setup mock elements
		Priority priority = mock(Priority.class);
		GameData gameData = mock(GameData.class);
		GameEntities gameEntities = mock(GameEntities.class);
		GameWorld gameWorld = mock(GameWorld.class);
		Vector2 mapSize = mock(Vector2.class);
		Player player = mock(Player.class);
		PositionPart positionPart = mock(PositionPart.class);
		Vector2 size = mock(Vector2.class);
		Vector2 position = mock(Vector2.class);
		MovingPart movingPart = mock(MovingPart.class);
		LifePart lifePart = mock(LifePart.class);
		CombatPart combatPart = mock(CombatPart.class);
		SpritePart spritePart = mock(SpritePart.class);

		// Setup entities list
		ArrayList<Entity> entities = new ArrayList<>();
		entities.add(player);

		// Setup mock return data
		// Game entities
		when(gameData.getGameEntities()).thenReturn(gameEntities);
		when(gameEntities.getEntities(any())).thenReturn(entities);

		// Player entity parts
		when(player.getEntityPart(PositionPart.class)).thenReturn(positionPart);
		when(player.getEntityPart(MovingPart.class)).thenReturn(movingPart);
		when(player.getEntityPart(LifePart.class)).thenReturn(lifePart);
		when(player.getEntityPart(CombatPart.class)).thenReturn(combatPart);
		when(player.getEntityPart(SpritePart.class)).thenReturn(spritePart);

		// World data
		when(gameData.getGameWorld()).thenReturn(gameWorld);
		when(gameWorld.getMapSize()).thenReturn(mapSize);
		when(mapSize.getX()).thenReturn(10f * GameWorld.TILE_SIZE);
		when(mapSize.getY()).thenReturn(10f * GameWorld.TILE_SIZE);

		// Position and size
		when(positionPart.getSize()).thenReturn(size);
		when(size.getX()).thenReturn(1f * GameWorld.TILE_SIZE);
		when(size.getY()).thenReturn(1f * GameWorld.TILE_SIZE);
		when(positionPart.getPosition()).thenReturn(position);
		when(position.getX()).thenReturn(5f * GameWorld.TILE_SIZE);
		when(position.getY()).thenReturn(5f * GameWorld.TILE_SIZE);

		// Run method to be tested
		ProcessingServiceSPI playerProcessingService = new PlayerProcessingService();
		playerProcessingService.process(gameData, priority);

		// Verify that parts has been processed
		verify(positionPart).process(gameData, player);
		verify(movingPart).process(gameData, player);
		verify(lifePart).process(gameData, player);
		verify(combatPart).process(gameData, player);
		verify(spritePart).process(gameData, player);
	}

	@Test
	public void mapTransition() {
		// Setup mock elements
		Priority priority = mock(Priority.class);
		GameData gameData = mock(GameData.class);
		GameEntities gameEntities = mock(GameEntities.class);
		GameWorld gameWorld = mock(GameWorld.class);
		Vector2 mapSize = mock(Vector2.class);
		Player player = mock(Player.class);
		PositionPart positionPart = mock(PositionPart.class);
		Vector2 size = mock(Vector2.class);
		Vector2 position = mock(Vector2.class);
		MovingPart movingPart = mock(MovingPart.class);
		LifePart lifePart = mock(LifePart.class);
		CombatPart combatPart = mock(CombatPart.class);
		SpritePart spritePart = mock(SpritePart.class);
		EventManager eventManager = mock(EventManager.class);

		// Setup entities list
		ArrayList<Entity> entities = new ArrayList<>();
		entities.add(player);

		// Setup mock return data
		// Game entities
		when(gameData.getGameEntities()).thenReturn(gameEntities);
		when(gameEntities.getEntities(any())).thenReturn(entities);

		// Player entity parts
		when(player.getEntityPart(PositionPart.class)).thenReturn(positionPart);
		when(player.getEntityPart(MovingPart.class)).thenReturn(movingPart);
		when(player.getEntityPart(LifePart.class)).thenReturn(lifePart);
		when(player.getEntityPart(CombatPart.class)).thenReturn(combatPart);
		when(player.getEntityPart(SpritePart.class)).thenReturn(spritePart);

		// World data
		when(gameData.getGameWorld()).thenReturn(gameWorld);
		when(gameWorld.getMapSize()).thenReturn(mapSize);
		when(mapSize.getX()).thenReturn(10f * GameWorld.TILE_SIZE);
		when(mapSize.getY()).thenReturn(10f * GameWorld.TILE_SIZE);

		// Position and size
		when(positionPart.getSize()).thenReturn(size);
		when(size.getX()).thenReturn(1f * GameWorld.TILE_SIZE);
		when(size.getY()).thenReturn(1f * GameWorld.TILE_SIZE);
		when(positionPart.getPosition()).thenReturn(position);
		when(position.getX()).thenReturn(11f * GameWorld.TILE_SIZE);
		when(position.getY()).thenReturn(11f * GameWorld.TILE_SIZE);

		// Run method to be tested
		try (MockedStatic<EventManager> dummy = Mockito.mockStatic(EventManager.class)) {
			dummy.when(() -> EventManager.getInstance()).thenReturn(eventManager);

			ProcessingServiceSPI playerProcessingService = new PlayerProcessingService();
			playerProcessingService.process(gameData, priority);

			// Verify that event has been dispatched
			verify(eventManager).notify(eq(MapTransitionEventType.class), any(MapTransitionEvent.class));
		}
	}
}