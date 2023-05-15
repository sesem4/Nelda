package dk.sdu.sesem4.sword;


import dk.sdu.sesem4.common.data.combat.Weapon;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class Sword extends Entity {

	private Weapon weapon;

	/**
	 * Constructor for the Sword class.
	 */
	public Sword(float lifeTime) {
		super(EntityType.PlayerProjectile);
		this.weapon = new Weapon(lifeTime, this);
	}

//	public void collided(GameData gameData, Entity other) {
//
//	}

	public void collidedWithMap(GameData gameData) {

	}

	public Weapon getWeapon() {
		return weapon;
	}
}
