/**
 * Class to represent a graph
 *  @author rbk
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Graph implements Iterable<Vertex> {

    //this method breaks the graph into smaller sub paths
    static List<CircularList<Vertex>> breakGraphIntoTours(Graph g) {
        
        List<CircularList<Vertex>> tourList = new ArrayList<CircularList<Vertex>>();
        
        //arraylist to store the vertex already visted in a cycle
        List<Vertex> cycleNodes;
        //start with the 1st vertex in the graph
        Vertex start; // = g.v.get(0);
        Vertex firstVertex;   // first vertex from where we have started the cycle CONSTANT for one cycle
        Vertex prev;
        Vertex next = new Vertex(0);
        //number of edges traversed in loop
        int edgeNum =0;
        Edge e;
        int position =0;
        
        //define circular list
        CircularList<Vertex> subTour;
        
        while(edgeNum < g.edgeSize){
            //System.out.println("<NEW CYCLE>");
            //System.out.println("edges traveresed: "+ edgeNum);
            //System.out.println("total edges: "+ g.edgeSize);
            
            cycleNodes = new ArrayList<Vertex>();
            
            if(edgeNum ==0){
                start = g.v.get(1);
            }
            else{
                start = getNextStartVertex(g);
            }
            //System.out.println("starting vertex: "+ start.name);
            prev = start;
            subTour = new CircularList<Vertex>();
            cycleNodes = new ArrayList<Vertex>();
            subTour.add(prev);
            //System.out.println("NExt vertex: "+ next.name);
            next = next = new Vertex(0);
            position =1;
            
            while (next.name != start.name){   //until start vertex equals to the current vertex
                //System.out.println("Prev vertex name: "+ prev.name);
                e = getAvaiableEdge(prev);    //get the available edge from the vertex
                //System.out.println("Edge returned: "+ e.from+ " to "+ e.to);
                if (prev.positionInList ==0)
                    prev.positionInList = position;
                
                edgeNum++;  //invrement the number of edges visited
                
                //find other end of the dge and add it to the cycle
                //set prev to the otherend
                next = e.otherEnd(prev);
                
                //System.out.println("prev.positionInList: "+ prev.positionInList);
                
                subTour.add(next);
                position++;
                prev = next;
            }
            
             if (next.positionInList ==0)
                    next.positionInList = position;
            
            //add the circular linked list to the list of that
            //subTour.printList();
            tourList.add(subTour);
        }
        
        return tourList;
    }

    //this method returns the edge unvisited from the vertex
    private static Edge getAvaiableEdge(Vertex vertex) {
        Edge vistedEdge;
        //System.out.println("Total edges for the vertex: "+ vertex.adjEdges);
        //System.out.println("Current edges of the vertex: "+ vertex.edgesVisited);
        //System.out.println("visited or not: "+ vertex.adj.get(vertex.edgesVisited).visited);
        while(vertex.edgesVisited < vertex.adjEdges){      //if total edges is less than number of egdes discovered
            if(vertex.adj.get(vertex.edgesVisited).visited == false){     //
                vertex.adj.get(vertex.edgesVisited).visited= true;
                vistedEdge = vertex.adj.get(vertex.edgesVisited);
                //vertex.edgesVisited++;
                //return vertex.adj.get(vertex.edgesVisited);
                return vistedEdge;
            }
            vertex.edgesVisited++;
        }
        return null;
    }

    //gets the next vertex eligible to be the start vertex of a cycle
    private static Vertex getNextStartVertex(Graph g) {
        Vertex nextVertex = null;
        int count =1;
        //System.out.println("vertex in vertex list: "+ g.getVertex(1).edgesVisited + "total edges: "+ g.getVertex(1).adjEdges );
        /*for (Vertex vr: g.v){
            //System.out.println("vertex in vertex list: "+ vr.name);
            if (vr.edgesVisited != vr.adjEdges){
                nextVertex = vr;
                //return nextVertex;
            }
        }*/
        while(count < g.size+1){
            if (g.getVertex(count).edgesVisited != g.getVertex(count).adjEdges){
                nextVertex = g.getVertex(count);
            }
            count++;
        }
        return nextVertex;
    }

    //this method stitches the sub tours and creats an Eular path
    static CircularList<Vertex> stitchTours(List<CircularList<Vertex>> listOfTours) {
        //System.out.println("<<STITCHING>>");
        //final tour
        CircularList<Vertex> eulerTour = new CircularList<Vertex>();
        //sub tour to be stitched
        CircularList<Vertex> pathToStitch;
        int listNum =0;
        boolean pathFoundFlag = false;
        for (CircularList<Vertex> subTour : listOfTours){
            
            //traverse the circular list to see if any of the node has position ==1
            listNum++;
            //Iterator<Vertex> it = subTour.iterator();
            //CircularList.Entry<Vertex> entry = (Entry<T>) subTour.header;
            //Entry<Vertex> firstHeader;
            //firstHeader = new Entry<Vertex>(subTour.header.element, subTour.header.next);
            //eulerTour = (CircularList<Vertex>)subTour.clone();
            
            CircularList<Vertex>.Entry<Vertex> firstHeader = subTour.header;
            CircularList<Vertex>.Entry<Vertex> temp1 = subTour.header;
            //System.out.println("SubTour"); subTour.printList();
            
            while(temp1.next != firstHeader){
                //System.out.println("SubTour elements: "+ temp1.next.element);
                if (temp1.next.element.positionInList == 1){   //if pisition in list is 1 for any node in the CL
                    //search the list of circular lists to find the first position of the list with same vertex
                    //takes the whole list and the position of the current CL being processed                    
                    
                    //System.out.println("positionInList=1 node: "+ temp1.next.element);
                    
                    
                    pathToStitch = getPathToStitch(listOfTours, temp1.next, listNum);
                     
                    
                    if (pathToStitch != null){
                        
                        pathFoundFlag = true;
                        subTour.setSeen();  //set the list is seen
                        
                        //System.out.print("path to stitch  ");
                        //pathToStitch.printList();
                    //header of the new list
                        CircularList<Vertex>.Entry<Vertex> secondHeader = pathToStitch.header;

                        CircularList<Vertex>.Entry<Vertex> endingNode = temp1.next.next; //ending pos in old cl
                        temp1.next.next = pathToStitch.header.next.next;
                        CircularList<Vertex>.Entry<Vertex> temp2 = pathToStitch.header.next.next;
                        CircularList<Vertex>.Entry<Vertex> prevNode = temp2;
                        while (temp2.next != secondHeader.next){
                            prevNode = temp2;
                            temp2 = temp2.next;
                            subTour.size++;
                        }
                        prevNode.next = endingNode;
                    
                    }
                }
                temp1 = temp1.next;
                //System.out.println("TMP NEXT: "+ temp1.element);
            }
            if (pathFoundFlag == true){
                eulerTour = subTour;
                break;
            }
            
        }
        return eulerTour;
    }

    private static CircularList<Vertex> getPathToStitch(List<CircularList<Vertex>> listOfTours, CircularList<Vertex>.Entry<Vertex> next, int listNum) {
        // get sub tour to stitch
        int count =0;
        for (CircularList<Vertex> subTour : listOfTours){
            count++;
            if (subTour.header.next.element == next.element){
                if(subTour.listSeen == false && listNum != count){
                    subTour.listSeen = true;
                    return subTour;
                }
            }
        }
        
        return null;
    }

    //given a tour, this method verifies if that is EulerPath or not
    static boolean verifyTour(Graph g, CircularList<Vertex> eulerPath) {
        boolean validCheck = true;
        //verify if all the egdes are valid
        //1. verify if the number of egdges are same in the graph and the path
        CircularList<Vertex>.Entry<Vertex> firstHeader = eulerPath.header;
        CircularList<Vertex>.Entry<Vertex> temp1 = eulerPath.header;
        Map<Integer, Integer> vertexEdgeCount = new HashMap<>();
        int s =0;
        int edgeCount =0;
        while (temp1.next != firstHeader){
            if (!vertexEdgeCount.containsKey(temp1.next.element.name))
                if (s ==0 || s == eulerPath.size-1)
                    vertexEdgeCount.put(temp1.next.element.name, 1);
                else
                    vertexEdgeCount.put(temp1.next.element.name, 2);
            else{
                int c = vertexEdgeCount.get(temp1.next.element.name);
                if (s ==0 || s == eulerPath.size-1)
                    vertexEdgeCount.put(temp1.next.element.name, c+1);
                else
                    vertexEdgeCount.put(temp1.next.element.name, c+2);
            }     
            
            temp1 = temp1.next;
            edgeCount++;
            s++;
        }
        //System.out.println("count: " +edgeCount +"g.edgeSize: "+ g.edgeSize);
        if (edgeCount-1 != g.edgeSize){
            validCheck = false;
            return validCheck;
        }
        
        //verify if all the verices are discovered
        
        //System.out.println("Euler Path size: "+ eulerPath.size);
        firstHeader = eulerPath.header;
        temp1 = eulerPath.header;
        
        while (temp1.next != firstHeader){
            if (temp1.next.element.adjEdges != vertexEdgeCount.get(temp1.next.element.name)){
                validCheck = false;
                break;
            }
            temp1 = temp1.next;
        }
        
        return validCheck;
    }

    //check if the graph can have Euler Path
    static boolean isGraphEularian(Graph g) {
        //boolean isEularian = true;
        //check if graph is connected
        Vertex src = g.v.get(1);
        g.bfs(src);
        for (Vertex vx: g){
            if (vx.seen == false){
                return false;
            }
        }
        //check if all edges have even deg 
        for (Vertex vx: g){
            if (vx.adj.size()%2 != 0)
                return false;
        } 
        return true;
    }

    List<Vertex> v; // vertices of graph
    int size; // number of verices in the graph
    boolean directed;  // true if graph is directed, false otherwise
    int edgeSize; //number of edges in the graph
    

    /**
     * Constructor for Graph
     * 
     * @param size
     *            : int - number of vertices
     */
    Graph(int size) {
	this.size = size;
	this.v = new ArrayList<>(size + 1);
	this.v.add(0, null);  // Vertex at index 0 is not used
	this.directed = false;  // default is undirected graph
	// create an array of Vertex objects
	for (int i = 1; i <= size; i++)
	    this.v.add(i, new Vertex(i));
    }

    /**
     * Find vertex no. n
     * @param n
     *           : int
     */
    Vertex getVertex(int n) {
	return this.v.get(n);
    }
    
    /**
     * Method to add an edge to the graph
     * 
     * @param a
     *            : int - one end of edge
     * @param b
     *            : int - other end of edge
     * @param weight
     *            : int - the weight of the edge
     */
    void addEdge(Vertex from, Vertex to, int weight) {
	Edge e = new Edge(from, to, weight);
	if(this.directed) {
	    from.adj.add(e);
            to.revAdj.add(e);
	} else {
	    from.adj.add(e);
	    to.adj.add(e);
            from.adjEdges++;    //increment total number of edges from
            to.adjEdges++;      //increment total number of edges to
            edgeSize++;         //increment total number of edges in the graph
	}
    }

    /**
     * Method to create iterator for vertices of graph
     */
    public Iterator<Vertex> iterator() {
	Iterator<Vertex> it = this.v.iterator();
	it.next();  // Index 0 is not used.  Skip it.
	return it;
    }

    // Run BFS from a given source node
    // Precondition: nodes have already been marked unseen
    public void bfs(Vertex src) {
	src.seen = true;
	src.distance = 0;
	Queue<Vertex> q = new LinkedList<>();
	q.add(src);
	while(!q.isEmpty()) {
	    Vertex u = q.remove();
	    for(Edge e: u.adj) {
		Vertex v = e.otherEnd(u);
		if(!v.seen) {
		    v.seen = true;
		    v.distance = u.distance + 1;
		    q.add(v);
		}
	    }
	}
    }

    // Check if graph is bipartite, using BFS
    public boolean isBipartite() {
	for(Vertex u: this) {
	    u.seen = false;
	}
	for(Vertex u: this) {
	    if(!u.seen) {
		bfs(u);
	    }
	}
	for(Vertex u:this) {
	    for(Edge e: u.adj) {
		Vertex v = e.otherEnd(u);
		if(u.distance == v.distance) {
		    return false;
		}
	    }
	}
	return true;
    }


    // read a directed graph using the Scanner interface
    public static Graph readDirectedGraph(Scanner in) {
	return readGraph(in, true);
    }
    
    // read an undirected graph using the Scanner interface
    public static Graph readGraph(Scanner in) {
	return readGraph(in, false);
    }
    
    public static Graph readGraph(Scanner in, boolean directed) {
	// read the graph related parameters
	int n = in.nextInt(); // number of vertices in the graph
	int m = in.nextInt(); // number of edges in the graph

	// create a graph instance
	Graph g = new Graph(n);
	g.directed = directed;
	for (int i = 0; i < m; i++) {
	    int u = in.nextInt();
	    int v = in.nextInt();
	    int w = in.nextInt();
	    g.addEdge(g.getVertex(u), g.getVertex(v), w);
	}
	in.close();
	return g;
    }

}
