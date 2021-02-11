/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular_automata_simulation.wire_world;

import cellular_automata_simulation.cellular_automata.*;

public class WireWorld implements CellularAutomata {

    private static final float[][] evalGrid;
    private static final State Empty;
    private static final State Head;
    private static final State Tail;
    private static final State Wire;
    public static DiscreteStateGroup handler;
    public static WireWorldFileHandler fileHandler;

    static {
        fileHandler = new WireWorldFileHandler();
        evalGrid = new float[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        handler = new DiscreteStateGroup();
        handler.addState(Empty = new DiscreteState(0));
        handler.addState(Head = new DiscreteState(1));
        handler.addState(Tail = new DiscreteState(2));
        handler.addState(Wire = new DiscreteState(3));
    }

    @Override
    public State evalRule(State current, State laplacian, float deltaTime) {
        if(current == Head){
            return Tail;
        }else if(current == Tail){
            return Wire;
        }else if(current == Wire){
            return (laplacian.getValue(0) == 1 || laplacian.getValue(0) == 2)? Head:Wire;
        }
        return Empty;
    }

    @Override
    public float[][] getEvalGrid() {
        float[][] ret = new float[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(evalGrid[i], 0, ret[i], 0, 3);
        }
        return ret;
    }

    @Override
    public StateGroup getStateHandler() {
        return handler;
    }

    @Override
    public State getLaplacian(State s,State l, float w) {
        if(s == Head){
            return new DiscreteState(l.getValue(0) + 1);
        }
        return l;
    }

}
