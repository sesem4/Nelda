package dk.sdu.sesem4.octorok.common;

import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;
import dk.sdu.sesem4.octorok.blue.BlueOctorok;
import dk.sdu.sesem4.octorok.red.RedOctorok;

public class OctorokPostProcessor implements PostProcessingServiceSPI {
	@Override
	public void postProcess(GameData gameData, Priority priority) {
		for (Entity octorok : gameData.getGameEntities().getEntities(Octorok.class)) {
			LifePart lifePart = octorok.getEntityPart(LifePart.class);

			if (lifePart.isDead()) {
				gameData.getGameEntities().removeEntity(octorok);
			}
		}
	}
}
