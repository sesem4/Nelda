package dk.sdu.sesem4.player;

import dk.sdu.sesem4.common.data.entity.Entity;
import dk.sdu.sesem4.common.data.entity.EntityType;
import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

public class Player extends Entity {

	/**
	 * Constructor for the Player class.
	 */
	public Player() {
		super(EntityType.Player);
	}
}
