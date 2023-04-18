package dk.sdu.sesem4.common.data.CollisionParts;

public class DamagePart implements CollisionPart {

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
        return damage;
    }
}
