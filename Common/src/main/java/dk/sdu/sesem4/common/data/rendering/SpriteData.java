package dk.sdu.sesem4.common.data.rendering;

import java.util.UUID;

public class SpriteData {
	/** Path for the texture of the sprite */
	private String texture;
	/** Sprite is flipped on the x-axis */
	private boolean xFlipped;
	/** Sprite is flipped on the y-axis */
	private boolean yFlipped;
	/** Resource location, of the sprite texture */
	private Class<?> resourceClass;
	/** ID to uniquely identify this sprite combination */
	private UUID uuid;

	/**
	 * Construct a SpriteData based on texture, if flipped x and if flipped y.
	 *
	 * @param texture  Path for texture
	 * @param xFlipped True if sprite should be flipped on the x-axis
	 * @param yFlipped True if sprite should be flipped on the y-axis
	 */
	public SpriteData(String texture, boolean xFlipped, boolean yFlipped) {
		this(texture, xFlipped, yFlipped, null);
	}

	/**
	 * Construct a SpriteData based on texture, if flipped x and if flipped y.
	 *
	 * @param texture       Path for texture
	 * @param xFlipped      True if sprite should be flipped on the x-axis
	 * @param yFlipped      True if sprite should be flipped on the y-axis
	 * @param resourceClass The class which the ressource is located in
	 */
	public SpriteData(String texture, boolean xFlipped, boolean yFlipped, Class<?> resourceClass) {
		this.texture = texture;
		this.xFlipped = xFlipped;
		this.yFlipped = yFlipped;
		this.resourceClass = resourceClass;
		this.uuid = UUID.randomUUID();
	}

	/**
	 * Construct a SpriteData based on file name og string representing the file
	 * path.
	 *
	 * @param fileName String file path
	 */
	public SpriteData(String fileName) {
		this(fileName, false, false);
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public boolean isxFlipped() {
		return xFlipped;
	}

	public boolean isyFlipped() {
		return yFlipped;
	}

	public void setXFlipped(boolean xFlipped) {
		this.xFlipped = xFlipped;
	}

	public void setYFlipped(boolean yFlipped) {
		this.yFlipped = yFlipped;
	}

	public Class<?> getResourceClass() {
		return this.resourceClass;
	}

	public UUID getUuid() {
		return uuid;
	}
}
