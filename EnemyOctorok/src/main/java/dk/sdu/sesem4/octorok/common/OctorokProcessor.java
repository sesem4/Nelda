package dk.sdu.sesem4.octorok.common;

import dk.sdu.sesem4.common.SPI.ProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.EntityParts.MovingPart;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.EntityParts.SpritePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;

abstract public class OctorokProcessor implements ProcessingServiceSPI {
	@Override
	public void process(GameData gameData, Priority priority) {
		//process the player for each game loop, change Movement, Position, LifePart, Path to Texture,
		for (Entity octorok : gameData.getGameEntities().getEntities(Octorok.class)) {
			PositionPart positionPart = octorok.getEntityPart(PositionPart.class);
			MovingPart movingPart = octorok.getEntityPart(MovingPart.class);
			LifePart lifePart = octorok.getEntityPart(LifePart.class);
			SpritePart spritePart = octorok.getEntityPart(SpritePart.class);

			//process all the parts
			positionPart.process(gameData, octorok);
			movingPart.process(gameData, octorok);
			lifePart.process(gameData, octorok);
			spritePart.process(gameData, octorok);
		}

	}
}
