package dk.sdu.sesem4.octorok.common;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Octorok extends Entity {
	private float speed = 1;
	private int hearts = 3;
	private float frameRate = 10;

	private List<Path> textures = new ArrayList<>();
	private final Vector2 size = new Vector2(16,16);
	private Direction direction = Direction.UP;

	public Octorok() {
		super(EntityType.Enemy);
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getHearts() {
		return hearts;
	}

	public void setHearts(int hearts) {
		this.hearts = hearts;
	}

	public float getFrameRate() {
		return frameRate;
	}

	public void setFrameRate(float frameRate) {
		this.frameRate = frameRate;
	}

	public Vector2 getSize() {
		return size;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public List<Path> getTextures() {
		return this.textures;
	}

	public void setTextures(List<Path> textures) {
		this.textures = textures;
	}


}
