package dk.sdu.sesem4.common.event;

public class MapTransitionEvent extends Event {

	private Direction direction;

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public MapTransitionEvent(Direction direction) {
		this.direction = direction;
	}
}
