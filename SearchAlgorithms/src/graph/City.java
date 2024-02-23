package graph;

import java.util.LinkedList;

/**
 * A City object that is considered as a node in a graph. All
 * edges get connected into a graph once constructed.
 * @author Andrew Serra
 */
public class City {

    private final String state;
    private final String name;

    private final Coordinate coords;
    private final LinkedList<City> connections = new LinkedList<>();

    /**
     * Creates a City object.
     * @param name String of the name of the city
     * @param state String of the name of the state
     * @param coords Coordinate object of the coordinates
     */
    public City(String name, String state, Coordinate coords) {
        this.name = name;
        this.state = state;
        this.coords = coords;
    }

    /**
     * Creates a bidirectional edge between two nodes.
     * @param node City object to create an edge with
     */
    public void connectTo(City node) {
        if(!this.connections.contains(node)) {
            this.connections.add(node);
        } else {
            System.out.printf(
                    "Connection exists: %s <-> %s\n", this, node);
        }
    }

    /**
     * Getter method for the city name.
     * @return The city name as a string
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for the state name.
     * @return The state name as a string
     */
    public String getState() {
        return state;
    }

    /**
     * Getter method for the coordinate object
     * @return The Coordinate object for the City object
     */
    public Coordinate getCoords() {
        return coords;
    }

    /**
     * Getter method for nodes that are
     * connected to this node.
     * @return The linked list of nodes with edges to this node
     */
    public LinkedList<City> getConnections() {
        return this.connections;
    }

    /**
     * Checks if a City is connected to this node,
     * @param node A City object
     * @return True if they have an edge
     */
    public boolean isConnected(City node) {
        return this.connections.contains(node);
    }

    /**
     * Calculates the Euclidean distance to another node.
     * @param node A City object
     * @return The Euclidean distance between two nodes.
     */
    public float distanceTo(City node) {
        if(node == null) {
            return 0;
        }
        Coordinate extCoords = node.getCoords();

        float lat = (float) Math.pow(this.coords.getY() - extCoords.getY(), 2);
        float lon = (float) Math.pow(this.coords.getX() - extCoords.getX(), 2);

        return (float) Math.round(Math.sqrt(lat + lon) * 100);
    }

    /**
     * Checks if an object is type of City, returns true if
     * city name and state name match the object's.
     * @param obj Any object trying to be compared.
     * @return True is state and city name match.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof City) {
            boolean isNameMatching = this.name.equals(((City)obj).getName());
            boolean isStateMatching = this.state.equals(((City)obj).getState());
            return isNameMatching && isStateMatching;
        } else {
            return false;
        }
    }

    /**
     * Representation of the City object.
     * @return String representation of City "city-name, state".
     */
    @Override
    public String toString() {
        return this.name + ", " + this.state;
    }
}
