
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author srabo
 */
public class EulerPath  {

    public static void main(String[] args) throws FileNotFoundException {
        
        Scanner in;
        //in = new Scanner(System.in);
        
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        
        Graph g = Graph.readGraph(in);
        
        //print input graph:
        System.out.println("Input Graph:");
        for(Vertex u: g) {
	    System.out.print(u + ": ");
	    for(Edge e: u.adj) {
		Vertex v = e.otherEnd(u);
		System.out.print(e + " ");
	    }
	    System.out.println();
	}
        
        //check if the Graph is Eularian
        boolean isEularian = Graph.isGraphEularian(g);
        if (isEularian == true)
            System.out.println("The given graph is Eulerian");
        else
            System.out.println("The given graph is not Eulerian");
        //list of sub-trails
        List<CircularList<Vertex>> listOfTours;
        listOfTours = Graph.breakGraphIntoTours(g);
        //stitch sub tours
        CircularList<Vertex> eulerPath;
        eulerPath = Graph.stitchTours(listOfTours);
        System.out.println("The actual tour is:");
        eulerPath.printList();
        //verifies if the tour is Euler Path
        boolean eulerTourInd = Graph.verifyTour(g, eulerPath);
        if (eulerTourInd == true)
            System.out.println("The given tour is a valid Eulerian Path ");
        else
            System.out.println("The given tour is not a valid Eulerian Path");
           
    }
    
}
