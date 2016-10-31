/**
 * Class to represent a vertex of a graph
 * @author rbk
 *
 */

import java.util.List;
import java.util.ArrayList;

public class Vertex  {
    int name; // name of the vertex
    boolean seen; // flag to check if the vertex has already been visited
    int distance; // distance of vertex from a source
    List<Edge> adj, revAdj; // adjacency list; use LinkedList or ArrayList
    int edgesVisited =0; //number of edges alreday visited
    int adjEdges =0; //total number of edges for the vertex
    int positionInList = 0; //this holds the position of the vertex in any of the lists

    /**
     * Constructor for the vertex
     * 
     * @param n
     *            : int - name of the vertex
     */
    Vertex(int n) {
	name = n;
	seen = false;
	distance = Integer.MAX_VALUE;
	adj = new ArrayList<Edge>();
	revAdj = new ArrayList<Edge>();   /* only for directed graphs */
        positionInList = 0;
        //number of edges visited
        
    }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
	return Integer.toString(name);
    }
}
