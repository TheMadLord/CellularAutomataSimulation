
package cellular_automata_simulation.cellular_automata;

/**
 * Interface for representing the state of a given cell in a cellular automata
 */
public interface State {
    
    /**
     * Getter for the number of values this state has
     * @return the the number of values this state has
     */
    public int getValueCount();
    
    /**
     * Getter for a given value of this state
     * 
     * @param ValueIndex the value to get
     * @return the value of the state at the given value
     */
    public float getValue(int ValueIndex);

}

