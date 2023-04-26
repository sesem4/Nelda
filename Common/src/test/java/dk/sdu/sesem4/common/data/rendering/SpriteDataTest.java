package dk.sdu.sesem4.common.data.rendering;

import junit.framework.TestCase;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SpriteDataTest {
	@Test
	public void testGetTexture() {
		Path file = Paths.get("file");
		SpriteData spriteData = new SpriteData(file, true, true);
		assertEquals(file, spriteData.getTexture());

		file = Paths.get("other-file");
		spriteData = new SpriteData(file);
		assertEquals(file, spriteData.getTexture());

		spriteData = new SpriteData("file");
		assertEquals("file", spriteData.getTexture().toString());
	}

	@Test
	public void testSetTexture() {
		Path file = Paths.get("file");
		SpriteData spriteData = new SpriteData(file);
		Path otherFile = Paths.get("other-file");
		spriteData.setTexture(otherFile);
		assertEquals(otherFile, spriteData.getTexture());
	}

	@Test
	public void testIsxFlipped() {
		Path file = Paths.get("file");

		SpriteData spriteData = new SpriteData(file, true, true);
		assertTrue(spriteData.isxFlipped());

		spriteData = new SpriteData(file, false, false);
		assertFalse(spriteData.isxFlipped());
	}

	@Test
	public void testIsyFlipped() {
		Path file = Paths.get("file");

		SpriteData spriteData = new SpriteData(file, true, true);
		assertTrue(spriteData.isyFlipped());

		spriteData = new SpriteData(file, false, false);
		assertFalse(spriteData.isyFlipped());
	}

	@Test
	public void testSetXFlipped() {
		Path file = Paths.get("file");

		SpriteData spriteData = new SpriteData(file, true, true);
		assertTrue(spriteData.isxFlipped());

		spriteData.setXFlipped(false);
		assertFalse(spriteData.isxFlipped());
	}

	@Test
	public void testSetYFlipped() {
		Path file = Paths.get("file");

		SpriteData spriteData = new SpriteData(file, true, true);
		assertTrue(spriteData.isyFlipped());

		spriteData.setYFlipped(false);
		assertFalse(spriteData.isyFlipped());
	}
}