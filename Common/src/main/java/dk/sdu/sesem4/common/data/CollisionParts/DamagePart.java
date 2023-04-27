package dk.sdu.sesem4.common.data.CollisionParts;

/**
 * The DamagePart is a CollisionPart that is applied to an Entity upon collision with another Entity.
 */
public class DamagePart implements CollisionPart {
	/**
	 * The amount of damage that should be dealt to another Entity.
	 */
	private final int damage;
	
	/**
	 * Constructs a DamagePart
	 * @param damage the amount of damage that should be dealt to another Entity, upon collision
	 */
	public DamagePart(int damage) {
		this.damage = damage;
	}
	
	/**
	 * Gets the damage value of the DamagePart
	 * @return damage
	 */
	public int getDamage() {
		return this.damage;
	}
}
