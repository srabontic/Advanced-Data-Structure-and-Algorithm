
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author srabo
 */
public class CircularList <T> implements Iterable<T>  {
    
    //index to store first occurrence of a node in a subpath
    //Map<Integer, Integer> stitchMap = new HashMap<Integer, Integer>();

    public class Entry<T> {
        T element;
        Entry<T> next;
        Entry(T x, Entry<T> nxt) {
            element = x;
            next = nxt;
        }
    }
    
    Entry<T> header, tail;
    int size;
    boolean listSeen;
    
    CircularList() {
        header = new Entry<>(null, null);
        tail = header;
        size = 0;
    }
    
    
    //public static void main(String[] args) {
        // TODO code application logic here
    //}

    @Override
    public Iterator<T> iterator() {
        return new SLLIterator<>(header); 
    }
    
    private class SLLIterator<E> implements Iterator<E> {
        
            Entry<E> cursor, prev;
            SLLIterator(Entry<E> head) {
                cursor = head;
                prev = null;
            }

            @Override
            public boolean hasNext() {
                return cursor.next != header;
            }

            @Override
            public E next() {
                prev = cursor;
                cursor = cursor.next;
                return cursor.element;
            }

            public void remove() {
                prev.next = cursor.next;
                prev = null;
            }

        
    
    }
        // Add new elements to the end of the list
        void add(T x) {
            tail.next = new Entry<>(x, header);
            tail = tail.next;
            size++;
        }
        
        void printList() {
	for(T item: this) {
	    System.out.print(item + " ");
	}
	System.out.println();
    }
         void setSeen(){
            listSeen = true;
        }
    
}
