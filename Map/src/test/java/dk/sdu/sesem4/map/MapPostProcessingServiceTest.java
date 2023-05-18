package dk.sdu.sesem4.map;

import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class MapPostProcessingServiceTest extends MapPostProcessingService {
	
	@Test
	public void testCheckMapCollisions() {
		GameData gameData = new GameData();
		
		float x = 128f;
		float y = 96;
		
		gameData.setDeltaTime(0.01f);
		Entity entity = new TestEntity(EntityType.Player);
		entity.addEntityPart(new PositionPart(new Vector2(x, y), new Vector2(16, 16), Direction.UP));
		entity.addEntityPart(new MovingPart(1, 1, (_1, _2) -> Direction.RIGHT));
		gameData.getGameEntities().addEntity(entity);
		
		entity.process(gameData, gameData.getGameEntities());
		
		checkMapCollisions(gameData, TestMap.getInstance());
		
		PositionPart positionPart = entity.getEntityPart(PositionPart.class);
		
		assertEquals(x, positionPart.getPosition().getX(), 0.001);
		assertEquals(y, positionPart.getPosition().getY(), 0.001);
	}
	
	@Test
	public void testCheckMapCollisions2() {
		GameData gameData = new GameData();
		
		float x = 128f;
		float y = 96;
		
		Entity entity = new TestEntity(EntityType.Player);
		entity.addEntityPart(new PositionPart(new Vector2(x, y), new Vector2(16, 16), Direction.UP));
		MovingPart movingPart = new MovingPart(10, 1, (_1, _2) -> Direction.DOWN);
		entity.addEntityPart(movingPart);
		gameData.getGameEntities().addEntity(entity);
		
		checkMapCollisions(gameData, TestMap.getInstance());
		
		gameData.setDeltaTime(0.01f);
		movingPart.process(gameData, entity);
		
		checkMapCollisions(gameData, TestMap.getInstance());
		
		PositionPart positionPart = entity.getEntityPart(PositionPart.class);
		
		assertNotEquals(y, positionPart.getPosition().getY(), 0.00001);
	}
}
