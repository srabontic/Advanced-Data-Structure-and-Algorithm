/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandatoryproject3;

/**
 *
 * @author Srabonti Chakraborty SXC154030
 */

/* Ver 1.0: Starter code for Prim's MST algorithm */

import java.util.Scanner;
import java.lang.Comparable;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MST {
    static final int Infinity = Integer.MAX_VALUE;

    /*
     * The main method of MST takes a graph as input
     * Seta the first vertex as the source vertex
     * Calls Prim's method which calculates the total weight of a MSt of the given graph
    */
    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            //File inputFile = new File("g4-big.txt");
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

	Graph g = Graph.readGraph(in);
        for(Vertex u: g) {
	    System.out.print(u + ": ");
	    for(Edge e: u.adj) {
		Vertex v = e.otherEnd(u);
		System.out.print(e + " ");
	    }
	    System.out.println();
	}
        /* Set the verytex 1 as source vertex*/
        Vertex s = g.getVertex(1);
        
        /* Call PrimMST to calculate the total weight of MST*/
        System.out.println(PrimMST(g, s));
    }
    
    /* PrimMST calculates weight of the MST of the given graph
     * 1) using Java's Priority Queue
     * 2) using User defined Indexed Priority Queue
     * It returns the minimum weight of MST
     * It also shows the time taken by each of the MST methods
    */
    static int PrimMST(Graph g, Vertex s)
    {
        int wmst = 0;
        int wmst_i = 0;      //total weight of Prim's with Indexed Binary Heap
        
        //Calls Prim's using Java's priority queues with priority queue of edges
        Timer timer = new Timer();
        wmst = PrimMSTPriorityQueue(g,s) ;
        timer.end();
        System.out.println("Min Weight by Java PQ MST: "+ wmst);
        System.out.println("Time taken by Java PQ MST: "+ timer);

        //Prim's using user defined Indexed priority queues with priority queue of vertices
        Timer timer1 = new Timer();
        wmst_i = PrimMSTIndexedPriorityQueue(g,s) ;
        timer1.end();
        System.out.println("Min Weight by Indexded PQ MST: "+ wmst_i);
        System.out.println("Time taken by Indexded PQ MST: "+ timer1);
           
        return wmst;
    }

    /*Prim's using Java's priority queues with priority queue of edges
    * The comparator is defined to compare the weight of edges
    */
    private static int PrimMSTPriorityQueue(Graph g, Vertex s) {
        int wmst =0;
        //decalare PQ which will store edges of the graph in the order of weight
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
                //add unseen adj edges of tempV to queue 
                for (Edge e1: tempV.adj){
                    Vertex vertex = e1.otherEnd(tempV);
                    if(!vertex.seen){
                        queue.add(e1);
                    }
                }
            }
        }
        return wmst;
    }

    /* Prim's MST for user defined Indexed Priority Queue
     * The comparator is defined to compare the weight of vertex
    */
    private static int PrimMSTIndexedPriorityQueue(Graph g, Vertex s) {
        int wmst=0;
        //initialization of vertices
        // set vertex.d (weight of min edge connecting u t some node S = infinity
        // set v.p (otherEnd of the minimum edge) = null;
        for (Vertex v: g){
            v.seen = false;
            v.d = Infinity;
            v.p = null;
        }
        s.d =0;   //before starting with s set its d to zero
        IndexedHeap<Vertex> indxQueue = new IndexedHeap<Vertex>(g.size, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o1.d - o2.d;
            }         
        });
        //Vertex tempV;
        for (Vertex v: g){
           indxQueue.add(v);
        }
        while(indxQueue.size >0){
            Vertex u = indxQueue.remove();
            u.seen = true;
            wmst += u.d;
            for (Edge e : u.adj){
                Vertex v = e.otherEnd(u);
                if (!v.seen && e.weight < v.d){
                    v.d = e.weight;
                    v.p = u;
                    indxQueue.percolateUp(v.getIndex());
                }
            }
        }
        return wmst;
    }
    
}

