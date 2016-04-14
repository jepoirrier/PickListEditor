/*
 * Pair.java
 * Generic class to handle a pair of Objects
 * Based on Jacques Thoorens'Paire class (Java classes at ECI Liege, 2005-2006)
 *
 * Copyright (C) 2007 Jean-Etienne Poirrier
 * Under GNU GPL -- see LICENSE.txt in the archive for full details about the licence
 */

package picklisteditor;

import java.util.AbstractCollection ;
import java.util.Iterator ;

/**
 * Generic class to handle a pair of Objects
 * @author J-E. Porrier
 */
public class Pair<E> extends AbstractCollection<E> {
    
    /**
     * Number of elements in the pair
     */
    private int max = 0 ;
    /**
     * first and second contains the two elements
     */
    private E first, second ;
    
    /**
     * Creates a new instance of Pair
     */
    public void Pair() {
    }
    
    /**
     * Returns the actual number of elements in the pair
     * @return  int the number of elements in the pair
     */
    public int size() {
        return max;
    }
    
    /**
     * Add an element in the pair (unless there are already two elements)
     * @param   e   Element to be added
     * @return  true    true if addition succeeded
     */
    public boolean add(E e){
        switch(max) {
            case 0:
                first = e;
                break;
            case 1: 
                if(first.equals(e))
                    return false;
                second = e;
                break;
            default:
                return false;
        }
        max++ ;
        return true ;
    }
    
    /**
     * Implementation of iterator()
     * @return  Iterator<E> an iterator of the Pair object
     */
    public Iterator<E> iterator() {
        return new IterPair<E>();
    }
    
    /**
     * Get an element at position pos
     * @param   pos int - position of the element to retrieve
     * @return  E   reference of the element (or null if not found)
     */
    public E get(int pos){
        if(pos > -1 && pos < max)
            if(pos == 0)
                return first;
            else
                return second;
        else
            return null;
    }
    
    /**
     * Internal class to go through the Pair
     */
    private class IterPair<E> implements Iterator{

        private int next ;

        IterPair(){
            next = 0 ;
        }
        
        public boolean hasNext() {
            return next < max;
        }
        

        public Object next() {
            if(hasNext()){
                if(next++ == 0)
                    return (E)first;
                else
                    return (E)second;
            } else
                return null;
        }
        
        public void remove() {
            if(next == 1){
                first = second;
                second = null;
            } else
                second = null;
            max--;
        }
    }
}