package dk.sdu.sesem4.common.event;

import dk.sdu.sesem4.common.util.Direction;

/**
 * @author Jakob L.M. & Jon F.J.
 * A class that represents the MapTransitionEvent, which is used to transition the map.
 */
public class MapTransitionEvent extends Event {

	private Direction direction;


	/**
	 * Gets the direction of the MapTransitionEvent
	 * @return direction of the MapTransitionEvent
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Sets the direction of the MapTransitionEvent
	 * @param direction the direction of the MapTransitionEvent
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Constructs a MapTransitionEvent
	 * @param direction the direction of the MapTransitionEvent
	 */
	public MapTransitionEvent(Direction direction) {
		this.direction = direction;
	}
}
