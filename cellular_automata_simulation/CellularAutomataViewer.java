package cellular_automata_simulation;

import cellular_automata_simulation.cellular_automata.*;
import cellular_automata_simulation.Reaction.*;
import cellular_automata_simulation.wire_world.*;
import cellular_automata_simulation.Life.*;
import cellular_automata_simulation.cellular_automata_view.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import javax.imageio.*;
import javax.swing.*;

public class CellularAutomataViewer implements KeyListener, ActionListener {
    /**
     * Entry point for stand alone.
     * 
     * @param args Command line arguments 
     */
    public static void main(String[] args) {
        new CellularAutomataViewer();
    }

    //Simulation properties 
    private CellularAutomataHandler CAHandle; // Current CellularAutomata Handler
    private boolean pause;                   // is the simulation paused or not. True = paused, False = running. defualts to paused
    
    //GUI properties
    private JCheckBoxMenuItem jmiPause;      //
    private JFileChooser fc;                 //
    private Timer t;                         //
    private GridViewer viewer;               //
    private ButtonGroup typeGroup;           //

    private CelluarAutomataGroup[] caList;   //
    private CelluarAutomataGroup current;    //   

    /**
     * Constructor 
     */
    public CellularAutomataViewer() {
        
        //Set Up Cellular Automata base data
        caList = new CelluarAutomataGroup[3];
        caList[0] = new CelluarAutomataGroup(new ConwayLife(), new ConwayLifeColorHandler(), ConwayLife.handler, ConwayLife.fileHandler);
        caList[1] = new CelluarAutomataGroup(new WireWorld(), new WireWorldColorHandler(), WireWorld.handler, WireWorld.fileHandler);
        caList[2] = new CelluarAutomataGroup(new ReactionDiffusion(1,0.5f,0.055f,0.062f), new ReactionDiffusionColorHandler(), ReactionDiffusion.handler, ReactionDiffusion.fileHandler);
        
        //Set Up initital Cellular Automata and its viewer
        current = caList[0];        
        CellularAutomataColorHandler cach = current.ch;
        Grid g = new Grid(50, 50, new Random(), current.sh);
        CAHandle = new CellularAutomataHandler(current.ca, g);
        viewer = new GridViewer(g, current.ch);
        
        //Set up JFrame
        JFrame frame = new JFrame("Celluar Automata");
        frame.setContentPane(viewer);
        frame.setVisible(true);
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        
        //set up Frame timer;
        t = new Timer(200, this);
        t.setActionCommand("Update");
        t.start();
        pause = true;
        
        //set up menu bar
        typeGroup = new ButtonGroup();
        JMenuBar menuBar = new JMenuBar();
        JMenu jmFile, jmEdit, jmType, jmSeed;
        menuBar.add(jmFile = new JMenu("File"));
        jmFile.add(new JMenuItem("Load"));
        jmFile.add(new JMenuItem("Save"));
        jmFile.add(new JMenuItem("Exit"));
        menuBar.add(jmEdit = new JMenu("Edit"));
        jmEdit.add(jmType = new JMenu("Set Type"));
        for (CelluarAutomataGroup cag : caList) {
            jmType.add(cag.button);
        }
        typeGroup.setSelected(current.button.getModel(), true);
        jmEdit.add(jmiPause = new JCheckBoxMenuItem("Pause"));
        jmiPause.setState(true);
        jmEdit.add(new JMenuItem("Step"));
        jmEdit.add(jmSeed = new JMenu("Seed"));
        jmSeed.add(new JMenuItem("Random"));
        jmSeed.add(new JMenuItem("Image"));
        jmSeed.add(new JMenuItem("Checkered"));
        frame.setJMenuBar(menuBar);
        
        //for each High level menu 
        for (JMenu jm : new JMenu[]{jmFile, jmEdit, jmType, jmSeed}) {
            //for each component in that menu 
            for (Component jmi : jm.getMenuComponents()) {
                //add this as a listener if appropriate  
                if (jmi instanceof JMenuItem) {

                    ((JMenuItem) jmi).addActionListener(this);
                }
                //set up Radio Button groupings
                if (jmi instanceof JRadioButtonMenuItem) {
                    typeGroup.add((AbstractButton) jmi);
                }
            }
        }
        
        //init JFileChooser.
        fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
    }
    
    //Hold over from KeyListener
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //Hold over from KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && pause) {
            CAHandle.step();
            viewer.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            pause = !pause;
            jmiPause.setState(pause);
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            viewer.showGrid(!viewer.gridShowing());
            viewer.repaint();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Step":
                if (pause) {
                    CAHandle.step();
                    viewer.repaint();
                }
                break;
            case "Update":
                if (!pause) {
                    CAHandle.update(1);
                    viewer.repaint();
                }
                break;
            case "Pause":
                pause = !pause;
                jmiPause.setState(pause);
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Random":
                Grid g = CAHandle.getGrid();
                int[] dims = getNewGridSize(g);
                g.seed(dims[0], dims[1], new Random(), CAHandle.getCellularAutomata().getStateHandler());
                viewer.repaint();
                break;
            case "Checkered":
                g = CAHandle.getGrid();
                dims = getNewGridSize(g);
                String s = JOptionPane.showInputDialog(null, "Enter a Checker Size", "Celluar Automata", JOptionPane.QUESTION_MESSAGE);
                if (s != null && !s.isEmpty()) {
                    try {
                        int cSize = Integer.parseInt(s);
                        g.seed(dims[0], dims[1], cSize, CAHandle.getCellularAutomata().getStateHandler());
                        viewer.repaint();
                    } catch (Throwable E) {
                        JOptionPane.showMessageDialog(null, "Invalid Number", "Celluar Automata", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "Image":
                g = CAHandle.getGrid();
                try {
                    int returnVal = fc.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        g.seed(ImageIO.read(fc.getSelectedFile()), CAHandle.getCellularAutomata().getStateHandler());
                        viewer.repaint();
                    }
                } catch (IOException ex) {;
                }

                break;

            case "Save":
                try {
                    int returnVal = fc.showSaveDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        FileHandler.saveFile(file, CAHandle);
                    }
                } catch (FileNotFoundException ex) {
                }
                break;
            case "Load":
                try {
                    int returnVal = fc.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        CAHandle = FileHandler.loadFile(file);
                        setTypeSelected(CAHandle.getCellularAutomata().getClass(), false);
                        viewer.setGrid(CAHandle.getGrid());
                        viewer.repaint();
                    }
                } catch (FileNotFoundException ex) {
                }
                break;
            default:
                for (CelluarAutomataGroup cag : caList) {
                    if (e.getSource().equals(cag.button)) {
                        cag.SetSelected(true);
                        return;
                    }
                }
                System.out.println("Unhandled Command " + e.getActionCommand());
        }
    }

    /**
     * Gets user input for a new grid size using Input Dialogs. 
     * 
     * @param g The grid to get new sizes for. Only used to get default data
     * @return An array of 2 integers 
     *  the first is the width of the new grid 
     *  the second is the height of the new grid
     *  if an Invalid input is given the Original size for the grid is returned
     */
    private int[] getNewGridSize(Grid g) {
        try {
            String wString = JOptionPane.showInputDialog(null, "Enter a Grid Width", "Celluar Automata", JOptionPane.QUESTION_MESSAGE);
            int w = Integer.parseInt(wString);
            String hString = JOptionPane.showInputDialog(null, "Enter a Grid Hieght", "Celluar Automata", JOptionPane.QUESTION_MESSAGE);
            int h = Integer.parseInt(hString);
            return new int[]{w, h};
        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Invalid Input", "Celluar Automata", JOptionPane.ERROR_MESSAGE);
            return new int[]{g.getWidth(), g.getHeight()};
        }

    }
    
    /**
     * Sets the Type of Celluar Automata Currently Active
     * 
     * @param Type the class of the Celluar Automata Group to set as selected
     * @param reSeed if the Celluar Automata should reinitialize the grid.
     */
    private void setTypeSelected(Class Type, boolean reSeed) {
        for (CelluarAutomataGroup cag : caList) {
            if (cag.ca.getClass().equals(Type)) {
                cag.SetSelected(reSeed);
                break;
            }
        }
    }

    /**
     * A class for grouping all the Viss
     */
    private class CelluarAutomataGroup {

        public CellularAutomata ca;
        public CellularAutomataColorHandler ch;
        public StateGroup sh;
        public FileHandler fh;
        public JRadioButtonMenuItem button;
        public String name;

        public CelluarAutomataGroup(CellularAutomata ca, CellularAutomataColorHandler ch, StateGroup sh, FileHandler fh) {
            this.ca = ca;
            this.ch = ch;
            this.sh = sh;
            this.fh = fh;
            this.name = fh.getName();
            button = new JRadioButtonMenuItem(name);
        }

        public void SetSelected(boolean reSeed) {
            if(current == this){
                return;
            }
            current = this;
            typeGroup.setSelected(button.getModel(), true);
            CAHandle.setCelluarAutomata(ca);
            viewer.setColorHandler(ch);
            viewer.repaint();
            if (reSeed) {
                Grid g = CAHandle.getGrid();
                g.seed(g.getWidth(), g.getHeight(), new Random(), current.sh);
            }
        }
    }
}
