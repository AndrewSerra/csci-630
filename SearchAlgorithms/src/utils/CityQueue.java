package utils;

import graph.City;

import java.util.LinkedList;

/**
 * A queue data structure that utilizes a linked list in the
 * background. Only add to last, and remove from first is available.
 * @author Andrew Serra
 */
public class CityQueue {

    private final LinkedList<Pair<City, City>> q = new LinkedList<>();
    private final LinkedList<City> visited = new LinkedList<>();

    /**
     * Get the size of the queue.
     * @return The number of items in the queue.
     */
    public int size() {
        return this.q.size();
    }

    /**
     * Returns true if the queue is empty.
     * @return True if queue is empty else false.
     */
    public boolean isEmpty() {
        return this.q.isEmpty();
    }

    /**
     * Appends a pair of City objects to the end of the linked list
     * if it is not already included.
     * @param city_to The City object at the start of the edge
     * @param city_from The City object at the end of the edge
     */
    public void add(City city_to, City city_from) {
        if(!visited.contains(city_to)) {
            this.q.addLast(new Pair<>(city_to, city_from));
            visited.add(city_to);
        }
    }

    /**
     * Removes the next pair in the linked list. The pair
     * contains two City objects. The first is The edge start, the
     * second is the edge end.
     * @return The pair of cities that is next in the queue.
     * @throws IllegalStateException
     */
    public Pair<City, City> remove() throws IllegalStateException {
        if(!this.q.isEmpty()) {
            return this.q.removeFirst();
        } else {
            throw new IllegalStateException("No elements in the queue.");
        }
    }
}
