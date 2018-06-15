/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandatoryproject3;

// Ver 1.0:  Starter code for Binary Heap implementation
// Author: Srabonti Chakraborty SXC154030

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BinaryHeap<T> implements PQ<T> {
    //T[] pq;
    Object[] pq;
    Comparator<T> c;
    int size; //contains the size of the heap
    /** Build a priority queue with a given array q */
    BinaryHeap(T[] q, Comparator<T> comp) {
	//pq = q;
        size = q.length;
        pq = (T[]) new  Object[size+1];
        for (int i=0; i< size; i++)
            pq[i+1] = q[i];
        c = comp;
	buildHeap();
    }

    /** Create an empty priority queue of given maximum size */
    BinaryHeap(int n, Comparator<T> comp) { /* to be implemented */
        pq = (T[]) new Object[n + 1];
        c = comp;
        size =0;
    }

    public void insert(T x) {
	add(x);
    }

    public T deleteMin() {
	return remove();
    }

    public T min() { 
	return peek();
    }

    public void add(T x) { 
        pq[size+1] = x;
        //pq[0]=x;
        //int s = size;
        percolateUp(size+1);
        //size++;
        ++size;
    }

    public T remove() { 
        T rv = (T)pq[1];
        pq[1] = pq[size];
        percolateDown(1);
        size--;   
	return rv;
    }

    public T peek() {
	return (T)pq[1];
    }

    /** pq[i] may violate heap order with parent */
    void percolateUp(int i) {
        T x = (T)pq[i];
        while(i > 1 && c.compare(x, (T)pq[i/2]) < 0){
            pq[i] = pq[i/2];
            i = i/2;
        }
        pq[i] = x;
    }

    /** pq[i] may violate heap order with children */
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
                hole = schild;
            }
        } 
        pq[hole] = x;
    }

    /** Create a heap.  Precondition: none. */
    void buildHeap() {
        for (int i = (size) / 2; i > 0; i--) {
            percolateDown(i);
        }
    }

    /* sort array A[1..n].  A[0] is not used. 
       Sorted order depends on comparator used to buid heap.
       min heap ==> descending order
       max heap ==> ascending order
     */
    public static<T> void heapSort(T[] A, Comparator<T> comp) { 
        BinaryHeap<T> binHeap = new BinaryHeap<>(A.length, comp);
        for (int i = 0; i < A.length; i++) {
            binHeap.add(A[i]);
	}
        for (int i = 0; i < A.length; i++) {
            System.out.print(binHeap.remove() + " ");
	}
    }
    
    public static void main(String args[]) {
		Comparator<Integer> comp = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) {
                            //if (Integer.valueOf(x) == null)
                            return x.intValue() - y.intValue();
			}
		};
                Scanner in;
                in = new Scanner(System.in);
                List<Integer> a = new ArrayList<Integer>();
                System.out.println("Enter number of elements ");
                int count = in.nextInt();
                for (int i=0; i< count; i++){
                    a.add(in.nextInt());
                }   
                Integer[] A = new Integer[a.size()];
                int c=0;
                for (Integer i: a){
                    A[c] = i;
                    c++;
                }
                //call heapsort method for the input array
		heapSort(A, comp);
	}
}

