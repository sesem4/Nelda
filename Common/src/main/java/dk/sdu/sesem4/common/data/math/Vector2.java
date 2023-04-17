package dk.sdu.sesem4.common.data.math;

import dk.sdu.sesem4.common.util.Direction;

import java.lang.Math;

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

    /**
     * Creates a new vector with the given x and y values
     */
    public Vector2() {
        this(0, 0);
    }

/**
     * Creates a new vector with the given x and y values
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new vector with the given direction
     */
    public Vector2(Direction direction) {
        x = 0;
        y = 0;
        switch (direction) {
            case UP:
                y = 1;
                break;
            case DOWN:
                y = -1;
                break;
            case LEFT:
                x = -1;
                break;
            case RIGHT:
                x = 1;
                break;
        }
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

    /**
     * Times x and y with a scalar
     * @param scalar scalar
     * @return new vector
     */
    public Vector2 times(float scalar) {
        return new Vector2(x*scalar, y*scalar);
    }

    /**
     * Plus x and y of this vector with x and y of other vector
     * @param other the other vector
     * @return new vector
     */
    public Vector2 plus(Vector2 other) {
        return new Vector2(this.x+other.x, this.y+other.y);
    }

    /**
     * Minus x and y of this vector with x and y of other vector
     * @param other the other vector
     * @return new vector
     */
    public Vector2 minus(Vector2 other) {
        return new Vector2(this.x-other.x, this.y-other.y);
    }

    /**
     * Change the direction of the vector to the opposite direction
     * @return the direction of the vector
     */
    public Direction toDirection() {
        // get the longest part of the vector.
        float xLength = Math.abs(this.getX());
        float yLength = Math.abs(this.getY());
        if (xLength > yLength) {
            if (this.getX() > 0) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        } else {
            if (this.getY() > 0) {
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        }
    }

/**
     * Get the direction to another vector
     * @param other the other vector
     * @return the direction to the other vector
     */
    public Direction getDirectionTo(Vector2 other) {
        Vector2 deltaPosition = this.minus(other);
        return deltaPosition.toDirection();
    }


    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Vector2)) return false;
        Vector2 otherVector = (Vector2)other;

        return this.x == otherVector.x && this.y == otherVector.y;
    }
}
