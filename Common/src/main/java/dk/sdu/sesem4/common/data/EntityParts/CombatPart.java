package dk.sdu.sesem4.common.data.EntityParts;

import dk.sdu.sesem4.common.SPI.CombatControllerSPI;
import dk.sdu.sesem4.common.data.combat.Weapon;
import dk.sdu.sesem4.common.data.combat.WeaponType;
import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.gamedata.GameData;
import dk.sdu.sesem4.common.data.rendering.SpriteData;
import dk.sdu.sesem4.common.util.CombatControllerLocator;
import dk.sdu.sesem4.common.util.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represents the CombatPart.
 */
public class CombatPart implements EntityPart {

	/**
	 * the list of SpriteData for the sprites of each direction
	 */
	private final Map<Direction, List<SpriteData>> combatSpriteList;

	/**
	 * The type of weapon that the Entity should use
	 */
	private WeaponType type;

	/**
	 * Combat controller currently used for the weapon type
	 */
	private CombatControllerSPI combatController;

	/**
	 * Currently spawned weapong
	 */
	private Weapon weapon;

	/**
	 * The constructor for the CombatPart class.
	 *
	 * @param type the type of weapon that the Entity should use
	 */
	public CombatPart(WeaponType type) {
		this(type, null);
	}

	/**
	 * The constructor for the CombatPart class.
	 *
	 * @param type    the type of weapon that the Entity should use
	 * @param sprites the list of string path for the sprites of each direction
	 */
	public CombatPart(WeaponType type, Map<Direction, List<SpriteData>> sprites) {
		this.type = type;

		this.combatController = CombatControllerLocator.locateController(this.type);

		// Setup sprite list
		this.combatSpriteList = new HashMap<>();
		if (sprites == null) {
			return;
		}

		for (Direction spriteListDirection : Direction.values()) {
			this.combatSpriteList.put(spriteListDirection, new ArrayList<>());
		}
	}


	@Override
	public void process(GameData gameData, Entity entity) {
		if (this.weapon != null) {
			this.weapon.incrementDuration(gameData.getDeltaTime());

			if (this.weapon.isFinished()) {
				gameData.getGameEntities().removeEntity(weapon.getEntity());
				this.weapon = null;
			}

			return;
		}

		if (this.combatController.shouldAttack(gameData, entity)) {
			this.weapon = this.combatController.spawnAttack(gameData, entity);
		}
	}

	/**
	 * Replace the sprites for the given direction.
	 *
	 * @param direction The direction to replace the sprites for
	 * @param sprites   The list of sprites to replaced with. The content should be the string path of the sprite
	 */
	public void setSprites(Direction direction, List<SpriteData> sprites) {
		this.combatSpriteList.replace(direction, sprites);
	}

	/**
	 * Add a sprite to the current list of sprites for the provided direction
	 *
	 * @param direction The direction to add sprite to
	 * @param sprite    The string path of sprite
	 */
	public void addSprite(Direction direction, SpriteData sprite) {
		this.combatSpriteList.get(direction).add(sprite);

	}

	/**
	 * Get the list of sprites for the provided direction
	 *
	 * @param direction The direction to get sprites from
	 * @return The list of sprites. The content is a list of string path for the sprites
	 */
	public List<SpriteData> getSprites(Direction direction) {
		return this.combatSpriteList.get(direction);

	}
}
