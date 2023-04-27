package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.data.rendering.SpriteData;

import static org.junit.Assert.*;

public class SpritePartTest {

	public void testGetSprite() {
		SpriteData spriteData = new SpriteData("");
		SpritePart spritePart = new SpritePart(spriteData);

		assertEquals(spriteData, spritePart.getSprite());

		spriteData = new SpriteData("file");
		spritePart = new SpritePart(spriteData);

		assertEquals(spriteData, spritePart.getSprite());
	}

	public void testSetSprite() {
		SpriteData spriteData = new SpriteData("");
		SpritePart spritePart = new SpritePart(spriteData);

		assertEquals(spriteData, spritePart.getSprite());

		SpriteData otherSpriteData = new SpriteData("");
		spritePart.setSprite(otherSpriteData);

		assertEquals(otherSpriteData, spritePart.getSprite());
	}
}