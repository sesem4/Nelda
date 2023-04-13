package dk.sdu.sesem4.common.data.CollisionParts;

public class DamagePart implements CollisionPart {
	int damage;

	public DamagePart(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}
}
