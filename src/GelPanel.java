/*
 * GelPanel.java
 * Handle everything to display the gel on a JPanel
 *
 * Copyright (C) 2007 Jean-Etienne Poirrier
 * Under GNU GPL -- see LICENSE.txt in the archive for full details about the licence
 */

package picklisteditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Class handling everything to display a gel on a JPanel
 * @author J-E. Porrier
 */
public class GelPanel extends JPanel {
    
    private Preferences myprefs;
    private Pair<Spot> pickrefs;
    private ArrayList<Spot> spots;
    private static final int GELMAXWIDTH = 2500; // TODO: check if it's true
    private static final int GELMAXHEIGHT = 2000; // TODO: check if it's true
    private boolean gotData = false; // while we don't have data: false (to avoid painting nothing when launching app)
    
    /** Creates a new instance of GelPanel */
    public GelPanel() {
    }
    
    /**
     * Push data into the GUI; will also set gotData to true
     * @param   p   a pair of spots = picking references
     * @param   s   an arraylist of spots = all the other spots
     * @param   r   an object Preferences
     */
    public void pushData(Pair<Spot> p, ArrayList<Spot> s, Preferences r) {
        pickrefs = p;
        spots = s;
        myprefs = r;
        gotData = true;
    }
    
    /**
     * Scale from the real postion (from Spot coordinate) to the position on panel
     * @param   realPos int -- real position (from Spot coordinate)
     * @param   maxPanelSize    int -- maximum panel size (e.g. width or height)
     * @param   isWidth boolean -- true if measure width, false if measure height (for the constants above)
     * @return  int the position on the gel
     */
    private int scale(int realPos, int maxPanelSize, boolean isWidth) {
        if(isWidth)
            return(realPos * maxPanelSize / GELMAXWIDTH);
        return(realPos * maxPanelSize / GELMAXHEIGHT);
    }
    
    /**
     * Real work to draw the gel...
     * @param   g   a Graphics context
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        this.setBackground(Color.LIGHT_GRAY);
        if(gotData){
            // getting panel dimensions
            int width = getWidth();
            int height = getHeight();
            int diam = myprefs.getSpotRadius() * 2; // diameter of spot to draw
            // painting picking references
            g2.setColor(Color.YELLOW);
            // painting picking reference 1
            int tmpX = scale(pickrefs.get(0).getX(), width, true);
            int tmpY = scale(pickrefs.get(0).getY(), height, true);
            Ellipse2D e = new Ellipse2D.Float(tmpX, tmpY, diam*2, diam*2); // pickrefs size > regular spot
            g2.draw(e);
            if(myprefs.isDispRefNumber())
                g2.drawString(pickrefs.get(0).getComment(), tmpX + (diam/2) - 2, tmpY - (diam/2));
            // painting picking reference 2
            tmpX = scale(pickrefs.get(1).getX(), width, true);
            tmpY = scale(pickrefs.get(1).getY(), height, true);
            e = new Ellipse2D.Float(tmpX, tmpY, diam*2, diam*2); // pickrefs size > regular spot
            g2.draw(e);
            if(myprefs.isDispRefNumber())
                g2.drawString(pickrefs.get(1).getComment(), tmpX + (diam/2) - 2, tmpY - (diam/2));
            // painting spots
            g2.setColor(Color.BLACK);
            for(Spot s:spots) {
                tmpX = scale(s.getX(), width, true);
                tmpY = scale(s.getY(), height, true);
                e = new Ellipse2D.Float(tmpX, tmpY, diam, diam);
                g2.fill(e);
                if(myprefs.isDispSpotNumber())
                    g2.drawString(Integer.toString(s.getSpotNumber()), tmpX + (diam/2) - 2, tmpY - (diam/2));
            }
        } // else no data received yet
    }
    // Easy, isn't it? :-)
}
