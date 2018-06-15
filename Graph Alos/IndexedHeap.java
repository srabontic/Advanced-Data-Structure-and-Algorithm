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
// Ver 1.0:  Starter code for Indexed heaps

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {
    /** Build a priority queue with a given array q */
    //decalre the index as an arraylist
    //ArrayList<Integer> indexKeys; 
    
    IndexedHeap(T[] q, Comparator<T> comp) {
	super(q, comp);
        //putIndex of each pq element
        //indexKeys = new ArrayList<Integer>(pq.length);
        for (int i=0; i< pq.length; i++){
            //indexKeys.get(i+1).putIndex(i+1);
            //super.pq[i+1].putIndex(i+1);
            ((T)super.pq[i+1]).putIndex(i+1);
        }
    }

    /** Create an empty priority queue of given maximum size */
    IndexedHeap(int n, Comparator<T> comp) {
	super(n, comp);
    }

    /** restore heap order property after the priority of x has decreased */
    void decreaseKey(T x) {
	percolateUp(x.getIndex());
    }

    /** pq[i] may violate heap order with parent 
     modified the index for Indexed binary heap*/
    void percolateUp(int i) {
        //IndexedPQNode x = IndexedPQNode.class.cast(pq[i]);
        T x = (T) pq[i];
        while(i > 1 && c.compare((T) x, (T)pq[i/2]) < 0){
            pq[i] = pq[i/2];
            ((T)pq[i]).putIndex(i);  //put the index value of the array element
            i = i/2;
        }
        pq[i] = (T) x;
        ((T)pq[i]).putIndex(i);  //put the index value of the array element
    }
    
    /** pq[i] may violate heap order with children 
        modified the index for Indexed binary heap*/
    void percolateDown(int i) { 
        T x = (T)pq[i];
        int hole =i;
        int schild;
        while(2* hole  <= size){
            //schild = 2*hole;
            //if (2*i >= size)
              //  break;
            //else{
                if(2*hole == size){
                    schild = 2*hole;
                }
                else if(c.compare((T)pq[2*hole], (T)pq[2*hole +1]) <=0){
                    schild = 2*hole;
                    }
                    else{
                    schild = 2*hole +1;   
                    }
            //}
            if (c.compare(x, (T)pq[schild]) <= 0) {
                break;
            }else{
                pq[hole] = pq[schild];
                ((T)pq[hole]).putIndex(hole);   //put the index value of the array element
                hole = schild;
            }
        } 
        pq[hole] = x;
        ((T)pq[hole]).putIndex(hole); //put the index value of the array element
    }
    
    public static void main(String args[]) {
        Comparator<Vertex> comp = new Comparator<Vertex>() {
                @Override
                public int compare(Vertex o1, Vertex o2) {
                    return o1.name - o2.name;
                }
            };
       
        Scanner in;
        in = new Scanner(System.in);
        
        Graph g = Graph.readGraph(in);
      
        Timer timer = new Timer();
        IndexedHeap heap = new IndexedHeap(g.size, comp);
        for (Vertex vertex : g) {
                heap.add(vertex);
                //System.out.println(heap);
        }
        for (Vertex vertex : g) {
                System.out.print(heap.remove() + " ");
        }
        System.out.println();
        timer.end();
        System.out.println("Time taken by Heap sort using Indexed PQ: "+ timer);
          
    }
    

}
