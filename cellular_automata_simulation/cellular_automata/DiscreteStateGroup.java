package cellular_automata_simulation.cellular_automata;

import java.util.HashMap;
import java.util.Random;

/**
 * A group of discrete states for use in Cellular automata where the number of possible states is limited
 */
public class DiscreteStateGroup implements StateGroup{
    
    /**
     * a map of value to state
     */
    private final HashMap<Float, State> stateMap;
    
    /**
     *Constructor 
     */
    public DiscreteStateGroup(){
        stateMap = new HashMap<>();
    }
    
    /**
     * Adds a state to the group
     * Overrides already existing if value are the same
     * 
     * @param s The state to be added
     */
    public void addState(State s){
        stateMap.put(s.getValue(0),s);
    }
    
    /**
     * Getter for a state based on value. 
     * 
     * @param value
     * @return The state with the given value. If value is not equal an existing state
     * the existing state with the value closest to the given value is returned. If no states
     * have been added to the group null is returned. 
     */
    @Override
    public State getState(float value){
         State[] all = getAllStates();
         if(all.length == 0){
             return null;
         }
         if(stateMap.containsKey(value)){
             return stateMap.get(value);
         }
         State ret;
         ret = all[0];
         float retDist = Math.abs(value-ret.getValue(0));
         for(State s: all){
             float dist = Math.abs(value-s.getValue(0));
             if(dist < retDist){
                 ret = s;
                 retDist = dist;
             }
         }
         return ret;
    }
    
    @Override
    public State[] getAllStates(){
        return stateMap.values().toArray(new State[stateMap.size()]);
    }
    @Override
    public boolean validateState(State s) {
        return stateMap.get(s.getValue(0)) == null;
    }

    @Override
    public State getRandomState(Random rng) {
        State[] states = getAllStates();
        int r = Math.abs(rng.nextInt())%states.length;
        return states[r];
    }

    @Override
    public State getBaseStae() {
        return new DiscreteState(0);
    }
   
}
