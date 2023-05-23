package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.SPI.CombatControllerSPI;
import dk.sdu.sesem4.common.data.combat.Weapon;
import dk.sdu.sesem4.common.data.combat.WeaponType;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.gamedata.GameEntities;
import dk.sdu.sesem4.common.util.CombatControllerLocator;
import org.junit.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

public class CombatPartTest {

	@Test
	public void process() {
		// Setup general mocks
		GameData gameData = mock(GameData.class);
		GameEntities gameEntities = mock(GameEntities.class);
		Entity entity = mock(Entity.class);
		CombatControllerSPI combatControllerSPI = mock(CombatControllerSPI.class);
		Weapon weapon = mock(Weapon.class);
		Entity weaponEntity = mock(Entity.class);

		// Test should attack
		try (MockedStatic<CombatControllerLocator> locator = mockStatic(CombatControllerLocator.class)) {
			locator.when(() -> CombatControllerLocator.locateController(any())).thenReturn(combatControllerSPI);

			EntityPart combatPart = new CombatPart(WeaponType.SWORD);
			combatPart.process(gameData, entity);

			verify(combatControllerSPI).shouldAttack(gameData, entity);
		}

		// Test spawning of attacks
		try (MockedStatic<CombatControllerLocator> locator = mockStatic(CombatControllerLocator.class)) {
			locator.when(() -> CombatControllerLocator.locateController(any())).thenReturn(combatControllerSPI);
			when(combatControllerSPI.shouldAttack(gameData, entity)).thenReturn(true);

			EntityPart combatPart = new CombatPart(WeaponType.SWORD);
			combatPart.process(gameData, entity);

			verify(combatControllerSPI).spawnAttack(gameData, entity);
		}

		// Test the weapon handling when attack is spawned
		try (MockedStatic<CombatControllerLocator> locator = mockStatic(CombatControllerLocator.class)) {
			// Setup start paramters for this specific test part
			locator.when(() -> CombatControllerLocator.locateController(any())).thenReturn(combatControllerSPI);
			when(combatControllerSPI.shouldAttack(gameData, entity)).thenReturn(true);
			when(combatControllerSPI.spawnAttack(gameData, entity)).thenReturn(weapon);
			when(gameData.getDeltaTime()).thenReturn(1f);
			when(gameData.getGameEntities()).thenReturn(gameEntities);

			// Run start combat part
			EntityPart combatPart = new CombatPart(WeaponType.SWORD);
			combatPart.process(gameData, entity);
			combatPart.process(gameData, entity);

			// Verify that the weapon was set and run through without being finished
			verify(weapon).incrementDuration(1f);
			verify(weapon).isFinished();

			// Setup data for weapon being finished
			when(weapon.isFinished()).thenReturn(true);
			when(weapon.getEntity()).thenReturn(weaponEntity);
			combatPart.process(gameData, entity);

			// Verify that the weapon is set to be removed
			verify(gameEntities).removeEntity(weaponEntity);
		}
	}
}