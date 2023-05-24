package dk.sdu.sesem4.ai.smart;

import dk.sdu.sesem4.common.data.math.Vector2;
import dk.sdu.sesem4.common.util.Direction;

public class TileMover {
	public Vector2 position;
	public Vector2 target;
	
	public Direction getDirection() {
		return position.getDirectionTo(target);
	}
	
	public boolean isDone() {
		Vector2 difference = position.minus(target);
		
		float xLength = Math.abs(difference.getX());
		float yLength = Math.abs(difference.getY());
		
		float distance = xLength+yLength;
		
		return distance < 0.0001;
	}
}
