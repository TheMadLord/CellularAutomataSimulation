package cellular_automata_simulation.cellular_automata;

/**
 * Base Interface for a cellular Automata
 */
public interface CellularAutomata {
    
        /**
         * Calculates the new state of a cell based on it current state the Laplacian
         * value of it neighborhood and the amount of time passed.
         * 
         * @param current the current state of cell
         * @param laplacian the total Laplacian state of the neighboring cells 
         * @param deltaTime the change in time
         *
         * @return the new State of the cell. 
         */
        public State evalRule(State current,State laplacian, float deltaTime);
        
        /**
         * Getter for the Laplacian Grid. This is a 3 by 3 array of weights used
         * for the Laplacian process.
         * 
         * @return A 3 by 3 array of weights.
         */
        public float[][] getEvalGrid();
        
        /**
         * Getter for the StateGroup for this Cellular Automata
         * 
         * @return the StateGroup related to the Cellular Automata
         */
        public StateGroup getStateHandler();
        
        /**
         * Calculates the Laplacian state for a state.
         * Used to calculate the running Laplacian state of a neighborhood.  
         * 
         * @param state the current state of the cell
         * @param laplacian The running Laplacian state for the neighborhood
         * @param weight The weight of this cell
         * @return the new Running Laplacian state.
         */
        public State getLaplacian(State state, State laplacian, float weight);
}
