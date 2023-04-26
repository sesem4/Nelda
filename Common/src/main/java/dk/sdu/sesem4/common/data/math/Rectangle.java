package dk.sdu.sesem4.common.data.math;

/**
 * <strong>Rectangle</strong>
 * <p>
 * Rectangle represents a rectangular area in the game world.
 * <p>
 * We do this with a position Vector and a size Vector
 * This class also contains the collision logic for rectangles on rectangles
 */
public class Rectangle {
	/**
	 * The position that the Rectangle starts at (bottom-left)
	 */
	private Vector2 position;
	/**
	 * The size of the Rectangle.
	 * x being width and y being height (origin at bottom-left)
	 */
	private Vector2 size;

	public Rectangle(Vector2 position, Vector2 size) {
		this.position = position;
		this.size = size;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getSize() {
		return size;
	}

	public void setSize(Vector2 size) {
		this.size = size;
	}
	
	/**
	 * Gets the x-coordinate of the left edge of the Rectangle
	 * @return x-coordinate of the left edge of the Rectangle
	 */
	public float getLeftEdge() {
		return position.getX();
	}
	
	/**
	 * Gets the x-coordinate of the right edge of the Rectangle
	 * @return x-coordinate of the right edge of the Rectangle
	 */
	public float getRightEdge() {
		return position.getX() + size.getX();
	}
	
	/**
	 * Gets the y-coordinate of the bottom edge of the Rectangle
	 * @return y-coordinate of the bottom edge of the Rectangle
	 */
	public float getBottomEdge() {
		return position.getY();
	}
	
	/**
	 * Gets the y-coordinate of the top edge of the Rectangle
	 * @return y-coordinate of the top edge of the Rectangle
	 */
	public float getTopEdge() {
		return position.getY() + size.getY();
	}
	
	/**
	 * Gets the bottom-left corner of the Rectangle
	 * @return Vector2 for the point at the bottom-left of the Rectangle
	 */
	public Vector2 getBottomLeftCorner() {
		Vector2 position = getPosition();
		Vector2 size = getSize();
		return new Vector2(
				position.getX(),
				position.getY()
		);
	}
	
	/**
	 * Gets the bottom-right corner of the Rectangle
	 * @return Vector2 for the point at the bottom-right of the Rectangle
	 */
	public Vector2 getBottomRightCorner() {
		Vector2 position = getPosition();
		Vector2 size = getSize();
		return new Vector2(
				position.getX() + size.getX(),
				position.getY()
		);
	}
	
	/**
	 * Gets the top-left corner of the Rectangle
	 * @return Vector2 for the point at the top-left of the Rectangle
	 */
	public Vector2 getTopLeftCorner() {
		Vector2 position = getPosition();
		Vector2 size = getSize();
		return new Vector2(
				position.getX(),
				position.getY() + size.getY()
		);
	}
	
	/**
	 * Gets the top-right corner of the Rectangle
	 * @return Vector2 for the point at the top-right of the Rectangle
	 */
	public Vector2 getTopRightCorner() {
		Vector2 position = getPosition();
		Vector2 size = getSize();
		return new Vector2(
				position.getX() + size.getX(),
				position.getY() + size.getY()
		);
	}
	
	/**
	 * Calculates whether two Rectangles (this and other) collide (overlap)
	 * One rectangle starting exactly where the other ends, <strong>does</strong> count as a collision.
	 * @param other The rectangle to check collision with
	 * @return Whether the rectangles collide (overlap)
	 */
	public boolean collidesWith(Rectangle other) {
		if (this.getLeftEdge()   > other.getRightEdge()) return false;
		if (this.getBottomEdge() > other.getTopEdge())   return false;
		if (other.getLeftEdge()   > this.getRightEdge()) return false;
		if (other.getBottomEdge() > this.getTopEdge())   return false;
		return true;
	}
}
