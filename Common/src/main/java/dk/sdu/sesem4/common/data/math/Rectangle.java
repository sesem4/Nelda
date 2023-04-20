package dk.sdu.sesem4.common.data.math;

public class Rectangle {
	private Vector2 position;
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

	public float getLeftEdge() {
		return position.getX();
	}

	public float getRightEdge() {
		return position.getX() + size.getX();
	}

	public float getBottomEdge() {
		return position.getY();
	}

	public float getTopEdge() {
		return position.getY() + size.getY();
	}

	public Vector2 getBottomLeftCorner() {
		Vector2 position = getPosition();
		Vector2 size = getSize();
		return new Vector2(
				position.getX(),
				position.getY()
		);
	}

	public Vector2 getBottomRightCorner() {
		Vector2 position = getPosition();
		Vector2 size = getSize();
		return new Vector2(
				position.getX() + size.getX(),
				position.getY()
		);
	}

	public Vector2 getTopLeftCorner() {
		Vector2 position = getPosition();
		Vector2 size = getSize();
		return new Vector2(
				position.getX(),
				position.getY() + size.getY()
		);
	}

	public Vector2 getTopRightCorner() {
		Vector2 position = getPosition();
		Vector2 size = getSize();
		return new Vector2(
				position.getX() + size.getX(),
				position.getY() + size.getY()
		);
	}

	public boolean collidesWith(Rectangle other) {
		if (this.getLeftEdge()   > other.getRightEdge()) return false;
		if (this.getBottomEdge() > other.getTopEdge())   return false;
		if (other.getLeftEdge()   > this.getRightEdge()) return false;
		if (other.getBottomEdge() > this.getTopEdge())   return false;
		return true;
	}
}
