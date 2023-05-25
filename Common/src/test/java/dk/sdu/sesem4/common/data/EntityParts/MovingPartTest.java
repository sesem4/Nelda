package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.CollisionParts.Knockback;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.entity.classes.TestEntity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.data.rendering.SpriteData;
import dk.sdu.sesem4.common.util.Direction;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MovingPartTest {
	private MovingPart movingPart;
	private MovementControllerSPI movementController;
	private Knockback knockback;
	private List<SpriteData> spriteDataList;

	@Before
	public void setUp() throws Exception {
		movingPart = new MovingPart(10,1,movementController);
		knockback = new Knockback(Direction.DOWN,5,10);
		spriteDataList = new ArrayList<>();

	}

	public void tearDown() throws Exception {
	}

	@Test
	public void testGetMoveSpeed() {
		assertEquals(10f,movingPart.getMoveSpeed(), 0.001);
	}

	@Test
	public void testSetMoveSpeed() {
		assertEquals(10f,movingPart.getMoveSpeed(), 0.001);
		movingPart.setMoveSpeed(20f);
		assertEquals(20f,movingPart.getMoveSpeed(), 0.001);
	}

	@Test
	public void testSetKnockback() {
		movingPart.setKnockback(knockback);
		assertEquals(new Knockback(Direction.DOWN,5,10), knockback);
	}

	@Ignore
	public void testGetKnockback() {
		assertEquals(knockback,movingPart.getKnockback());
	}

	@Ignore
	public void testIsNotKnockedBack() {
		assertFalse(movingPart.isKnockedBack());
	}

	@Test
	public void testSetSprites() {
		movingPart.setSprites(Direction.UP,spriteDataList);
		Direction direction = Direction.UP;
		List<SpriteData> spriteData = spriteDataList;
		assertEquals(spriteData,movingPart.getSprites(direction));
	}

	@Test
	public void testAddSprite() {
		assertEquals(0,spriteDataList.size());
		spriteDataList.add(new SpriteData("file-path"));
		assertEquals(1,spriteDataList.size());
	}

	@Test
	public void testGetSprites() {
		movingPart.setSprites(Direction.UP,spriteDataList);
		assertArrayEquals(spriteDataList.toArray(),movingPart.getSprites(Direction.UP).toArray());
	}

	@Test
	public void testSetSprite() {
	}
	
	@Test
	public void testUndoMovementBeforeProcessDoesNothing() {
		Vector2 startPosition = new Vector2(10, 10);
		
		Entity entity = new TestEntity(EntityType.Player);
		PositionPart positionPart = new PositionPart(startPosition, new Vector2(16, 16), Direction.UP);
		entity.addEntityPart(positionPart);
		
		movingPart.undoMovement(entity);
		
		Vector2 endPosition = positionPart.getPosition();
		
		assertEquals(startPosition, endPosition);
	}
	
	@Test
	public void testDirectionWhenNoMovementControllerShouldBeNull() {
		Vector2 startPosition = new Vector2(10, 10);
		
		Entity entity = new TestEntity(EntityType.Player);
		PositionPart positionPart = new PositionPart(startPosition, new Vector2(16, 16), Direction.UP);
		entity.addEntityPart(positionPart);
		
		GameData gameData = new GameData();
		gameData.setDeltaTime(0.016f);
		movingPart.process(gameData, entity);
		
		Vector2 endPosition = positionPart.getPosition();
		
		assertEquals(new Vector2(10, 10), endPosition);
	}

	@Test
	public void process() {
		// Setup mocked elements
		GameData gameData = mock(GameData.class);
		Entity entity = mock(Entity.class);
		PositionPart positionPart = mock(PositionPart.class);
		MovementControllerSPI movementControllerSPI = mock(MovementControllerSPI.class);
		Vector2 position = mock(Vector2.class);

		// Set mocked
		MovingPart mockedMovingPart = new MovingPart(1, 1, movementControllerSPI);

		// Setup return values
		when(positionPart.getPosition()).thenReturn(position);
		when(entity.getEntityPart(PositionPart.class)).thenReturn(positionPart);

		// Setup position
		when(position.getX()).thenReturn(1f);
		when(position.getY()).thenReturn(1f);

		// Run through all directions to go
		for (Direction direction : Direction.values()) {
			when(movementControllerSPI.getMovement(gameData, entity)).thenReturn(direction);

			mockedMovingPart.process(gameData, entity);

			verify(positionPart).setDirection(direction);
		}

		// Verify if no direction is ot be returned from movement controller
		when(movementControllerSPI.getMovement(gameData, entity)).thenReturn(null);

		// Verify positions
		// X
		verify(position, times(2)).setX(1);
		verify(position, times(1)).setX(0);
		verify(position, times(1)).setX(2);
		// Y
		verify(position, times(2)).setY(1);
		verify(position, times(1)).setY(0);
		verify(position, times(1)).setY(2);
	}
}
