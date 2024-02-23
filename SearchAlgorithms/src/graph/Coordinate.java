package graph;

/**
 * Coordinate storage
 * @author Andrew Serra
 */
public class Coordinate {
    private final float x;
    private final float y;
    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter method for the x value.
     * @return X coordinate as a float
     */
    public float getX() {
        return x;
    }

    /**
     * Getter method for the y value.
     * @return Y coordinate as a float
     */
    public float getY() {
        return y;
    }

    /**
     * Representation of the coordinates as a string.
     * @return String representation of coordinates.
     */
    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", this.x, this.y);
    }
}
