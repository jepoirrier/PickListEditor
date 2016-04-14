/*
 * PickList.java
 * Class handling a picklist. For the moment, a picklist contains a pair of picking references and an ArrayList of Spots
 *
 * Copyright (C) 2007 Jean-Etienne Poirrier
 * Under GNU GPL -- see LICENSE.txt in the archive for full details about the licence
 */

package picklisteditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Class handling a complete GE Healthcare picklist
 * @author J-E. Porrier
 */
public class PickList {
    
    private Pair<Spot> pickrefs = new Pair<Spot>();
    private ArrayList<Spot> spots = new ArrayList<Spot>();
    
    /** Creates a new instance of PickList - Default constructor */
    public PickList() {
    }
    
    /**
     * PickList constructor where picking references and spots are given
     * @param   p   a Pair object containing (2) Spots (which are the picking references)
     * @param   s   an ArrayList of Spots
     */
    public PickList(Pair<Spot> p, ArrayList<Spot> s) {
        pickrefs = p;
        spots = s;
    }
    
    /**
     * Read a GE Healthcare pick list file
     * @return  boolean true if it succeeded in reading the file
     */
    public boolean readGEFile(String path) throws FileNotFoundException, IOException {
        BufferedReader br;
        boolean answer = false; // by default, it fails
        String thisLine;
        
        // reset a previous picklist
        pickrefs = new Pair<Spot>();
        spots = new ArrayList<Spot>();
        
        br = new BufferedReader(new FileReader(path));
        // testing if first line (exists and) is from a GE Healthcare pick list
        if((thisLine = br.readLine()) != null) {
            if(thisLine.compareToIgnoreCase("Spot\tCoords (Pixels)\tComment") != 0)
                return false; // No need to continue if not this on 1st line
        }
        // we can continue: 1st line passed the test: we have a GEHealthcare file :-)
        while((thisLine = br.readLine()) != null) {
            // we got a line, it's ok to process
            String res[] = thisLine.split("\u0009");
            if(res.length == 3) {
                if(res[0].compareTo("0") == 0) {
                    // Picking references are car. by Spot = 0 and Comment = IRx
                    // Here we only test on Spot = 0
                    Spot s = new Spot(Integer.parseInt(res[0]), getXY(res[1], true), getXY(res[1], false), res[2]);
                    if(!pickrefs.add(s))
                        System.err.println("Error adding picking reference: " + s.toString());
                }
            } else {
                // regular spots (= non pickrefs) have only 2 parts
                Spot s = new Spot(Integer.parseInt(res[0]), getXY(res[1], true), getXY(res[1], false));
                spots.add(s);
            }
        } // file finished
        answer = true;
        br.close();
        
        return answer;
    }
    
    /**
     * Save a GE Healthcare pick list file
     * @param   path    a String containing the full path to the file
     * @return  boolean true if it succeeded in saving the file
     */
    public boolean saveGEFile(String path) throws IOException {
        BufferedWriter bw;
//        String encoding = System.getProperty("file.encoding", "ISO-8859-1");
//        String lineSep = System.getProperty("line.separator", "\r\n");
        boolean answer = false; // by default, it fails
        String thisLine;
        
//        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), encoding));
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
        
        try {
            bw.write("Spot\tCoords (Pixels)\tComment\r\n");
            bw.write("0\t" + pickrefs.get(0).getX() + " , " + pickrefs.get(0).getY() + "\t" + "IR1\r\n");
            bw.write("0\t" + pickrefs.get(1).getX() + " , " + pickrefs.get(1).getY() + "\t" + "IR2\r\n");
            for(Spot s: spots) {
                bw.write(s.getSpotNumber() + "\t" + s.getX() + " , " + s.getY() + "\t" + s.getComment() + "\r\n");
            }
        } finally {
            bw.close();
        }
        answer = true;
        return answer;
    }
    
    /**
     * Split the middle part of column 1 on " , " and return x or y
     * @param   s   the string to parse (the middle part of column 1)
     * @param   pos true if it returns x ; false for y
     * @return  int the x value
     */
    private int getXY(String s, boolean pos) {
        //0	166 , 882	IR1
        String res[] = s.split(" , ");
        if(pos)
            return Integer.parseInt(res[0]);
        else
            return Integer.parseInt(res[1]);
    }
    
    /**
     * Gives the Pair object containing the picking references
     * @return  Pair<Spot>  a Pair object containing the picking references
     */
    public Pair<Spot> getPickRefs() {
        return pickrefs;
    }

    /**
     * Gives the ArrayList containing all the Spots (not picking references)
     * @return  ArrayList<Spot> an ArrayList of Spot objects
     */
    public ArrayList<Spot> getSpotList() {
        return spots;
    }
}
