package dk.sdu.sesem4.common.data.rendering;

import junit.framework.TestCase;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SpriteDataTest {
	@Test
	public void testGetTexture() {
		String file = Paths.get("file").toString();
		SpriteData spriteData = new SpriteData(file, true, true);
		assertEquals(file, spriteData.getTexture());

		file = Paths.get("other-file").toString();
		spriteData = new SpriteData(file);
		assertEquals(file, spriteData.getTexture());

		spriteData = new SpriteData("file");
		assertEquals("file", spriteData.getTexture().toString());
	}

	@Test
	public void testSetTexture() {
		String file = Paths.get("file").toString();
		SpriteData spriteData = new SpriteData(file);
		String otherFile = Paths.get("other-file").toString();
		spriteData.setTexture(otherFile);
		assertEquals(otherFile, spriteData.getTexture());
	}

	@Test
	public void testIsxFlipped() {
		String file = Paths.get("file").toString();

		SpriteData spriteData = new SpriteData(file, true, true);
		assertTrue(spriteData.isxFlipped());

		spriteData = new SpriteData(file, false, false);
		assertFalse(spriteData.isxFlipped());
	}

	@Test
	public void testIsyFlipped() {
		String file = Paths.get("file").toString();

		SpriteData spriteData = new SpriteData(file, true, true);
		assertTrue(spriteData.isyFlipped());

		spriteData = new SpriteData(file, false, false);
		assertFalse(spriteData.isyFlipped());
	}

	@Test
	public void testSetXFlipped() {
		String file = Paths.get("file").toString();

		SpriteData spriteData = new SpriteData(file, true, true);
		assertTrue(spriteData.isxFlipped());

		spriteData.setXFlipped(false);
		assertFalse(spriteData.isxFlipped());
	}

	@Test
	public void testSetYFlipped() {
		String file = Paths.get("file").toString();

		SpriteData spriteData = new SpriteData(file, true, true);
		assertTrue(spriteData.isyFlipped());

		spriteData.setYFlipped(false);
		assertFalse(spriteData.isyFlipped());
	}

	@Test
	public void testGetUUID() {
		SpriteData spriteData1 = new SpriteData("1");
		SpriteData spriteData2 = new SpriteData("1");

		assertNotEquals(spriteData1, spriteData2);
	}
}