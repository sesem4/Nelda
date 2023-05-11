package dk.sdu.sesem4.moblin.common;

import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.LifePart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.process.Priority;

public class MoblinPostProcessor implements PostProcessingServiceSPI {
	@Override
	public void postProcess(GameData gameData, Priority priority) {
		for (Entity moblin : gameData.getGameEntities().getEntities(Moblin.class)) {
			LifePart lifePart = moblin.getEntityPart(LifePart.class);

			if (lifePart.isDead()) {
				gameData.getGameEntities().removeEntity(moblin);
			}
		}
	}
}
