/*
 * GUIEditSpot.java
 * -- NOT USED ANYMORE --
 * was supposed to be a GUI to edit spot but it's more user friendly (imho) to double-click on a cell
 *
 * Copyright (C) 2007 Jean-Etienne Poirrier
 * Under GNU GPL -- see LICENSE.txt in the archive for full details about the licence
 */

package picklisteditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class extending JPanel in order to display the GUI to edit a spot coordinates
 * @author J-E. Porrier
 */
public class GUIEditSpot extends JPanel implements ActionListener {
    
    private int spotNumber, spotX, spotY;
    // GUI elements
    private JLabel lblSpotNumber, lblSpotX, lblSpotY;
    
    /** Creates a new instance of GUIEditSpot */
    public GUIEditSpot(int s, int x, int y) {
        spotNumber = s;
        spotX = x;
        spotY = y;
    }

    public void actionPerformed(ActionEvent e) {
    }
    
}
