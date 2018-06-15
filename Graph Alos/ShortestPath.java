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

/* Ver 1.0: Starter code for Dijkstra's Shortest path algorithm */

import java.util.Scanner;
import java.lang.Comparable;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Comparator;


public class ShortestPath {
    static final int Infinity = Integer.MAX_VALUE;

    static void DijkstraShortestPaths(Graph g, Vertex s)
    {
        // Code for Dijkstra's algorithm needs to be written
	// Shortest paths will be stored in fields d,p in the Vertex class
        
        //Initialization
        for (Vertex v: g){
            v.seen = false;
            v.d = Infinity;
            v.p = null;
        }
        s.d =0;   //before starting with s set its d to zero
        //declare an IndexedHeap with u.d as priority
        IndexedHeap<Vertex> indxQueue = new IndexedHeap<Vertex>(g.size, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o1.d - o2.d;
            }         
        });
        for (Vertex v: g){
           indxQueue.add(v);
        }
        while(indxQueue.size >0){
            Vertex u = indxQueue.remove();
            u.seen = true;
            for (Edge e : u.adj){
                 Vertex v = e.otherEnd(u);
                 if (!v.seen && u.d+e.weight < v.d){
                     v.d = u.d + e.weight;
                     v.p = u;
                     indxQueue.decreaseKey(v);  
                 }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }

	Graph g = Graph.readGraph(in);
	int src = in.nextInt();
	int target = in.nextInt();
        Vertex s = g.getVertex(src);
	Vertex t = g.getVertex(target);
        Timer timer = new Timer();
        DijkstraShortestPaths(g, s);
        timer.end();
        System.out.println("Time taken by Dijkstra's: "+ timer);

	System.out.println(t.d);
    }
}
