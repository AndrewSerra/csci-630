package search;

import graph.City;
import utils.CityQueue;
import utils.Pair;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Runs A* search starting from the entry node
 * provided. Searches for the name of the destination
 * city name.
 * @author Andrew Serra
 */
public class AStar extends SearchAlgorithmBase {
    private final City dst;
    private final HashMap<City, Pair<City, Float>> reverse = new HashMap<>();
    private final CityQueue queue = new CityQueue();
    private final LinkedList<City> visited = new LinkedList<>();

    /**
     * Creates a AStar object.
     * @param entryNode The starting City of the search.
     * @param dstName Name of the city being searched.
     */
    public AStar(City entryNode, City dstName) {
        this.src = entryNode;
        this.dst = dstName;
        this.queue.add(this.src, null);
    }

    /**
     * Sorts the strings in increasing order depending on the f value.The
     * f value is defined as f(n) = g(n) + h(n) where g(n) is the current
     * distance travelled, and h(n) is the distance to the destination.
     * @param nextHops Children nodes of the current City object
     */
    private void sortChildren(City c, LinkedList<City> nextHops) {
        nextHops.sort((c1, c2) -> {
            float f1 = c.distanceTo(c1) + this.dst.distanceTo(c1);
            float f2 = c.distanceTo(c2) + this.dst.distanceTo(c2);
            return (int) (f1 - f2);
        });
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
            parentNode = this.reverse.get(parentNode).getFirst();
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
            float dist = _curr.distanceTo(_from) + (
                    this.reverse.get(_from) != null ?
                            this.reverse.get(_from).getSecond() : 0);
            this.reverse.put(_curr, new Pair<>(_from, dist));
            this.visited.add(_curr);

            LinkedList<City> connections = _curr.getConnections();
            sortChildren(_curr, connections);

            if(this.dst == _curr) {
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
