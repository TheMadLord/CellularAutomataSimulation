package cellular_automata_simulation.cellular_automata;

import java.util.Random;

/**
 * Handles the parsing, managing and validating of a type states
 */
public interface StateGroup {

    /**
     * Checks if a State is a valid state 
     *
     * @param s the state to check
     * @return if the state is valid
     */
    public boolean validateState(State s);

    
    /**
     * Get a random state. 
     * 
     * @param rng A random number generator, used to generate the returned state
     * @return a Random state
     */
    public State getRandomState(Random rng);

    /**
     * Gets a State from a value.
     * 
     * @param value the value to parse to a state
     * @return the state corresponding to the given value.
     */
    public State getState(float value);

    /**
     * Getter for a default state 
     * @return The Default state of the StateGroup
     */
    public State getBaseStae();

    /**
     * Getter for all states this State Handler knows.
     * if a Non-finite number key states are returned
     * 
     * @return a list of states 
     */
    public State[] getAllStates();
}
