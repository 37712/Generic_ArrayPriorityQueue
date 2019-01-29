
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

public class OrderedArrayPriorityQueue 
        <E extends Comparable <E>> implements PriorityQueue <E>{
    
    private E[] storage;
    private int maxSize, currentSize;
    
    public OrderedArrayPriorityQueue(){
        this(DEFAULT_MAX_CAPACITY);
    }
    
    public OrderedArrayPriorityQueue(int max){
	maxSize = max;
	currentSize = 0;
	storage = (E[])new Comparable[maxSize];
    }
    
    //  Inserts a new object into the priority queue.  Returns true if
    //  the insertion is successful.  If the PQ is full, the insertion
    //  is aborted, and the method returns false.
    public boolean insert(E obj){
        if(isFull()){ return false; }
        int index = BinarySearchLow(obj, 0, currentSize-1);
        for(int i = currentSize; i > index; i--) storage[i] = storage[i-1];
        storage[index] = obj;
        currentSize++;
        return true;
    }
    
    //  binary search that returns the lowest index of element
    private int BinarySearchLow(E obj, int low, int high){
        if(low > high){ return low; }
        int mid = low + ((high - low) / 2);
        if(obj.compareTo(storage[mid]) >= 0)// go left
            return BinarySearchLow(obj, low, mid - 1);
        else// go right
            return BinarySearchLow(obj, mid + 1, high);
    }
    
    //  binary search returns index+1 of the highest priority element
    private int BinarySearchHigh(E obj, int low, int high){
        if(low > high){ return low; }
        int mid = low + (high - low) / 2;
        if(obj.compareTo(storage[mid]) > 0)// go left
            return BinarySearchHigh(obj, low, mid-1);
        else// go right
            return BinarySearchHigh(obj, mid+1, high);
    }
    
    //  Removes the object of highest priority that has been in the
    //  PQ the longest, and returns it.  Returns null if the PQ is empty.
    public E remove(){
        if(isEmpty()) return null;
        return storage[--currentSize];
    }
    
    //  Deletes all instances of the parameter obj from the PQ if found, and
    //  returns true.  Returns false if no match to the parameter obj is found.
    public boolean delete(E obj) {
        int index = BinarySearchLow(obj, 0, currentSize - 1);
        int j = BinarySearchHigh(obj, 0, currentSize - 1)-index;
        if (j == 0) return false;
        for(int i = index; i < currentSize - 1; i++)
            storage[i] = storage[i+j];
        currentSize = currentSize-j;
        return true;
    }
   
    //  Returns the object of highest priority that has been in the
    //  PQ the longest, but does NOT remove it. 
    //  Returns null if the PQ is empty.
    public E peek(){
        if(isEmpty()) return null;
        return storage[currentSize-1];
    }
    
    //  Returns true if the priority queue contains the specified element
    //  false otherwise.
    public boolean contains(E obj){
        return BooleanBinarySearch(obj); 
    }
    
    //  finds object by using binary search, returns true if found
    //  will work even with empty array/list
    public boolean BooleanBinarySearch(E obj){
        int lo = 0, mid, hi = currentSize-1;//storage.length-1;
        while (hi >= lo){// searching
            mid = (lo + hi) / 2;
            // found???
            if(obj.compareTo(storage[mid]) == 0) return true;
            // go to right half
            else if(obj.compareTo(storage[mid]) < 0) lo = mid+1;
            // go to left half
            else hi = mid-1;
        }
        return false;// not found
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
    public Iterator<E> iterator(){
        return new IteratorHelper();
    }
    
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

