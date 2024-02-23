import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

import graph.City;
import graph.Coordinate;
import search.AStar;
import search.BreadthFirstSearch;
import search.DepthFirstSearch;


/**
 * Main file of the search algorithms project. Runs BFS,
 * DFS, and A* on a given set of cities. The data is hard-coded
 * but the source and sink are provided either with an input file
 * or standard input. As an output, the options are standard output
 * or an output file.
 * @author Andrew Serra
 */
public class Search extends Thread {
    private String src;
    private String dst;
    private final String outFile;
    private final HashMap<String, City> nodes = new HashMap<>();

    /**
     * Creates a Search object
     * @param inputFile Input filename containing source and sink
     * @param outputFile Output filename containing where to write paths
     */
    public Search(String inputFile, String outputFile) {
        // Set up the search data
        readCityFile();
        readEdgeFile();

        readInputFile(inputFile);
        this.outFile = outputFile;
    }

    /**
     * Static method to print how to run the program.
     */
    public static void printHelp() {
        System.out.println("Usage: java Search inputFile outputFile");
    }

    /**
     * Reads in the city.dat file containing all city nodes
     * and their information.
     */
    private void readCityFile() {
        try (
                BufferedReader input = new BufferedReader(new FileReader("city.dat")); ) {
            String line;
            while ((line = input.readLine()) != null) {
                String[] contents = line.split("\\s+");
                float x = Float.parseFloat(contents[2]);
                float y = Float.parseFloat(contents[3]);
                Coordinate coord = new Coordinate(x, y);
                this.nodes.put(contents[0], new City(contents[0], contents[1], coord));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: city.dat");
            System.exit(0);
        } catch (IOException e) {
            System.err.printf("IO exception: %s\n", e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Reads in the edge.dat file containing all city connections.
     */
    private void readEdgeFile() {
        try ( BufferedReader input = new BufferedReader(new FileReader("edge.dat")); ) {
            String line;
            while ((line = input.readLine()) != null) {
                String[] contents = line.split("\\s+");
                if(contents.length == 2) {
                    City node1 = this.nodes.get(contents[0]);
                    City node2 = this.nodes.get(contents[1]);
                    node1.connectTo(node2);
                    node2.connectTo(node1);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: edge.dat");
            System.exit(0);
        } catch (IOException e) {
            System.err.printf("IO exception: %s\n", e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Reads the input file containing the source and sink
     * node names. The file contains two city names, the first
     * is the source, the second is the sink.
     * @param filename File containing source and sink names, or "-" for
     *                 standard input.
     */
    private void readInputFile(String filename) {

        try ( Reader r = filename.equals("-") ?
                new InputStreamReader(System.in) : new FileReader(filename);
              BufferedReader input = new BufferedReader(r); ) {
            this.src = input.readLine();
            this.dst = input.readLine();

            if (!this.nodes.containsKey(this.src)) {
                System.err.printf("No such city: %s\n", this.src);
                System.exit(0);
            } else if (!this.nodes.containsKey(this.dst)) {
                System.err.printf("No such city: %s\n", this.dst);
                System.exit(0);
            }
        } catch (FileNotFoundException e) {
            System.err.printf("File not found: %s\n", filename);
            System.exit(0);
        } catch (IOException e) {
            System.err.printf("IO exception: %s\n", e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Writes to the output file solutions of each algorithm. After
     * each path, the number of hops and the total distance is given.
     * @param w Writer object to output the solution
     * @param title Name of the algorithm to be printed
     * @param path Path of the cities that connect the source and sink as
     *             a linked list.
     */
    private void printPathData(Writer w, String title, LinkedList<City> path) throws IOException {
        City prevCity = path.getFirst();
        float totalDist = 0;
        String ls = System.getProperty("line.separator");

        w.write(ls + title + " Results:" + ls);
        w.write(prevCity.getName() + ls);
        for (int i=1; i < path.size(); i++) {
            City currCity = path.get(i);
            w.write(currCity.getName() + ls);

            totalDist += currCity.distanceTo(prevCity);
            prevCity = currCity;
        }
        w.write("That took " + (path.size()-1) + " hops to find." + ls);
        w.write("Total distance = " + (int)totalDist + " miles." + ls + ls);
    }

    /**
     * Runs Breadth-First Search
     * @param w Writer object to output the result
     * @throws IOException
     */
    public void BFS(Writer w) throws IOException {
        City city = this.nodes.get(this.src);
        BreadthFirstSearch _bfs = new BreadthFirstSearch(city, this.dst);
        _bfs.run();
        LinkedList<City> path = _bfs.getPath();

        this.printPathData(w, "Breadth-First Search", path);
    }

    /**
     * Runs Depth-First Search
     * @param w Writer object to output the result
     * @throws IOException
     */
    public void DFS(Writer w) throws IOException {
        City city = this.nodes.get(this.src);
        DepthFirstSearch _dfs = new DepthFirstSearch(city, this.dst);
        _dfs.run();
        LinkedList<City> path = _dfs.getPath();

        this.printPathData(w, "Depth-First Search", path);
    }

    /**
     * Runs A* Search
     * @param w Writer object to output the result
     * @throws IOException
     */
    public void AStar(Writer w) throws IOException {
        City srcCity = this.nodes.get(this.src);
        City dstCity = this.nodes.get(this.dst);
        AStar _as = new AStar(srcCity, dstCity);
        _as.run();
        LinkedList<City> path = _as.getPath();

        this.printPathData(w, "A* Search", path);
    }

    /**
     * Runs the thread.
     */
    @Override
    public void run() {
        try ( Writer w = this.outFile.equals("-") ?
                new PrintWriter(System.out) : new FileWriter(this.outFile, true);
              BufferedWriter output = new BufferedWriter(w); ) {
            this.BFS(output);
            this.DFS(output);
            this.AStar(output);
        } catch (FileNotFoundException e) {
            System.err.printf("File not found: %s\n", this.outFile);
            System.exit(0);
        } catch (IOException e) {
            System.err.printf("IO exception: %s\n", e.getMessage());
            System.exit(0);
        }
    }

    public static void main(String[] args) {

        if(args.length != 2) {
            Search.printHelp();
            System.exit(0);
        }

        new Search(args[0], args[1]).run();
    }
}