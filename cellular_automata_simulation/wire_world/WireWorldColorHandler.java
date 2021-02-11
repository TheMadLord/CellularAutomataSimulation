/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular_automata_simulation.wire_world;

import cellular_automata_simulation.cellular_automata_view.CellularAutomataColorHandler;
import cellular_automata_simulation.cellular_automata.State;
import java.awt.Color;

/**
 *
 * @author The Madlord
 */
public class WireWorldColorHandler implements CellularAutomataColorHandler{
    Color Wire = Color.BLUE;
    Color Empty = Color.BLACK;
    Color Head = Color.YELLOW;
    Color Tail = Color.RED;
    
    @Override
    public Color getColor(State state) {
        if(state.getValue(0) <= 0.5){
            return Empty;
        }else if(state.getValue(0) <= 1.5){
            return Head;
        }else if(state.getValue(0) <= 2.5){
            return Tail;
        }
        return Wire;
    }

    @Override
    public void setColor(State state, Color c) {
    }
    
}
