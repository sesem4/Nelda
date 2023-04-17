package dk.sdu.sesem4.common.data.CollisionParts;

public class KnockbackPart implements CollisionPart {
	int duration;
	float speed;

	public KnockbackPart(int duration, float speed) {
		this.duration = duration;
		this.speed = speed;
	}

	public int getDuration() {
		return duration;
	}

	public float getSpeed() {
		return speed;
	}
}
