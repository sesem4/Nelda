package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.rendering.SpriteData;

import java.util.UUID;

public class SpritePart implements EntityPart {
	/** Current sprite for entity */
	private SpriteData sprite;

	/** Id */
	private final UUID id;

	/**
	 * Construct SpritePart with sprite
	 *
	 * @param sprite Sprite to initially set on entity
	 */
	public SpritePart(SpriteData sprite) {
		this.sprite = sprite;
		this.id = UUID.randomUUID();
	}

	public SpriteData getSprite() {
		return sprite;
	}

	public void setSprite(SpriteData sprite) {
		this.sprite = sprite;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public void process(GameData gameData, Entity entity) {

	}
}
