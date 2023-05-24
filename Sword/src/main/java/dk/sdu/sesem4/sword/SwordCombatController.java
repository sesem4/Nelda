package dk.sdu.sesem4.sword;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dk.sdu.sesem4.common.SPI.CombatControllerSPI;
import dk.sdu.sesem4.common.data.combat.Weapon;
import dk.sdu.sesem4.common.data.combat.WeaponType;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;

public class SwordCombatController implements CombatControllerSPI {
	@Override
	public boolean shouldAttack(GameData gameData, Entity entity) {
		return Gdx.input.isKeyPressed(Input.Keys.SPACE);
	}

	@Override
	public Weapon spawnAttack(GameData gameData, Entity entity) {
		SwordPlugin swordPlugin = new SwordPlugin();
		Sword sword = swordPlugin.spawn(gameData, entity);
		return sword.getWeapon();
	}

	@Override
	public WeaponType getWeaponType() {
		return WeaponType.SWORD;
	}
}
