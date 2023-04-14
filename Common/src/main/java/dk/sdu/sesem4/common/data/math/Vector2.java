package dk.sdu.sesem4.common.data.math;

import dk.sdu.sesem4.common.util.Direction;

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

    public Vector2(Direction direction) {
        x = 0;
        y = 0;
        switch (direction) {
            case UP -> y = 1;
            case DOWN -> y = -1;
            case LEFT -> x = -1;
            case RIGHT -> x = 1;
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

    public Vector2 times(float a) {
        return new Vector2(x*a, y*a);
    }

    public Vector2 plus(Vector2 other) {
        return new Vector2(this.x+other.x, this.y+other.y);
    }

    public Vector2 minus(Vector2 other) {
        return new Vector2(this.x-other.x, this.y-other.y);
    }
}
