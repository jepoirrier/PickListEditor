/*
 * GUIScreen.java
 * Display the main screen of the application (the JPanel only, actually)
 *
 * Copyright (C) 2007 Jean-Etienne Poirrier
 * Under GNU GPL -- see LICENSE.txt in the archive for full details about the licence
 */

package picklisteditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class organising all the GUI component on an extended-from-JPanel for the main screen
 * @author J-E. Porrier
 */
public class GUIScreen extends JPanel implements ActionListener {
    
    private Preferences myprefs;
    private Pair<Spot> pickrefs;
    private ArrayList<Spot> spots;
    private boolean gotData = false; // true if data is read from Open
    // table elements
//    private Object[][] dummyCells = {{" "," "," "}, {" "," "," "}, {" "," "," "}, {" "," "," "}, {" "," "," "}};
    private String[] columnNames = {"Spot", "x", "y"};
    // GUI elements
    private Border bordEtched, bordTitled;
    private GelPanel gelPanel;
    private GridBagLayout gb;
    private GridBagConstraints ct;
    private JButton btRevalidateTable, btInvertRefs;
    private JCheckBox cbDispRefNumber, cbDispSpotNumber;
    private JLabel lblSpotRadius;
    private JPanel paramPanel, tablePanel;
    private JSpinner spinSpotSize;
    private JTable tblCoord;
    
    /** Creates a new instance of GUIScreen with all the GUI components */
    public GUIScreen() {
        // start preferences
        myprefs = new Preferences();
        // GUI elements
        gelPanel = new GelPanel();
        gelPanel.setPreferredSize(new Dimension(634, 668));
        bordTitled = BorderFactory.createTitledBorder(bordEtched, "Gel");
        gelPanel.setBorder(bordTitled);
        // *** paramPanel (GridBagLayout)
        paramPanel = new JPanel();
        bordEtched = BorderFactory.createEtchedBorder();
        bordTitled = BorderFactory.createTitledBorder(bordEtched, "Controls");
        paramPanel.setBorder(bordTitled);
        cbDispRefNumber = new JCheckBox("Display picking references numbers");
        cbDispRefNumber.setSelected(myprefs.isDispRefNumber());
        cbDispRefNumber.setActionCommand("toggleRefNumber");
        cbDispRefNumber.setMnemonic(KeyEvent.VK_P);
        cbDispRefNumber.addActionListener(this);
        cbDispSpotNumber = new JCheckBox("Display spot numbers");
        cbDispSpotNumber.setSelected(myprefs.isDispSpotNumber());
        cbDispSpotNumber.setActionCommand("toggleSpotNumber");
        cbDispSpotNumber.setMnemonic(KeyEvent.VK_S);
        cbDispSpotNumber.addActionListener(this);
        lblSpotRadius = new JLabel("Spot Radius:");
        spinSpotSize = new JSpinner(new SpinnerNumberModel(myprefs.getSpotRadius(), myprefs.getMinSpotRadius(), myprefs.getMaxSpotRadius(), 1));
        spinSpotSize.addChangeListener(new SpinnerListener());
        btInvertRefs = new JButton("Invert picking references");
        btInvertRefs.setActionCommand("invertPickRefs");
        btInvertRefs.setMnemonic(KeyEvent.VK_I);
        btInvertRefs.addActionListener(this);
        gb = new GridBagLayout();
        paramPanel.setLayout(gb);
        ct = new GridBagConstraints();
        ct.insets = new Insets(3, 3, 3, 3);
        ct.anchor = ct.WEST;
        ct.weightx = 100;
        ct.weighty = 100;
        ct.gridx = 0;
        ct.gridy = 0;
        ct.gridwidth = 1;
        ct.gridheight = 1;
        paramPanel.add(cbDispRefNumber, ct);
        ct.gridy = 1;
        paramPanel.add(cbDispSpotNumber, ct);
        ct.gridx = 1;
        ct.gridy = 0;
        ct.anchor = ct.CENTER;
        paramPanel.add(lblSpotRadius, ct);
        ct.gridy = 1;
        paramPanel.add(spinSpotSize, ct);
        ct.gridx = 2;
        ct.gridy = 1;
        ct.fill = ct.CENTER;
        ct.gridy = 1;
        paramPanel.add(btInvertRefs, ct);
        // *** paramPanel finished
        // *** tablePanel
        tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(250, 500));
        bordTitled = BorderFactory.createTitledBorder(bordEtched, "Pick list");
        tablePanel.setBorder(bordTitled);
        btRevalidateTable = new JButton("Revalidate Table");
        btRevalidateTable.setActionCommand("revalidateTableChanges");
        btRevalidateTable.setMnemonic(KeyEvent.VK_R);
        btRevalidateTable.setEnabled(false);
        btRevalidateTable.addActionListener(this);
        tablePanel.setLayout(new BorderLayout(3, 3));
        tablePanel.add(btRevalidateTable, BorderLayout.SOUTH);
        // *** tablePanel finished
        // now add everything to a BorderLayout
        this.setLayout(new BorderLayout(10, 10));
        this.add(gelPanel, BorderLayout.CENTER);
        this.add(paramPanel, BorderLayout.SOUTH);
        this.add(tablePanel, BorderLayout.EAST);
        // disable elements not modifiable without data
        cbDispRefNumber.setEnabled(false);
        cbDispSpotNumber.setEnabled(false);
        spinSpotSize.setEnabled(false);
        btInvertRefs.setEnabled(false);
    }
    
    /**
     * Push data into the GUI; will also set gotData to true
     * DO NOT CALL when just a control has been changed; call controlsChanged() instead!
     * @param   p   a pair of spots = picking references
     * @param   s   an arraylist of spots = all the other spots
     * @param   n   a boolean indicating if it's new data (from Open) or not
     */
    public void pushData(Pair<Spot> p, ArrayList<Spot> s, boolean n) {
        pickrefs = p;
        spots = s;
        gelPanel.pushData(p, s, this.myprefs);
        gelPanel.repaint();
        if(!gotData || n) {
            // previously, we didn't have any data
            // 1st time: enable the control panel
            cbDispRefNumber.setEnabled(true);
            cbDispSpotNumber.setEnabled(true);
            spinSpotSize.setEnabled(true);
            btInvertRefs.setEnabled(true);
            // 1st time or new data: we have to display a new table
            if(gotData){ // previous data existed so ...
                tablePanel.removeAll(); // ... a table was previously there and had to be removed
                tablePanel.add(btRevalidateTable, BorderLayout.SOUTH); // I don't see how to do it without removing the button too :-(
                // TODO: I guess I can avoid removeAll() (and avoid removing the button) by having a variable for JScrollPane and removing it only
            }
            tblCoord = new JTable(getSpotsInATable(), columnNames);
            tablePanel.add(new JScrollPane(tblCoord), BorderLayout.CENTER);
            tablePanel.validate(); // read the documentation before wondering why it didn't add your table (above)!
            btRevalidateTable.setEnabled(true);
        }
        gotData = true;
    }
    
    /**
     * get the data managed by the JPanel as a PickList object
     * @return  PickList object
     */
    public PickList getData() {
        return(new PickList(pickrefs, spots));
    }
    
    /**
     * Transforms the data from the ArrayList<Spot> into an Object[][] suitable for a JTable
     * (just because I don't understand the TableColumnModel, not yet)
     * @return  Object[][]  an Object[][] suitable for a JTable (as main data)
     */
    public Object[][] getSpotsInATable() {
        String[][] sTable = new String[spots.size()][3];
        for(int i = 0; i < spots.size(); i++) {
            sTable[i][0] = Integer.toString(spots.get(i).getSpotNumber());
            sTable[i][1] = Integer.toString(spots.get(i).getX());
            sTable[i][2] = Integer.toString(spots.get(i).getY());
        }
        return(sTable);
    }
    
    /**
     * Indicates to the application (and gelPanel) that a control changed something in the appearance of the spots
     * To be called when a <b>control</b> has been changed: will update gel display if data already there
     * If you need to indicate that the actual content of the picklist changed, use function pushData()
     */
    public void controlsChanged() {
        if(gotData)
            pushData(pickrefs, spots, false);
    }
    
    /**
     * Manages the action performed (implementation of interface method)
     * @param   e   an ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        // Action if toggles the display of reference numbers
        if("toggleRefNumber".equals(e.getActionCommand())) {
            myprefs.toggleDispRefNumber();
            cbDispRefNumber.setSelected(myprefs.isDispRefNumber());
            controlsChanged();
        }
        // Action if toggles the display of spot numbers
        if("toggleSpotNumber".equals(e.getActionCommand())) {
            myprefs.toggleDispSpotNumber();
            cbDispSpotNumber.setSelected(myprefs.isDispSpotNumber());
            controlsChanged();
        }
        // Action if pushes on the Invert Picklist button (main code to invert spot here!)
        if("invertPickRefs".equals(e.getActionCommand())) {
            // find the mirror point on the x-axis
            int mirror = (pickrefs.get(0).getX() + pickrefs.get(1).getX()) / 2;
            // redefine the pickrefs
            Pair<Spot> p2 = new Pair<Spot>();
            int tmp = pickrefs.get(0).getX();
            if(tmp < mirror)
                tmp = mirror + (mirror - tmp);
            else
                tmp = mirror - (tmp - mirror);
            p2.add(new Spot(0, tmp, pickrefs.get(0).getY(), pickrefs.get(1).getComment()));
            tmp = pickrefs.get(1).getX();
            if(tmp < mirror)
                tmp = mirror + (mirror - tmp);
            else
                tmp = mirror - (tmp - mirror);
            p2.add(new Spot(0, tmp, pickrefs.get(1).getY(), pickrefs.get(0).getComment()));
            // Now the spots
            ArrayList<Spot> s2 = new ArrayList<Spot>();
            for(Spot s:spots) {
                tmp = s.getX();
                if(tmp < mirror)
                    tmp = mirror + (mirror - tmp);
                else
                    tmp = mirror - (tmp - mirror);
                s2.add(new Spot(s.getSpotNumber(), tmp, s.getY()));
            } // end of spots
            pushData(p2, s2, true);
        }
        // Action if clicked on the Revalidate Table data button after user changed table content (or not)
        if("revalidateTableChanges".equals(e.getActionCommand())) {
            int max = tblCoord.getRowCount();
            if(max > 0) {
                ArrayList<Spot> newSpots = new ArrayList<Spot>();
                int newN, newX, newY;
                for(int i = 0; i < max; i++) {
                    try {
                        newN = Integer.parseInt((String)tblCoord.getValueAt(i, 0));
                        newX = Integer.parseInt((String)tblCoord.getValueAt(i, 1));
                        newY = Integer.parseInt((String)tblCoord.getValueAt(i, 2));
                        newSpots.add(new Spot(newN, newX, newY));
                    } catch (NumberFormatException nfe) {
                        // catched an error
                    }
                } // for finished, parsing table completed
                pushData(pickrefs, newSpots, true);
            } // else if max =< 0: do nothing because there is nothing in the table!
        }
    }
    
    /**
     * Tuned ChangeListener class for the JSpinner
     */
    private class SpinnerListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            myprefs.setSpotRadius((Integer)spinSpotSize.getValue());
            spinSpotSize.setValue(myprefs.getSpotRadius()); // mandatory for the min/max checking
            pushData(pickrefs, spots, false);
        }
    }
    
}
