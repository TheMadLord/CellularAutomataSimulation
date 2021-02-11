package cellular_automata_simulation.cellular_automata;

import java.io.*;
import java.util.*;

/**
 * A class with functionality for loading and saving cellular automata states to file
 */
public abstract class FileHandler {
    
    /**
     * A map of cellular automata names to their file handlers
     * used to tell what cellular automata to use to parse a file.
     */
    private final static HashMap<String, FileHandler> nameReg = new HashMap<>();
    /**
     * A map of cellular automata classes to their file handlers
     * used to tell what cellular automata to use to write to a file
     */
    private final static HashMap<Class, FileHandler> classReg = new HashMap<>();
    
    /**
     * Registers a FileHandler with load and save functions
     * 
     * @param fh the File Handler to register
     */
    public final static void Register(FileHandler fh) {
        nameReg.put(fh.getName(), fh);
        classReg.put(fh.getHandledClass(), fh);
    }

    /**
     * Loads a grid sets up a CellularAutomataHandler with the right cellular Automata
     * Cellular Automata are selected based on the getName() function
     * @param file The file to read from.
     * @return  A initialized CellularAutmataHandler
     * @throws FileNotFoundException if the file does not exist
     */
    public final static CellularAutomataHandler loadFile(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        String ca = scan.nextLine().trim();
        FileHandler fh = nameReg.get(ca);
        if (fh != null) {
            return fh.parse(scan);
        }
        return null;
    }

    /**
     * Loads a grid sets up a CellularAutomataHandler with the right cellular Automata
     * Cellular Automata are selected based on the getName() function
     * 
     * @param fileName the name of the file to be read from
     * @return 
     * @throws FileNotFoundException if the file does not exist
     */
    public final static CellularAutomataHandler loadFile(String fileName) throws FileNotFoundException {
        return loadFile(new File(fileName));
    }

    /**
     * Saves the state of a Cellular Automata grid state to a file.
     * 
     * @param file the file to save to.
     * @param cah the Cellular Automata Handler to save to file
     * @throws FileNotFoundException if the file does not exist and could not be made.
     */
    public static void saveFile(File file, CellularAutomataHandler cah) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        FileHandler fh = classReg.get(cah.getCellularAutomata().getClass());
        if (fh != null) {
            writer.println(fh.getName());
            fh.write(writer,cah);
        }else{
            System.out.println(cah.getCellularAutomata().getClass());
        }
        writer.close();
    }

    /**
     * Getter for the name of the cellular automata related to this file handler
     * used for determining which file handler to use to parse a file
     * 
     * @return the name of the cellular automata 
     */
    public abstract String getName();
    
    /**
     * Getter for the class of related to this file handler
     * used for determining which file handler to use to write to a file
     * 
     * @return The Class of the Cellular Automata
     */
    public abstract Class getHandledClass();
    
    /**
     * Parse a file into a valid CellularAutomataHandler for the appropriate cellular Automata 
     * 
     * @param file a scanner made from the file to be parsed
     * @return a valid CellularAutomataHandler
     */
    protected abstract CellularAutomataHandler parse(Scanner file);

    /**
     * Write the details of cellular Automata grid state to a file
     * 
     * @param file a PrintWriter to the file.
     * @param cah  The CellularAutomataHandler to get data from.
     */
    protected abstract void write(PrintWriter file, CellularAutomataHandler cah);
}
