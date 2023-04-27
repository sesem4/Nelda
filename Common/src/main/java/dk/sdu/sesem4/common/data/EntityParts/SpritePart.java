package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.rendering.SpriteData;

public class SpritePart {
	/** Current sprite for entity */
	private SpriteData sprite;

	/**
	 * Construct SpritePart with sprite
	 *
	 * @param sprite Sprite to initially set on entity
	 */
	public SpritePart(SpriteData sprite) {
		this.sprite = sprite;
	}

	public SpriteData getSprite() {
		return sprite;
	}

	public void setSprite(SpriteData sprite) {
		this.sprite = sprite;
	}
}
