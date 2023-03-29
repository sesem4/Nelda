package dk.sdu.sesem4.common.SPI;

import dk.sdu.sesem4.common.data.Entity;
import dk.sdu.sesem4.common.data.GameData;

public interface CombatSPI {
    /**
     * Spawn weapon for combat
     * <br><br>
     * Pre-condition: An entity has to be existing, with position and direction data.<br>
     * post-condition: Weapon entity has been spawned in the world.
     *
     * @param gameData The game data
     * @param entity The entity that spawns the weapon
     */
    void spawnWeapon(GameData gameData, Entity entity);
}
