package dk.sdu.sesem4.player;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {

	private int speed = 1;
	private int hearts = 3;
	private int frameRate = 10;

	private final Vector2 startPosition = new Vector2(128, 88);
	private final Vector2 size = new Vector2(16, 16);

	private Direction direction = Direction.UP;

	/**
	 * Constructor for the Player class.
	 */
	public Player() {
		super(EntityType.Player);
	}

	/**
	 * Get the speed of the entity.
	 *
	 * @return int
	 */
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * Set the speed of the entity.
	 *
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Get the amount of hearts the player has.
	 *
	 * @return int
	 */
	public int getHearts() {
		return this.hearts;
	}

	/**
	 * Set the amount of hearts the player has.
	 *
	 * @param hearts The amount of hearts the player has.
	 */
	public void setHearts(int hearts) {
		this.hearts = hearts;
	}

	/**
	 * Get the frame rate of the player.
	 *
	 * @return The frame rate of the player.
	 */
	public int getFrameRate() {
		return frameRate;
	}

	/**
	 * Set the frame rate of the player.
	 *
	 * @param frameRate
	 */
	public void setFrameRate(int frameRate) {
		this.frameRate = frameRate;
	}

	/**
	 * Get the direction of the player.
	 *
	 * @return The direction of the player.
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Set the direction of the player.
	 *
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Get the start position of the player.
	 *
	 * @return The start position of the player.
	 */
	public Vector2 getStartPosition() {
		return this.startPosition;
	}


	/**
	 * Get the size of the player.
	 *
	 * @return The size of the player.
	 */
	public Vector2 getSize() {
		return this.size;
	}
}
