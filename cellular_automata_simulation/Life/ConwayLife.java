/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular_automata_simulation.Life;

import cellular_automata_simulation.cellular_automata.*;

public class ConwayLife implements CellularAutomata{
    private static float[][] evalGrid;
    private static State Alive;
    private static State Dead;
    public static DiscreteStateGroup handler;
    public static ConwayLifeFileHandler fileHandler; 
    
    static{
        fileHandler = new ConwayLifeFileHandler();
        evalGrid = new float[][]{{1,1,1},{1,0,1},{1,1,1}};
        handler = new DiscreteStateGroup();
        handler.addState(Alive = new DiscreteState(1));
        handler.addState(Dead = new DiscreteState(0));
    }
    
    @Override
    public State evalRule(State current, State laplacian,float deltaTime){ 
        float L = laplacian.getValue(0);
        return ((L >= ((current == Alive)? 2:3)) && (L <= 3))? Alive:Dead;
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
    public State getLaplacian(State s, State l, float w) {
        return new DiscreteState((s.getValue(0) * w) +  l.getValue(0));
    }


}
 