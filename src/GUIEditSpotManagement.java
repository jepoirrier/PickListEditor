/*
 * GUIEditSpotManagement.java
 * -- NOT USED ANYMORE --
 * was supposed to be a GUI to edit spot but it's more user friendly (imho) to double-click on a cell
 *
 * Copyright (C) 2007 Jean-Etienne Poirrier
 * Under GNU GPL -- see LICENSE.txt in the archive for full details about the licence
 */

package picklisteditor;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Class extending JPanel in order to manage and display a GUI to edit spot coordinates
 * @author J-E. Porrier
 */
public class GUIEditSpotManagement extends JPanel implements ActionListener{
    
    private int oldSpotNumber, oldX, oldY;
    private Spot newSpot;
    
    private JButton btCancel, btOK;
    private JDialog dialog;
    private JLabel titleSpot, titleX, titleY;
    private JTextField tfSpotNumber, tfX, tfY;
    
    /**
     * Creates a new instance of GUIEditSpotManagement
     * @param   s   int containing the spot number
     * @param   x   int containing the x coordinate
     * @param   y   int continaint the y coordinate
     */
    public GUIEditSpotManagement(int s, int x, int y) {
        
        oldSpotNumber = s;
        oldX = x;
        oldY = y;
        
        // GUI ...
        this.setLayout(new GridLayout(4, 2, 3, 3));
        titleSpot = new JLabel("Spot number: ");
        add(titleSpot);
        tfSpotNumber = new JTextField(String.valueOf(s), 6);
        add(tfSpotNumber);
        titleX = new JLabel("Coord. X: ");
        add(titleX);
        tfX = new JTextField(String.valueOf(x), 6);
        add(tfX);
        titleY = new JLabel("Coord. Y: ");
        add(titleY);
        tfY = new JTextField(String.valueOf(y), 6);
        add(tfY);
        
        btCancel = new JButton("Cancel");
        btCancel.setVerticalTextPosition(AbstractButton.CENTER);
        btCancel.setHorizontalTextPosition(AbstractButton.CENTER);
        btCancel.setMnemonic(KeyEvent.VK_C);
        btCancel.setActionCommand("cancel");
        btCancel.setEnabled(true);
        add(btCancel);
        
        btOK = new JButton("Validate");
        btOK.setVerticalTextPosition(AbstractButton.CENTER);
        btOK.setHorizontalTextPosition(AbstractButton.CENTER);
        btOK.setMnemonic(KeyEvent.VK_V);
        btOK.setActionCommand("validate");
        btOK.setEnabled(true);
        add(btOK);
    }
    
    /**
     * Actually show the dialog and return the (un)modified spot
     * @param   parent  the parent Component
     * @param   title   the title of the GUI frame
     * @return  a Spot
     */
    public Spot showDialog(Component parent, String title) {
        boolean ok = false;
        
        // Localise le cadre proprietaire
        Frame proprio = null;
        if(parent instanceof Frame)
            proprio = (Frame) parent;
        else
            proprio = (Frame)SwingUtilities.getAncestorOfClass(Frame.class, parent);
        
        // Si 1ere fois ou si proprio a change, cree nouvelle boite de dialogue
        if(dialog == null || dialog.getOwner() != proprio) {
            dialog = new JDialog(proprio, true);
            dialog.add(this);
            dialog.getRootPane().setDefaultButton(btOK);
            dialog.pack();
            dialog.setLocation(150, 150);
        }
        
        // definir le titre et afficher la boite de dialogue
        dialog.setTitle(title);
        dialog.setVisible(true);
        return newSpot;
    }

    /**
     * Manages the action performed (implementation of interface method)
     * @param   e   an ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if("cancel".equals(e.getActionCommand())) {
            newSpot = new Spot(oldSpotNumber, oldX, oldY); // cancelling edition, returning the old spot
            (SwingUtilities.getWindowAncestor(this)).setVisible(false);
        }
        if("validate".equals(e.getActionCommand())) {
            int newN, newX, newY;
            // checking new values
            try {
                newN = Integer.parseInt(tfSpotNumber.getText());
                newX = Integer.parseInt(tfX.getText());
                newY = Integer.parseInt(tfY.getText());
                newSpot = new Spot(newN, newX, newY);
            } catch(NumberFormatException nfe) {
                newSpot = new Spot(oldSpotNumber, oldX, oldY);
            }
            (SwingUtilities.getWindowAncestor(this)).setVisible(false);
        }
    }
}
