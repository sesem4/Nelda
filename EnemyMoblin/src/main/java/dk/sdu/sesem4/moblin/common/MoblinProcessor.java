package dk.sdu.sesem4.moblin.common;

import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;

public class MoblinProcessor implements ProcessingServiceSPI {
	@Override
	public void process(GameData gameData, Priority priority) {
		//process the player for each game loop, change Movement, Position, LifePart, Path to Texture,
		for (Entity moblin : gameData.getGameEntities().getEntities(Moblin.class)) {
			PositionPart positionPart = moblin.getEntityPart(PositionPart.class);
			MovingPart movingPart = moblin.getEntityPart(MovingPart.class);
			LifePart lifePart = moblin.getEntityPart(LifePart.class);
			SpritePart spritePart = moblin.getEntityPart(SpritePart.class);

			//process all the parts
			positionPart.process(gameData, moblin);
			movingPart.process(gameData, moblin);
			lifePart.process(gameData, moblin);
			spritePart.process(gameData, moblin);
		}
	}
}
