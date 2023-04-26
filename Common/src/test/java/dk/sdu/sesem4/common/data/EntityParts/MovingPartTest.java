package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.SPI.MovementControllerSPI;
import dk.sdu.sesem4.common.data.CollisionParts.Knockback;
import dk.sdu.sesem4.common.data.rendering.SpriteData;
import dk.sdu.sesem4.common.util.Direction;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
		assertEquals(10,movingPart.getMoveSpeed());
	}

	@Test
	public void testSetMoveSpeed() {
		assertEquals(10,movingPart.getMoveSpeed());
		movingPart.setMoveSpeed(20);
		assertEquals(20,movingPart.getMoveSpeed());
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
}