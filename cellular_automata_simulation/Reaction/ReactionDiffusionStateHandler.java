/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cellular_automata_simulation.Reaction;

import cellular_automata_simulation.cellular_automata.*;

import java.util.Random;

/**
 *
 * @author The Madlord
 */
public class ReactionDiffusionStateHandler implements StateGroup{

    @Override
    public boolean validateState(State s) {
        if(s instanceof ReactionDiffusionState){
            ReactionDiffusionState rds = (ReactionDiffusionState)s;
            if(0 <= rds.A && rds.A <= 1 && 0 <= rds.B && rds.B <= 1){
                return true;
            }
        }
        return false;
    }

    @Override
    public State getRandomState(Random rng) {
        return getState(rng.nextFloat());
    }

    @Override
    public State getState(float value) {
        value = Math.min(value, 1);
        return(new ReactionDiffusionState(value, 1 - value));
    }

    @Override
    public State[] getAllStates() {
        return new State[]{new ReactionDiffusionState(1,0),new ReactionDiffusionState(0,1)};
    }

    @Override
    public State getBaseStae() {
       return new ReactionDiffusionState(0,0);
    }

    
   
}
