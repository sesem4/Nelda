package dk.sdu.sesem4.sword;

import dk.sdu.sesem4.common.SPI.CombatControllerSPI;
import dk.sdu.sesem4.common.SPI.CombatSPI;
import dk.sdu.sesem4.common.data.combat.WeaponType;

public class SwordCombat implements CombatSPI {
	private final CombatControllerSPI combatControllerSPI;

	public SwordCombat() {
		combatControllerSPI = new SwordCombatController();
	}

	@Override
	public WeaponType getType() {
		return combatControllerSPI.getWeaponType();
	}

	@Override
	public CombatControllerSPI getCombatController() {
		return combatControllerSPI;
	}
}
