package dk.sdu.sesem4.common.data.CollisionParts;

import dk.sdu.sesem4.common.util.Direction;

public class Knockback {
	private Direction direction;
	private int duration;
	private float speed;
	public Knockback(Direction direction, int duration, float speed) {
		this.direction = direction;
		this.duration = duration;
		this.speed = speed;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Direction getDirection() {
		return direction;
	}

	public int getDuration() {
		return duration;
	}

	public float getSpeed() {
		return speed;
	}

	public void decrementDuration() {
		setDuration(getDuration() - 1);
	}

	public boolean hasRunOut() {
		return getDuration() <= 0;
	}


	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Knockback)) return false;
		Knockback otherKnockback = (Knockback)other;

		return otherKnockback.direction == this.direction &&
				otherKnockback.duration == this.duration &&
				otherKnockback.speed == this.speed;
	}
}
