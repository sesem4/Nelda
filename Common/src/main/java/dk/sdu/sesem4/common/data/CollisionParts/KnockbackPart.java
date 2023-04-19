package dk.sdu.sesem4.common.data.CollisionParts;

/**
 * The KnockbackPart is a CollisionPart that is applied to an Entity upon collision with another Entity.
 */
public class KnockbackPart implements CollisionPart {

	/**
	 * The duration for how long the Entity Knockback effects should be applied to another Entity upon collision
	 */
	private int duration;

	/**
	 * The speed that the Entity knockback should push another Entity upon collision
	 */
	private float speed;

	/**
	 * Constructs a KnockbackPart
	 * @param duration for how long the Entity Knockback effects should be applied to another Entity upon collision
	 * @param speed how fast the Entity Knockback should push another Entity upon collision
	 */
	public KnockbackPart(int duration, float speed) {
		this.duration = duration;
		this.speed = speed;
	}

	/**
	 * Gets for how long the Entity Knockback effects should be applied to another Entity upon collision
	 * @return duration
	 */
	public int getDuration() {
		return this.duration;
	}

	/**
	 * Gets the speed with which the Entity Knockback should push another Entity upon collision
	 * @return speed
	 */
	public float getSpeed() {
		return this.speed;
	}
}
