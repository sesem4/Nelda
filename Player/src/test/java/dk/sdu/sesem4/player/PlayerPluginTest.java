package dk.sdu.sesem4.player;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerPluginTest extends PlayerPlugin{


	@Test
	public void createPlayerTest() {
		this.player = new Player();
		PositionPart positionPart = new PositionPart(this.player.getStartPosition(),this.player.getSize(),this.player.getDirection());
		assertEquals(positionPart.getPosition(),this.player.getStartPosition());

		MovingPart movingPart = new MovingPart(
				this.player.getSpeed(),
				this.player.getFrameRate(),
				(gameData, entity) -> null  // THIS LINE IS STILL MISSING, WAITING FOR KEYBOARDCOMPONENT TO WORK.
		);
		assertEquals(movingPart.getMoveSpeed(),this.player.getSpeed());

		this.player.addEntityPart(movingPart);
		this.player.addEntityPart(positionPart);
		assertEquals(this.player.getEntityPart(PositionPart.class),positionPart);
		assertEquals(this.player.getEntityPart(MovingPart.class),movingPart);


	}
}