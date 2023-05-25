import dk.sdu.sesem4.common.SPI.CombatControllerSPI;
import dk.sdu.sesem4.common.SPI.CombatSPI;
import dk.sdu.sesem4.common.SPI.PluginServiceSPI;
import dk.sdu.sesem4.sword.SwordCombat;
import dk.sdu.sesem4.sword.SwordCombatController;
import dk.sdu.sesem4.sword.SwordPlugin;

module Sword {
	requires Common;
	requires ShadedLibGDX;

	provides PluginServiceSPI with SwordPlugin;
	provides CombatSPI with SwordCombat;
	provides CombatControllerSPI with SwordCombatController;
}