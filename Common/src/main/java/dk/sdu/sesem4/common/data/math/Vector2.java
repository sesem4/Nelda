package dk.sdu.sesem4.common.data.math;

import dk.sdu.sesem4.common.util.Direction;

import java.lang.Math;

/**
 * 2-dimensional Vector (X and Y)
 */
public class Vector2 {
    float x;
    float y;
    
    /**
     * Creates a new Vector with (x,y) = (0,0)
     */
    public Vector2() {
        this(0, 0);
    }

    /**
     * Creates a new Vector with the given x and y values
     */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new Vector with magnitude 1 and the given direction
     */
    public Vector2(Direction direction) {
        this.x = 0;
        this.y = 0;
        switch (direction) {
            case UP:
                this.y = 1;
                break;
            case DOWN:
                this.y = -1;
                break;
            case LEFT:
                this.x = -1;
                break;
            case RIGHT:
                this.x = 1;
                break;
        }
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    /**
     * Add x and y of this Vector with x and y of another Vector
     * @param other the Vector to add
     * @return the resulting Vector after adding
     */
    public Vector2 plus(Vector2 other) {
        return new Vector2(this.x+other.x, this.y+other.y);
    }
    
    /**
     * Subtract x and y of another Vector from x and y of this Vector
     * @param other the Vector to subtract
     * @return the resulting Vector after subtracting
     */
    public Vector2 minus(Vector2 other) {
        return new Vector2(this.x-other.x, this.y-other.y);
    }

    /**
     * Multiply x and y by a scalar
     * @param scalar the scalar to multiply by
     * @return the resulting Vector after multiplying
     */
    public Vector2 times(float scalar) {
        return new Vector2(this.x*scalar, this.y*scalar);
    }

    /**
     * Divide x and y by a scalar
     * @param scalar the scalar for divide by
     * @return the resulting Vector after dividing
     */
    public Vector2 divide(float scalar) {
        return new Vector2(this.x/scalar, this.y/scalar);
    }

    /**
     * Convert this Vector to a direction.
     * This is done by getting the direction with the highest magnitude
     * @return the created direction
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
     * Get the direction to another Vector
     * @param other the other Vector
     * @return the direction to the other Vector
     */
    public Direction getDirectionTo(Vector2 other) {
        Vector2 deltaPosition = this.minus(other);
        return deltaPosition.toDirection();
    }


    /**
     * Check if two Vectors are equal
     * @param other the object to check equality against
     * @return true if the Vectors are equal. false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        // This is done because we override equals on Object, which can only take another Object, so we need to make sure the other is actually a Vector2
        if (!(other instanceof Vector2)) return false;
        Vector2 otherVector = (Vector2)other;
        
        float xDiff = Math.abs(this.getX() - otherVector.getX());
        float yDiff = Math.abs(this.getY() - otherVector.getY());
        
        
        // this exists because of IEEE-754 equality weirdness (Infinities, NaN, Positive and Negative Zero, etc.)
        boolean sameX = Float.compare(this.getX(), otherVector.getX()) == 0;
        boolean sameY = Float.compare(this.getY(), otherVector.getY()) == 0;
        
        float epsilon = 0.0001f;
        return (sameX || xDiff < epsilon) && (sameY || yDiff < epsilon);
    }
}
