/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandatoryproject3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author srabo
 */
public class TSP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner in;
        in = new Scanner(System.in);
        
        Graph g = Graph.readGraph(in);
        Vertex s = g.getVertex(1);
        //call TSP to get the TSP Path
        Timer timer = new Timer();
        TSPPath(g,s);
        timer.end();
        System.out.println("Time taken by TSP: "+ timer);
        
    }
    
    static void TSPPath(Graph g, Vertex s){
        //call PrimMST to get the MST
         Timer timer = new Timer();
        //store minimum edges to a list
        List<Edge> minEdges = new ArrayList<Edge>();
        //create a new graph to store the MST
        
        //decalare PQ which will store edges of the graph in the order of weight
        Vertex source = g.getVertex(1);
        PrimPQ(g, source, minEdges);
        /*for (Edge e: minEdges){
            System.out.println(e);
        }*/
        //count the number of egdes and vertices in the MST
        int numEdges = minEdges.size();
        int numVertex = g.size;
        
        Graph gMST = new Graph(numVertex);
        //create the MST 
        makeMST(gMST, minEdges);
        gMST.directed = false;
	//dfs traverse the mst 
        
        gMST.dfs(gMST.getVertex(1));
        timer.end();
        System.out.println("Time taken by TSP: "+ timer);
    }
            
    
    private static void PrimPQ(Graph g, Vertex s, List<Edge> minEdges) {
        //System.out.println("in prim mst");
        int wmst =0;
        Queue<Edge> queue = new PriorityQueue<Edge>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                //comparator will compare difference between weight of edges.
                return o1.weight - o2.weight;
            }      
        });
        Vertex tempV;
        //add adj edges of s (source) to PQ
        for(Edge e: s.adj){
            queue.add(e);
        }
        s.seen = true;
        while(queue.size() > 0){
            Edge e = queue.remove(); //remove the edge with least weight from the PQ
            if (!(e.from.isSeen() && e.to.isSeen())){
                if(e.from.isSeen()){
                    tempV = e.to;
                    e.to.seen = true;
                }else{
                    tempV = e.from;
                    e.from.seen = true;
                }
                //System.out.print(e.weight+" ");
                wmst = wmst + e.weight;
                //store the minimum edge for future reference
                minEdges.add(e); 
                //add unseen adj edges of tempV to queue 
                for (Edge e1: tempV.adj){
                    Vertex vertex = e1.otherEnd(tempV);
                    if(!vertex.seen){
                        queue.add(e1);
                    }
                }
            }
        }
        /*for (Edge e: minEdges){
            System.out.println(e.from.name+" "+e.to.name);
        }*/
    }

    private static void makeMST(Graph gMST, List<Edge> minEdges) {
        //System.out.println("in make tree");
        gMST.directed = false;
        Vertex v1;
        Vertex v2;
        for (Edge e: minEdges){
	    //gMST.addEdge(e.from, e.to, e.weight);
            v1 = gMST.getVertex(e.from.name);
            v1.seen = false;
            v2 = gMST.getVertex(e.to.name);
            v2.seen = false;
            gMST.addEdge(v1,v2, e.weight);
	}
        /*for(Vertex u: gMST) {  
            //u.seen = false;
	    for(Edge e: u.adj) { 
		Vertex v = e.otherEnd(u); 
		//System.out.print(e + " ");
	    }
	    System.out.println();
	}*/
    }
    
}
