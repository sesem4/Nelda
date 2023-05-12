package dk.sdu.sesem4.common.data.combat;

import dk.sdu.sesem4.common.data.entity.Entity;

/**
 * Used to determine the duration of a weapon
 */
public class Weapon {

	// Duration of the weapon
	private float duration;

	// Life time of the weapon
	private final float lifeTime;

	// The entity that is using the weapon
	private final Entity entity;

	public Weapon(float lifeTime, Entity entity) {
		this.lifeTime = lifeTime;
		this.entity = entity;
	}

	/**
	 * Get the duration of the weapon
	 *
	 * @return The duration of the weapon
	 */
	public float getDuration() {
		return duration;
	}

	/**
	 * Get the life time of the weapon
	 *
	 * @return The life time of the weapon
	 */
	public float getLifeTime() {
		return lifeTime;
	}

	/**
	 * Increment the duration of the weapon
	 *
	 * @param deltaTime The time since the last update
	 */
	public void incrementDuration(float deltaTime) {
		this.duration += deltaTime;
	}

	/**
	 * Check if the weapon is finished
	 *
	 * @return True if the weapon is finished
	 */
	public boolean isFinished() {
		return this.duration >= this.lifeTime;
	}

	/**
	 * Get the entity that is using the weapon
	 *
	 * @return The entity of the weapon
	 */
	public Entity getEntity() {
		return entity;
	}
}
