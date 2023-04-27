package dk.sdu.sesem4.common.data.rendering;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SpriteData {
    /** Path for the texture of the sprite */
    private Path texture;
    /** Sprite is flipped on the x-axis */
    private boolean xFlipped;
    /** Sprite is flipped on the y-axis */
    private boolean yFlipped;

    /**
     * Construct a SpriteData based on texture, if flipped x and if flipped y.
     *
     * @param texture Path for texture
     * @param xFlipped True if sprite should be flipped on the x-axis
     * @param yFlipped True if sprite should be flipped on the y-axis
     */
    public SpriteData(Path texture, boolean xFlipped, boolean yFlipped) {
        this.texture = texture;
        this.xFlipped = xFlipped;
        this.yFlipped = yFlipped;
    }

    /**
     * Construct a SpriteData based on the path for the texture of the sprite, where xFlipped and yFlipped are false as default.
     * @param texture Path for texture
     */
    public SpriteData(Path texture) {
        this(texture, false, false);
    }

    /**
     * Construct a SpriteData based on file name og string representing the file path.
     *
     * @param fileName String file path
     */
    public SpriteData(String fileName) {
        this(Paths.get(fileName));
    }

    public Path getTexture() {
        return texture;
    }

    public void setTexture(Path texture) {
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
}
