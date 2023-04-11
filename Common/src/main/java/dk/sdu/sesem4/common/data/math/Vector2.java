package dk.sdu.sesem4.common.data.math;

/**
 * 2 point vector (X and Y)
 */
public class Vector2 {
	/**
	 * Vector point one in the 2D vector
	 */
	float x;
	/**
	 * Vector point two in the 2D vector
	 */
	float y;

	public Vector2() {
		this(0, 0);
	}

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
