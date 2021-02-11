package cellular_automata_simulation.Reaction;

import cellular_automata_simulation.cellular_automata_view.CellularAutomataColorHandler;
import cellular_automata_simulation.cellular_automata.State;
import java.awt.Color;

public class ReactionDiffusionColorHandler implements CellularAutomataColorHandler{
    
    @Override
    public Color getColor(State state) {
        int s = (int)(255*state.getValue(0));
        s = Math.min(Math.max(0,s),255);
        return new Color(s,s,s);
    }

    @Override
    public void setColor(State state, Color c) {
    }
    
}
