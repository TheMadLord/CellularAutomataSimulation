package cellular_automata_simulation.Life;

import cellular_automata_simulation.cellular_automata_view.CellularAutomataColorHandler;
import cellular_automata_simulation.cellular_automata.State;
import java.awt.Color;

/**
 *
 * @author The Madlord
 */
public class ConwayLifeColorHandler implements CellularAutomataColorHandler{
    Color Alive = Color.RED;
    Color Dead = Color.BLACK;
    
    @Override
    public Color getColor(State state) {
        return (state.getValue(0) > 0.5)? Alive:Dead;
    }

    @Override
    public void setColor(State state, Color c) {
        if(state.getValue(0) > 0.5){
            Alive = c;
        }else{
            Dead = c;
        }
    }
    
}
