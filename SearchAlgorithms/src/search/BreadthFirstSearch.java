package search;

import graph.City;
import utils.CityQueue;
import utils.Pair;

import java.util.*;

/**
 * Runs breadth first search starting from
 * the entry node provided. Searches for the name
 * of the destination city name.
 * @author Andrew Serra
 */
public class BreadthFirstSearch extends SearchAlgorithmBase {

    private final HashMap<City, City> reverse = new HashMap<>();
    private final CityQueue queue = new CityQueue();
    private final LinkedList<City> visited = new LinkedList<>();

    /**
     * Creates a BreadthFirstSearch object.
     * @param entryNode The starting City of the search.
     * @param dstName Name of the city being searched.
     */
    public BreadthFirstSearch(City entryNode, String dstName) {
        this.src = entryNode;
        this.dst = dstName;
        this.queue.add(this.src, null);
    }

    /**
     * Sorts the strings in increasing order.
     * @param nextHops Children nodes of the current City object
     */
    private void sortChildren(LinkedList<City> nextHops) {
        nextHops.sort(Comparator.comparing(City::toString));
    }

    /**
     * Creates the linked list containing all nodes forming
     * the path in reverse from sink to source.
     * @param dstNode Destination City object
     */
    private void findPath(City dstNode) {
        this.path = new LinkedList<>();
        City parentNode = dstNode;

        while (parentNode != this.src) {
            this.path.addFirst(parentNode);
            parentNode = this.reverse.get(parentNode);
        }
        this.path.addFirst(this.src);
    }

    /**
     * Executes the search of the destination city iteratively.
     */
    private void search() {
        while(!this.queue.isEmpty()) {
            Pair<City, City> pair = this.queue.remove();
            City _curr = pair.getFirst();
            City _from = pair.getSecond();
            this.reverse.put(_curr, _from);
            this.visited.add(_curr);

            LinkedList<City> connections = _curr.getConnections();
            sortChildren(connections);

            if(this.dst.equals(_curr.getName())) {
                findPath(_curr);
                break;
            }

            for (City connection : connections) {
                if (!this.visited.contains(connection)) {
                    this.queue.add(connection, _curr);
                }
            }
        }
    }

    /**
     * Runs the thread.
     */
    public void run() {
        this.search();
    }
}
