/*
 * Main.java
 * Main (First) class of the application - handle the start but also the menu!
 *
 * Copyright (C) 2007 Jean-Etienne Poirrier
 * Under GNU GPL -- see LICENSE.txt in the archive for full details about the licence
 */

package picklisteditor;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

/**
 * Main class, handling the starting point and the menu too!
 * @author J-E. Porrier
 */
public class Main {
    
    private static PickList pl;
    private static String pathFile = "";
    private static GUIScreen guiScreen;
    private static ExtensionFileFilter myfilter;
    private static JFileChooser chooser;
    private static JFrame frame;
    private static JMenu menuFile, menuEdit, menuHelp;
    private static JMenuBar mymenu;
    private static JMenuItem miOpen, miSave, miSaveAs, miExit, miCopy, miInvert, miAbout;
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * Main function :-)
     * @param   args    an array of strings containing the command line arguments
     */
    public static void main(String[] args) {
        // Prevoit un job dans le thread de gestion des evenements :
        // cree et montre le GUI de cette application (comme dans tous les exemples de Sun)
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    /**
     * Create and show the application GUI
     */
    private static void createAndShowGUI() {
        // Define the Look&Feel de l'application
        UIManager.LookAndFeelInfo[] lafInfos = UIManager.getInstalledLookAndFeels();
        String defaultlaf = "javax.swing.plaf.metal.MetalLookAndFeel";
        for(int i = 0; i < lafInfos.length; i++) {
            String name = lafInfos[i].getName();
            String className = lafInfos[i].getClassName();
            if(className.contains("Windows") && !className.contains("Classic"))
                defaultlaf = className;
            if(className.contains("Mac"))
                defaultlaf = className;
        }
        try {
            UIManager.setLookAndFeel(defaultlaf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Initialise the PickList object
        pl = new PickList();
        // Create and define the display window (as in every Sun examples)
        frame = new JFrame("Picklist Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create and define the panels containing the UI elements
        guiScreen = new GUIScreen();
        guiScreen.setOpaque(true); // content containers should be opaque
        frame.setContentPane(guiScreen);
        frame.setJMenuBar(createMenu());
        // Now display the window
        frame.pack();
        frame.setLocation(20, 20);
        frame.setVisible(true);
    }
    
    /**
     * Create a menu for the application
     * @return  JMenuBar    a JMenuBar containing the menu
     */
    private static JMenuBar createMenu() {
        mymenu = new JMenuBar();
        menuFile = new JMenu("File");
        menuFile.setMnemonic('F');
        mymenu.add(menuFile);
        miOpen = new JMenuItem("Open...");
        miOpen.setMnemonic('O');
        miOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        miOpen.addActionListener(openAction);
        menuFile.add(miOpen);
        miSave = new JMenuItem("Save");
        miSave.setMnemonic('S');
        miSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        miSave.addActionListener(saveAction);
        miSave.setEnabled(false);
        menuFile.add(miSave);
        miSaveAs = new JMenuItem("Save As...");
        miSaveAs.addActionListener(saveAsAction);
        miSaveAs.setEnabled(false);
        menuFile.add(miSaveAs);
        menuFile.addSeparator();
        miExit = new JMenuItem("Exit");
        miExit.setMnemonic('X');
        miExit.addActionListener(exitAction);
        menuFile.add(miExit);
        menuEdit = new JMenu("Edit");
        mymenu.add(menuEdit);
        miCopy = new JMenuItem("Copy gel image...");
        miCopy.setMnemonic('C');
        miCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        miCopy.addActionListener(copyAction);
        miCopy.setEnabled(false);
        menuEdit.add(miCopy);
        miInvert = new JMenuItem("Invert picking references");
        miInvert.setMnemonic('I');
        miInvert.addActionListener(invertAction);
        miInvert.setEnabled(false);
        menuEdit.add(miInvert);
        menuHelp = new JMenu("Help");
        mymenu.add(menuHelp);
        miAbout = new JMenuItem("About...");
        miAbout.setMnemonic('A');
        miAbout.addActionListener(aboutAction);
        menuHelp.add(miAbout);
        // *** Menu finished
        return(mymenu);
    }
    
    private static Action openAction = new AbstractAction("open") {
        public void actionPerformed(ActionEvent event) {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            myfilter = new ExtensionFileFilter();
            myfilter.addExtension("txt");
            myfilter.setDescription("GE Healthcare pick list (*.txt)");
            chooser.setFileFilter(myfilter);
            chooser.setMultiSelectionEnabled(false);
            if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                pathFile = chooser.getSelectedFile().getPath();
                try {
                    if(pl.readGEFile(pathFile)) {
                        // Things to do if we succeed in reading the GE file given by the path
                        guiScreen.pushData(pl.getPickRefs(), pl.getSpotList(), true);
                        // enable some options
                        miSave.setEnabled(true);
                        miSaveAs.setEnabled(true);
//                        miCopy.setEnabled(true);
                        miInvert.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Not a GE Healthcare pick list file");
                    }
                } catch (HeadlessException ex) {
                    ex.printStackTrace();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } // else user didn't choose a file (Cancel or error)
        } // end of actionPerformed()
    };
    
    /**
     * Save the content of the PickList (from GUIScreen object) with a predefined name
     * To be called from saveAction and saveAsAction ONLY! :-)
     */
    private static void saveFile() {
        if(pathFile != "") {
            // ok pathFile contains something
            pl = guiScreen.getData();
            try {
                if(!pl.saveGEFile(pathFile))
                    JOptionPane.showMessageDialog(null, "File not saved!");
            } catch (HeadlessException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private static Action saveAction = new AbstractAction("save") {
        public void actionPerformed(ActionEvent event) {
            saveFile();
        }
    };
    
    private static Action saveAsAction = new AbstractAction("saveas") {
        public void actionPerformed(ActionEvent event) {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            myfilter = new ExtensionFileFilter();
            myfilter.addExtension("txt");
            myfilter.setDescription("GE Healthcare pick list (*.txt)");
            chooser.setFileFilter(myfilter);
            chooser.setMultiSelectionEnabled(false);
            if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                pathFile = chooser.getSelectedFile().getPath();
                saveFile();
            }         
        }
    };
    
    private static Action exitAction = new AbstractAction("exit") {
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    };
    
    private static Action copyAction = new AbstractAction("copy") {
        public void actionPerformed(ActionEvent event) {
            // Nothing for the moment ... TODO!
        }
    };
    
    private static Action invertAction = new AbstractAction("invert") {
        public void actionPerformed(ActionEvent event) {
            guiScreen.actionPerformed(new ActionEvent(this, 42, "invertPickRefs"));
        }
    };
    
    private static Action aboutAction = new AbstractAction("about") {
        public void actionPerformed(ActionEvent event) {
            JOptionPane.showMessageDialog(null, "<html><p>Pick List Editor v.0.2<p>&copy; Jean-Etienne Poirrier, 2007<p>http://www.poirrier.be/");
        }
    };
    
    /**
     * Tuned FileFilter class for the picklist extensions
     * Generic function for file filtering
     */
    private static class ExtensionFileFilter extends FileFilter {
        
        private String description = "";
        private ArrayList<String> extensions = new ArrayList<String>();
        
        /**
         * Add a file extension to be used by this filter
         */
        public void addExtension(String extension) {
            if(!extension.startsWith("."))
                extension = "." + extension;
            extensions.add(extension.toLowerCase());
        }
        
        /**
         * Define a description for files this filter will recognize
         * @param   desc    a description of recognized files
         */
        public void setDescription(String desc) {
            description = desc;
        }
        
        /**
         * Defines if it can accept (or not) a file
         * @return  boolean true if file is accepted; false if file is not accepted
         */
        public boolean accept(File f) {
            if(f.isDirectory())
                return true;
            String name = f.getName().toLowerCase();
            
            for(String extension : extensions)
                if(name.endsWith(extension))
                    return true;
            return false;
        }
        
        /**
         * Returns a description of recognized files
         * @return  String  a description of recognized files
         */
        public String getDescription() {
            return(description);
        }
    }
}
