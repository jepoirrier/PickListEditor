/*
 * SpotsTableModel.java
 * -- NOT USED ANYMORE --
 * was supposed to be tuned table model for the spots (but I abandonned this way since I didn't understand the TableCOLUMNModel! :-(
 *
 * Copyright (C) 2007 Jean-Etienne Poirrier
 * Under GNU GPL -- see LICENSE.txt in the archive for full details about the licence
 */

package picklisteditor;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Tuned Table Model Class
 * @author J-E. Porrier
 */
public class SpotsTableModel extends AbstractTableModel {
    
    private ArrayList<Spot> spots;
    
    /**
     * Creates a new instance of SpotsTableModel
     * @param   s   an ArrayList of Spots
     */
    public SpotsTableModel(ArrayList<Spot> s) {
        this.spots = new ArrayList<Spot>();
        this.spots = s;
    }

    /**
     * Returns the number of rows in the table
     * @return  int the number of rows in the table
     */
    public int getRowCount() {
        return(spots.size());
    }

    /**
     * Returns the number of columns in the table
     * @return  int the number of columns in the table
     */
    public int getColumnCount() {
        return(3); // for spots, there are only 3 columns: number, x and y
    }

    /**
     * Returns an int for something in a cell at a specified position
     * Implements AbstractModel getValueAt()
     * @param   rowIndex    int containing the index of the row
     * @param   columnIndex int containing the index of the column
     * @return  Object  an oject that is, in fact, an int (spot number, x or y)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case(1):
                return(spots.get(rowIndex-1).getSpotNumber());
            case(2):
                return(spots.get(rowIndex-1).getX());
            case(3):
                return(spots.get(rowIndex-1).getY());
        }
        return(-1);
    }
    
    /**
     * Returns if a cell is editable or not (given its coordinates)
     * @param   r   int containing the row number of the cell
     * @param   c   int containing the column number of the cell
     * @return  boolean true if the cell is editable; false otherwise
     */
    public boolean isCellEditable(int r, int c) {
        return(true);
    }
}
