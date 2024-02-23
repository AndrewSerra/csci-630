package search;

import graph.City;

import java.util.LinkedList;

/**
 * A Base class for the search algorithms.
 * @author Andrew Serra
 */
public class SearchAlgorithmBase extends Thread {

    protected City src;
    protected String dst;
    protected LinkedList<City> path = null;

    /**
     * Returns the final path found for the two cities.
     * @return The linked list containing the path between the cities.
     */
    public LinkedList<City> getPath() {
        if(this.path == null) {
            throw new IllegalStateException("BFS has not been run yet.");
        }
        return this.path;
    }
}
