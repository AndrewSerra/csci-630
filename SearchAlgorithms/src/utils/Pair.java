package utils;

/**
 * A simple data structure to store a pair of objects that
 * do not share the same type. It is immutable.
 *
 * @author Andrew serra
 * @param <L> Type of the first element
 * @param <R> Type of the second element
 */
public class Pair<L, R> {

    private final L left;
    private final R right;

    /**
     * Creates a Pair object
     * @param left Object to store in the first location
     * @param right Object to store in the second location
     */
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Getter method for first location
     * @return The object stored in the left
     */
    public L getFirst() { return this.left; }

    /**
     * Getter method for second location
     * @return The object stored in the right
     */
    public R getSecond() { return this.right; }
}
