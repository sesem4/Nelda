package dk.sdu.sesem4.collision;

import dk.sdu.sesem4.common.SPI.PostProcessingServiceSPI;
import dk.sdu.sesem4.common.data.EntityParts.PositionPart;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.math.Rectangle;
import dk.sdu.sesem4.common.data.process.Priority;

import java.util.ArrayList;
import java.util.List;

public class CollisionSystem implements PostProcessingServiceSPI {
	@Override
	public void postProcess(GameData gameData, Priority priority) {
		List<Collision> collisions = getCollisions(gameData.getGameEntities().getEntities());
		for (Collision collision : collisions) {
			collision.a.collided(collision.b);
			collision.b.collided(collision.a);
		}
	}

	private List<Collision> getCollisions(List<Entity> entities) {
		List<Collision> collisions = new ArrayList<>();
		for (int i = 0; i < entities.size(); i++) {
			for (int j = i+1; j < entities.size(); j++) {
				Entity a = entities.get(i);
				Entity b = entities.get(j);
				if (doesCollide(a, b)) {
					collisions.add(new Collision(a, b));
				}
			}
		}
		return collisions;
	}

	private boolean doesCollide(Entity a, Entity b) {
		PositionPart aPosPart = a.getEntityPart(PositionPart.class);
		PositionPart bPosPart = b.getEntityPart(PositionPart.class);

		Rectangle aBox = new Rectangle(aPosPart.getPosition(), aPosPart.getSize());
		Rectangle bBox = new Rectangle(bPosPart.getPosition(), bPosPart.getSize());

		return aBox.collidesWith(bBox);
	}
}
