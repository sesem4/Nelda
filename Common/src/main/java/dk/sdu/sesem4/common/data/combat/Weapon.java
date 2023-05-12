package dk.sdu.sesem4.common.data.combat;

import dk.sdu.sesem4.common.data.entity.Entity;

public class Weapon {

	private float duration;

	private final float lifeTime;

	private final Entity entity;

	public Weapon(float lifeTime, Entity entity) {
		this.lifeTime = lifeTime;
		this.entity = entity;
	}

	public float getDuration() {
		return duration;
	}

	public float getLifeTime() {
		return lifeTime;
	}

	public void incrementDuration(float deltaTime) {
		this.duration += deltaTime;
	}

	public boolean isFinished() {
		return this.duration >= this.lifeTime;
	}

	public Entity getEntity() {
		return entity;
	}
}
