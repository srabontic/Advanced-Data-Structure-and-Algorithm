/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandatoryproject2;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Srabonti Chakraborty SXC154030
 */
public class SkipListImpl <T extends Comparable<? super T>> implements SkipList<T>{

    
    /* skipListNode is the class which denotes the elements od a skip list*/
    
    public class skipListNode<T>{
        private T element;
        public skipListNode[] nextNodes;
         
        public skipListNode(T x, int lev) {
            this.element = x;
            this.nextNodes = new skipListNode[lev];
        }
        
    }
    
    private skipListNode header;
    private skipListNode tail;
    private int maxlevel;
    private int size;
    //public List<skipListNode<T>> prev;
    public static final Long POSINF = Long.MAX_VALUE; 
    public static final Long NEGINF = Long.MIN_VALUE;
    
    
    /* this is the default constructor of skipListNode class
       This initializes a skip list with header and tail
       The header has all the elements as negative inf and 
       tail contains positive infinity as elements
       All the nextnodes are null initially
    */
    public SkipListImpl(){
        maxlevel =10;
        size =0;
        header = new skipListNode<Long>(NEGINF, maxlevel);           //Long.valueOf(0), maxlevel);
        tail = new skipListNode<Long>(POSINF, maxlevel);
        for (int i=maxlevel-1; i>=0; i--)    {
            header.nextNodes[i] = tail;
        }
    }
    
    /*Add function adds an element to an existing skip list*/
    @Override
    public boolean add(T x) {
        
        //choice of level
        skipListNode[] prev = find(x);
        //for (skipListNode a: prev)
            //System.out.println("prev values: "+ a.element);
        
        if (prev[0].nextNodes[0].element == x){ //x already exists
            prev[0].nextNodes[0].element = x;
            return false;
        }
        else{
            int lev = choice (maxlevel);
            if (lev == 0) lev=1;
            skipListNode n = new skipListNode(x, lev);         
                    //System.out.println("lev: "+ lev);
                    for (int i=0; i< lev; i++){
                        //System.out.println("index: "+i);
                        n.nextNodes[i]= prev[i].nextNodes[i];
                        prev[i].nextNodes[i] = n;
                        size++;  
                        //System.out.println("for loop end: "+i);
                }
            }
        
        //print elemnets in 0 level
        skipListNode node = header;
        //System.out.println("node: "+node.element+"  ");
        /*while(((T)node.element).compareTo((T)tail.element) != 0){
                System.out.println("node: "+node.element);
                node = node.nextNodes[0];
            }
        System.out.println("node: "+node.element+"  ");*/
        //System.out.println("size: "+size());
        return true; 
    }
  
    /* Choice function randomly choses a level for a particualr skiplist node*/
    private int choice(int maxlevel) {
        int l =0;
        boolean b;
        //System.out.println("choice");
        while(l<maxlevel){
            Random random = new Random();
            b = random.nextBoolean();
            if (b)
                    break;
            else
                l++;
        }
        //System.out.println("return choice"+ l);
        return l;
    }

    

    /*Contains function calls the find function and returns if the skip list contains an element or not*/
    @Override
    public boolean contains(T x) {
        skipListNode[] prev = find(x);
	return (prev[0].nextNodes[0].element == x);
    }
    
    /*find function finds if an element exitsts in a skiplist and returns the previous 
      nodes which can point to the element in question
    */
    private skipListNode[] find(T x) {
        //List<skipListNode<T>> prev = new ArrayList<skipListNode<T>>();
        skipListNode[] prev = new skipListNode[maxlevel];
        skipListNode p = header;  
        for (int i = maxlevel-1; i>= 0; i--){     
            while(((T)p.nextNodes[i].element).compareTo(x) < 0){
                //System.out.println("in print");
                p = p.nextNodes[i];                           
            }
            //prev.add(i, p);
            prev[i] = p;
        }
        return prev;
    }

    
    
    
    /*Find the element at the index position x; returned value
   is added to result (0 if there is no element in that index).
   Note that the first element of the list is at index 0.*/
    @Override
    public T findIndex(int n) {
        skipListNode node = header;
        Long z =Long.valueOf(0);
        int c =0;
        while(((T)node.nextNodes[0].element).compareTo((T)tail.element) != 0){
            if(c==n){
                return (T)node.element ;
            }
            node = node.nextNodes[0];
            c++;
        }
	return (T)z;
    }

    /*Find the first element; value of returned element is added to result.*/
    @Override
    public T first() {
        skipListNode firstNode = header.nextNodes[0];
	return (T)firstNode.element;
    }

    /*Find least element that is >= x; returned value is
      added to result, or 0 if no such element*/
    @Override
    public T ceiling(T x) {
        skipListNode node = header;
        boolean found = false;
        Long z =Long.valueOf(0);
            
        while(((T)node.nextNodes[0].element).compareTo(x) <= 0){
            node = node.nextNodes[0];
            found = true;
        }

        //System.out.println("node nextnode: "+ node.nextNodes[0].element);
        
        if(((T)node.nextNodes[0].element).compareTo((T)tail.element) == 0)
            return (T)z;
        else
            return (T) node.nextNodes[0].element;
      
    }
    
    /*Find the Greatest element that is <= x, returned value is
      added to result, or 0 if no such element.*/
    @Override
    public T floor(T x) {
        skipListNode node = header;
        skipListNode prev = header;
        Long z =Long.valueOf(0);
        //while((((T)node.nextNodes[0].element).compareTo(x) < 0)){ // || ((T)node.nextNodes[0].element).compareTo((T)tail.element) != 0){
        while(((T)node.nextNodes[0].element).compareTo(x) < 0){
            prev = node;
            node = node.nextNodes[0];
        }
        //System.out.println("curr.element "+ node.element);
        //System.out.println("prev.element "+ prev.element);
        if(((T)node.element).compareTo((T)tail.element) == 0)
            return (T)z;
        else
            return (T)node.element;
    }

    /*Is the list empty?*/
    @Override
    public boolean isEmpty() {
        skipListNode node = header;
        for (int i=maxlevel-1; i>=0; i--){
            if (((T)node.nextNodes[i].element).compareTo((T)tail.element) != 0){
                return false;
            }
        }
	return true;
    }

    /*Returns an iterator for the list*/
    @Override
    public Iterator<T> iterator() {
	return new nodeIterator<>();
        //return null;
    }
     private class nodeIterator<T> implements Iterator<T> {

        int index=0;
        skipListNode s = header;
        public nodeIterator() {
            if(index<size)
                index++;
            else
                index = index;
        }
        public boolean hasNext() {
            if (index<size)
                return true;
            else
                return false;
        }
        public T next() {
	    T rv = (T)  s.nextNodes[index++];
	    return rv;
	}
        public void remove() { throw new UnsupportedOperationException(); }
    }

    /*Find the last element; value of returned element is added to result.*/
    @Override
    public T last() {
        skipListNode node = header;
        T lastNode = null;
        while(((T)node.nextNodes[0].element).compareTo((T)tail.element) != 0){
            node = node.nextNodes[0];
        }
        lastNode = (T) node.element;
        //System.out.println(lastNode);
	return lastNode;
    }

    /*Rebuild this list into a perfect skip list*/
    @Override
    public void rebuild() {
	skipListNode[] arr = new skipListNode[size];
        System.out.println("size:"+size());
        skipListNode node = header;
        //int i =0;
        /*while(((T)node.nextNodes[0].element).compareTo((T)tail.element) != 0){
            arr[i] = node;
            node = node.nextNodes[0];
        }*/
        int slSize = size();
        int k = (int) Math.log(slSize);
        //resize(arr,0,size-1,size/2);
        resize(arr,0,slSize-1,k);
        
        //create head
        skipListNode newHead;
        newHead= new skipListNode<Long>(NEGINF, k);
        //create tail
        skipListNode newTail;
        newTail= new skipListNode<Long>(POSINF, k);
        //fill the array content 
        //filling nextNodes
        int j=0;
        boolean flag = false;
        for (int i =0; i< arr.length; i++){
            int m = i+1;
            arr[i].element = i;
            while(m<arr.length){
                j=0;
                while(j<= arr[i].nextNodes.length-1 || j<=arr[m].nextNodes.length-1){
                    if (((T)arr[i].nextNodes[j].element).compareTo(null) == 0){
                        //arr[i].nextNodes[j].element = arr[m];
                        arr[i].nextNodes[j].element = m;
                        j++;
                        if(j == arr[i].nextNodes.length)
                            flag =true;
                    }
                    if (flag == true)
                            break;
                    else
                            m++;
                    
                }
                
            }
            //point to tail
            if (flag == false){
                int l = arr[i].nextNodes.length;
                arr[i].nextNodes[l-1] = newTail;
                }
        }
        //pointing head next to nodes
        for (int i=0; i< newHead.nextNodes.length; i++){
            int n =0;
            while(n< arr.length){
                if (((T)arr[n].nextNodes[i].element).compareTo(null) !=0){  // if the ith index of the nearest node has values
                    newHead.nextNodes[i].element = n;
                    break;
                }
            }
        }
        //replace index with nodes
        for (int i = arr.length-1; i>0; i++){
            arr[i].element = findIndex(i);
            for (int n = arr[i].nextNodes.length-1; n>0;n++){
                arr[i].nextNodes[n].element = findIndex((int) arr[i].nextNodes[n].element);
            }
        }
                
        //set prev of arr[0] which is header
        /*for (int m =0; m < arr[0].nextNodes.length; m++){
            newHead.nextNodes[m] = arr[0];
        }
        for (int i=1; i< arr.length; i++){
            //System.out.println(arr[i].element);
            //System.out.println(arr[i].nextNodes.length);
            for (int j =i; i>0; i--){
                
            }*/  
    }

    
    
    
    private void resize(skipListNode[] arr, int startIdx, int endIdx, int k) {
        if (startIdx <= endIdx){
            if (k==0){
                //System.out.println("in if");
                for (int i=startIdx; i<= endIdx; i++){
                    arr[i] = new skipListNode(0,0);
                }
            }
            else{
                //System.out.println("in else");
                int q = (startIdx + endIdx)/2;
                arr[q] = new skipListNode(0,k);
                resize(arr,startIdx,q-1,k-1);
                resize(arr,q+1,endIdx,k-1);
            }
        }
    }
 
    
    
    /* Remove x from this list; add 1 to result if x is in the list.*/
    @Override
    public T remove(T x) {
        
        Long z =Long.valueOf(0);
        skipListNode[] prev = find(x);
        skipListNode n = prev[0].nextNodes[0];
        if ((((T)n.element).compareTo(x) != 0)) {
            return (T)z;
        }
        else{
            for (int i = 0; i < maxlevel - 1; i++) {
                if (prev[i].nextNodes[i] == n) {
                    prev[i].nextNodes[i] = n.nextNodes[i];
                    size--;
                }
                else{
                    break;
                }
            }
            return (T) n.element;
        }
    }

    /*Number of elements in the list */
    @Override
    public int size() {
        
        int size =0;
        skipListNode node = header;
        //for (int i = maxlevel-1; i>= 0; i--){ 
            while(((T)node.nextNodes[0].element).compareTo((T)tail.element) != 0){
                node = node.nextNodes[0];
                size++;
            }
        //}
        
	return size;
    }
}
