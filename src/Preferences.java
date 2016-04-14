/*
 * Preferences.java
 * Class for the storage of the general ... preferences :-)
 *
 * Copyright (C) 2007 Jean-Etienne Poirrier
 * Under GNU GPL -- see LICENSE.txt in the archive for full details about the licence
 */

package picklisteditor;

/**
 * Preference class is there to store ... general preferences for the app
 * @author J-E. Porrier
 */
public class Preferences {
    
    private boolean dispRefNumber;
    private boolean dispSpotNumber;
    private int spotRadius, srMin, srMax;
    
    /** Creates a new instance of Preferences */
    public Preferences() {
        dispRefNumber = true;
        dispSpotNumber = false;
        spotRadius = 3;
        srMin = 1;
        srMax = 10;
    }
    
    /**
     * Returns wether picking references should display their numbers of not (= get)
     * @return  boolean true if pickrefs should displey their numbers
     */
    public boolean isDispRefNumber() {
        return dispRefNumber;
    }
    
    /**
     * Defines wether picking references should display their number or not (= set)
     * @param   choice  boolean indicating if pickrefs number should be displayed
     */
    public void dispRefNumber(boolean choice) {
        dispRefNumber = choice;
    }
    
    /**
     * Invert the display status of the References numbers
     */
    public void toggleDispRefNumber() {
        dispRefNumber = !dispRefNumber;
    }
    
    /**
     * Returns wether spots should display their numbers of not (= get)
     * @return  boolean true if spots should displey their numbers
     */
    public boolean isDispSpotNumber() {
        return dispSpotNumber;
    }
    
    /**
     * Defines wether spots should display their number or not (= set)
     * @param   choice  boolean indicating if spots number should be displayed
     */
    public void dispSpotNumber(boolean choice) {
        dispSpotNumber = choice;
    }
    
    /**
     * Invert the display status of the Spots numbers
     */
    public void toggleDispSpotNumber() {
        dispSpotNumber = !dispSpotNumber;
    }
    
    /**
     * Returns the spot radius (in pixels)
     * @return  int the spot radius in pixels
     */
    public int getSpotRadius() {
        return spotRadius;
    }
    
    /**
     * Defines the spot radius (in pixels)
     * @param   size the spot radius in pixels (integer)
     */
    public void setSpotRadius(int size) {
        spotRadius = size;
        if(spotRadius > srMax)
            spotRadius = srMax;
        if(spotRadius < srMin)
            spotRadius = srMin;
    }
    
    /**
     * Returns the maximum size for a spot radius (in pixels)
     * @return  int the maximum size for a spot radius in pixels
     */
    public int getMaxSpotRadius() {
        return srMax;
    }
    
    /**
     * Returns the minimum size for a spot radius (in pixels)
     * @return  int the minimum size for a spot radius in pixels
     */
    public int getMinSpotRadius() {
        return srMin;
    }
}
