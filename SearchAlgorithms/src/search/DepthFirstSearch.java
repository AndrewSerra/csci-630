package search;

import graph.City;
import utils.CityStack;
import utils.Pair;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Runs depth first search starting from
 * the entry node provided. Searches for the name
 * of the destination city name.
 * @author Andrew Serra
 */
public class DepthFirstSearch extends SearchAlgorithmBase {
    private final HashMap<City, City> reverse = new HashMap<>();
    private final CityStack stack = new CityStack();
    private final LinkedList<City> visited = new LinkedList<>();

    /**
     * Creates a DepthFirstSearch object.
     * @param entryNode The starting City of the search.
     * @param dstName Name of the city being searched.
     */
    public DepthFirstSearch(City entryNode, String dstName) {
        this.src = entryNode;
        this.dst = dstName;
        this.stack.add(this.src, null);
    }

    /**
     * Sorts the strings in reversed order.
     * @param nextHops Children nodes of the current City object
     */
    private void sortChildren(LinkedList<City> nextHops) {
        nextHops.sort((o1, o2) -> o2.toString().compareTo(o1.toString()));
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
        while(!this.stack.isEmpty()) {
            Pair<City, City> pair = this.stack.remove();
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
                    this.stack.add(connection, _curr);
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
