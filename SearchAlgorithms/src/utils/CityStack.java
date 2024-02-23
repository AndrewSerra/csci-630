package utils;

import graph.City;

import java.util.LinkedList;

/**
 * A stack data structure that utilizes a linked list in the
 * background. Only add to last, and remove from last is available.
 * @author Andrew Serra
 */
public class CityStack {
    private final LinkedList<Pair<City, City>> s = new LinkedList<>();
    private final LinkedList<City> visited = new LinkedList<>();

    /**
     * Get the size of the stack.
     * @return The number of items in the stack.
     */
    public int size() {
        return this.s.size();
    }

    /**
     * Returns true if the stack is empty.
     * @return True if stack is empty else false.
     */
    public boolean isEmpty() {
        return this.s.isEmpty();
    }

    /**
     * Appends a pair of City objects to the end of the linked list
     * if it is not already included.
     * @param city_to The City object at the start of the edge
     * @param city_from The City object at the end of the edge
     */
    public void add(City city_to, City city_from) {
        if(!visited.contains(city_to)) {
            this.s.addLast(new Pair<>(city_to, city_from));
            visited.add(city_to);
        }
    }

    /**
     * Removes the last pair added to the linked list. The pair
     * contains two City objects. The first is The edge start, the
     * second is the edge end.
     * @return The pair of cities that was last traversed.
     * @throws IllegalStateException
     */
    public Pair<City, City> remove() throws IllegalStateException {
        if(!this.s.isEmpty()) {
            return this.s.removeLast();
        } else {
            throw new IllegalStateException("No elements in the queue.");
        }
    }
}
