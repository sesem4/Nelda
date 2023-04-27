package dk.sdu.sesem4.collision;

import dk.sdu.sesem4.common.data.entity.Entity;


/**
 * Contains the data about a specific collision.
 * Currently only which Entities are involved.
 */
public class Collision {
	public Entity a;
	public Entity b;

	public Collision(Entity a, Entity b) {
		this.a = a;
		this.b = b;
	}
}
