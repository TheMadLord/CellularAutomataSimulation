package cellular_automata_simulation.cellular_automata_view;

import cellular_automata_simulation.cellular_automata.State;
import java.awt.Color;

/**
 * Interface for handling the drawing of states
 */
public interface CellularAutomataColorHandler {
    
    /**
     * Translate between a Cellular State and a color
     * 
     * @param state the state of a given cell
     * @return the color the state should be colored
     */
    public Color getColor(State state);
    
    /**
     * Set the color for a given state.
     * 
     * @param state the cell state 
     * @param c the Color that the state should be
     */
    public void setColor(State state, Color c);
}
