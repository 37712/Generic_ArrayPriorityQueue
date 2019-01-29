
/*  CS310 Spring 2018
    Programming Assignment #1
    Carlos A. Gamino Reyes
*/

/*  The PriorityQueue ADT may store objects in any order.  However,
    removal of objects from the PQ must follow specific criteria.
    The object of highest priority that has been in the PQ longest
    must be the object returned by the remove() method.  FIFO return
    order must be preserved for objects of identical priority.
   
    Ranking of objects by priority is determined by the Comparable<E>
    interface.  All objects inserted into the PQ must implement this
    interface.
*/

package data_structures;

import java.util.Iterator;

public class UnorderedArrayPriorityQueue 
        <E extends Comparable <E>> implements PriorityQueue <E>{
    
    private E[] storage;
    private int maxSize, currentSize;
    
    public UnorderedArrayPriorityQueue(){
        this(DEFAULT_MAX_CAPACITY);
    }
    
    public UnorderedArrayPriorityQueue(int max){
	maxSize = max;
	currentSize = 0;
	storage = (E[])new Comparable[maxSize];
    }
    
    //  Inserts a new object into the priority queue.  Returns true if
    //  the insertion is successful.  If the PQ is full, the insertion
    //  is aborted, and the method returns false.
    public boolean insert(E obj){
        if(isFull()){ return false; }
        storage[currentSize++] = obj;
        return true;
    }   
   
    //  Removes the object of highest priority that has been in the
    //  PQ the longest, and returns it.  Returns null if the PQ is empty.
    public E remove(){ 
        if(isEmpty()) return null;
        E min = storage[0];
        int index = 0;
        for(int i = 1; i < currentSize; i++){
            if(storage[i].compareTo(min) < 0){
                min = storage[i];
                index = i;
            }
        }
        for(int i = index; i < currentSize-1; i++){
            storage[i] = storage[i+1];
        }
        currentSize--;
        return min; 
    }
    
    //Deletes all instances of the parameter obj from the PQ if found, and
    //returns true.  Returns false if no match to the parameter obj is found.
    public boolean delete(E obj){
        boolean flag = false;
        for(int i = 0; i < currentSize; i++){
            if(obj.compareTo(storage[i]) == 0){// if found
                for(int j = i; j < currentSize-1; j++)
                    storage[j] = storage[j+1];
                currentSize--; i--; flag = true;
            }
        }
        return flag;
    } 
   
    //  Returns the object of highest priority that has been in the
    //  PQ the longest, but does NOT remove it. 
    //  Returns null if the PQ is empty.
    public E peek(){
        if(isEmpty()) return null;
        E min = storage[0];
        for(int i = 1; i < currentSize; i++){
            if(storage[i].compareTo(min) < 0){
                min = storage[i];
            }
        }
        return min;
    }
    
    //  Returns true if the priority queue contains the specified element
    //  false otherwise.
    public boolean contains(E obj){
        for(int i = 0; i < currentSize; i++){
            if(obj.compareTo(storage[i]) == 0){
                return true;
            }
        }
        return false;
    }
   
    //  Returns the number of objects currently in the PQ.
    public int size(){ return currentSize; }
      
    //  Returns the PQ to an empty state.
    public void clear(){ currentSize = 0; }
   
    //  Returns true if the PQ is empty, otherwise false
    public boolean isEmpty(){ return currentSize == 0; }
   
    //  Returns true if the PQ is full, otherwise false.  List based
    //  implementations should always return false.
    public boolean isFull(){ return currentSize == maxSize; }
    
    //  Returns an iterator of the objects in the PQ, in no particular
    //  order.  
    public Iterator<E> iterator(){ return new IteratorHelper(); }
    
    public class IteratorHelper implements Iterator<E>{
        int iterIndex;
        
        public IteratorHelper(){ iterIndex = 0; }
        
        public boolean hasNext(){ return iterIndex < currentSize; }
        
        public E next(){
            if(!hasNext())
                    throw new RuntimeException("failed to traverse next()");
            return storage[iterIndex++];
        }
        
        public void remove(){ throw new UnsupportedOperationException(); }
		
    }
}
