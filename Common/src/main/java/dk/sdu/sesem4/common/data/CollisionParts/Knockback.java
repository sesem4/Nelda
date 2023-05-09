package dk.sdu.sesem4.common.data.CollisionParts;

import dk.sdu.sesem4.common.util.Direction;

/**
 * The Knockback class represents a Knockback that is applied to an Entity upon
 */
public class Knockback {

	/**
	 * The direction the Knockback should push the Entity in
	 */
	private Direction direction;

	/**
	 * The duration of the Knockback
	 */
	private float duration;

	/**
	 * The speed with which the Knockback should push the Entity in
	 */
	private float speed;

	/**
	 * Constructs a Knockback
	 * 
	 * @param direction the direction the Knockback should push the Entity in
	 * @param duration  for how long the Knockback should take place
	 * @param speed     the speed with which the Knockback should push the Entity in
	 */
	public Knockback(Direction direction, float duration, float speed) {
		this.direction = direction;
		this.duration = duration;
		this.speed = speed;
	}

	/**
	 * Sets for how long the Entity will be knocked back
	 * 
	 * @param duration the amount of time the Knockback should last
	 */
	public void setDuration(float duration) {
		this.duration = duration;
	}

	/**
	 * Gets for how long the Entity will be knocked back
	 * 
	 * @return duration
	 */
	public float getDuration() {
		return this.duration;
	}

	/**
	 * Decrements the duration of the Knockback by 1
	 *
	 * @param deltaTime the time that has passed since the last update
	 */
	public void decrementDuration(float deltaTime) {
		this.setDuration(this.getDuration() - deltaTime);
	}

	/**
	 * Gets the direction the Knockback pushes the Entity in
	 * 
	 * @return direction
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Gets the speed with which the Entity is knocked back
	 * 
	 * @return speed
	 */
	public float getSpeed() {
		return this.speed;
	}

	/**
	 * Checks whether the duration of the Knockback has run out
	 * 
	 * @return Boolean
	 */
	public boolean hasRunOut() {
		return this.getDuration() <= 0;
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

		return otherKnockback.direction == this.direction
				&& otherKnockback.duration == this.duration
				&& otherKnockback.speed == this.speed;
	}
}
