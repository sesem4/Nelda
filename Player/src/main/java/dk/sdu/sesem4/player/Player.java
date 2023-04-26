package dk.sdu.sesem4.player;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.util.Direction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {

	private int speed = 5;
	private int hearts = 3;
	private final float startPositionX = 128;
	private final float startPositionY = 88;
	private float currentPositionX = startPositionX;
	private float currentPositionY = startPositionY;
	private List<Path> texturesPath = new ArrayList<Path>();

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
	 * Get the start position of the player on the x-axis.
	 *
	 * @return The start position of the player on the x-axis.
	 */
	public float getStartPositionX() {
		return this.startPositionX;
	}

	/**
	 * Get the start position of the player on the y-axis.
	 *
	 * @return The start position of the player on the y-axis.
	 */
	public float getStartPositionY() {
		return this.startPositionY;
	}

	/**
	 * Get the current position of the player on the x-axis.
	 *
	 * @return The current position of the player on the x-axis.
	 */
	public float getCurrentPositionX() {
		return this.currentPositionX;
	}

	/**
	 * Get the current position of the player on the y-axis.
	 *
	 * @return Get the current position of the player on the y-axis.
	 */
	public float getCurrentPositionY() {
		return currentPositionY;
	}


	/**
	 * Set the current position of the player on the x-axis.
	 *
	 * @param currentPositionX
	 */
	public void setCurrentPositionX(float currentPositionX) {
		this.currentPositionX = currentPositionX;
	}


	/**
	 * Set the current position of the player on the y-axis.
	 *
	 * @param currentPositionY
	 */
	public void setCurrentPositionY(float currentPositionY) {
		this.currentPositionY = currentPositionY;
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

	public List<Path> getTexturesPath() {
		return this.texturesPath;
	}

	public void setTexturesPath(List<Path> texturesPath) {
		this.texturesPath = texturesPath;
	}
}
