package dk.sdu.sesem4.common.data.CollisionParts;

import dk.sdu.sesem4.common.util.Direction;

public class Knockback {
	public Knockback(Direction direction, int duration, float speed) {
		this.direction = direction;
		this.duration = duration;
		this.speed = speed;
	}
	Direction direction;
	int duration;
	float speed;

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
}
