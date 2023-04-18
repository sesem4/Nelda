package dk.sdu.sesem4.common.data.CollisionParts;

import dk.sdu.sesem4.common.util.Direction;

public class Knockback {

	private Direction direction;
	private int duration;
	private float speed;

	/**
	 * Constructs a Knockback
	 * 
	 * @param direction the direction the Knockback should push the Entity in
	 * @param duration  for how long the Knockback should take place
	 * @param speed     the speed with which the Knockback should push the Entity in
	 */
	public Knockback(Direction direction, int duration, float speed) {
		this.direction = direction;
		this.duration = duration;
		this.speed = speed;
	}

	/**
	 * Sets for how long the Entity will be knocked back
	 * 
	 * @param duration the amount of time the Knockback should last
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Gets for how long the Entity will be knocked back
	 * 
	 * @return duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Decrements the duration of the Knockback by 1
	 */
	public void decrementDuration() {
		setDuration(getDuration() - 1);
	}

	/**
	 * Gets the direction the Knockback pushes the Entity in
	 * 
	 * @return direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Gets the speed with which the Entity is knocked back
	 * 
	 * @return speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Checks whether the duration of the Knockback has run out
	 * 
	 * @return Boolean
	 */
	public boolean hasRunOut() {
		return getDuration() <= 0;
	}

	/**
	 * Checks whether two Knockbacks have equal direction, duration and speed values
	 * 
	 * @param other the Entity that this Entity collides with
	 * @return Boolean
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Knockback))
			return false;
		Knockback otherKnockback = (Knockback) other;

		return otherKnockback.direction == this.direction &&
				otherKnockback.duration == this.duration &&
				otherKnockback.speed == this.speed;
	}
}
