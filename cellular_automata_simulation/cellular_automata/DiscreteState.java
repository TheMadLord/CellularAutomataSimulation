package cellular_automata_simulation.cellular_automata;

/**
 * A state for a cellular automata that has a given number of states of
 * states. Each state represented by a single value.
 */
public class DiscreteState implements State{
    private float value;
   
    /**
     * Constructor
     * 
     * @param value the value of the new state.
     */
    public DiscreteState(float value){
        this.value = value;
    }
    
    @Override
    public int getValueCount() {
       return 1;
    }

    @Override
    public float getValue(int ValueIndex) {
        if(ValueIndex == 0){
            return value;
        }else{
            return 0;
        }
    }
}
