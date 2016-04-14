/*
 * Spot.java
 * Class to handle a spot characteristics
 *
 * Copyright (C) 2007 Jean-Etienne Poirrier
 * Under GNU GPL -- see LICENSE.txt in the archive for full details about the licence
 */

package picklisteditor;

/**
 * Class to handle a spot characteristics
 * @author J-E. Porrier
 */
public class Spot implements Comparable {
    
    private int n, x, y;
    private String comment;
    
    /**
     * Creates a new instance of Spot without any comment
     * @param   n   int - the spot number
     * @param   x   int - the x coordinate
     * @param   y   int - the y coordinate
     */
    public Spot(int n, int x, int y) {
        this.n = n;
        this.x = x;
        this.y = y;
        this.comment = "";
    }
    
    /**
     * Creates a new instance of Spot with a comment
     * @param   n   int - the spot number
     * @param   x   int - the x coordinate
     * @param   y   int - the y coordinate
     * @param   comment String - a comment on the spot
     */
    public Spot(int n, int x, int y, String comment) {
        this.n = n;
        this.x = x;
        this.y = y;
        this.comment = comment;
    }
    
    /**
     * Return the spot number
     * @return  int the spot number
     */
    public int getSpotNumber() {
        return n;
    }
    
    /**
     * Return the X coordinate associated with the spot
     * @return  int the X coordinate
     */
    public int getX() {
        return x;
    }
 
    /**
     * Return the Y coordinate associated with the spot
     * @return  int the Y coordinate
     */
    public int getY() {
        return y;
    }
    
    /**
     * Return the comment associated with the spot
     * @return  String  the comment associated with the spot
     */
    public String getComment() {
        return comment;
    }
    
    public String toString() {
        return("n: " + n + " - x: " + x + " - y: " + y + " - " + comment);
    }

    /**
     * Implements the compareTo methods of the interface Comparable
     * @param   o   an Object that MUST be of type Spot (will be casted)
     * @return  int ; value = 1 if this object spot number is bigger, 0 if equal and -1 if this object spot number is lower
     */
    public int compareTo(Object o) {
        Spot s = (Spot)o;
        if(this.n > s.getSpotNumber())
            return(1);
        if(this.n == s.getSpotNumber())
            return(0);
        return(-1); // by default -- !!! a bit tricky !!!
    }
}
