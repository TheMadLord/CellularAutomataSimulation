package cellular_automata_simulation.Reaction;

import cellular_automata_simulation.cellular_automata.*;

public class ReactionDiffusionState implements State{
        float A, B;
        
        public ReactionDiffusionState(float A, float B) {
            this.A = A;
            this.B = B;
        }
        
        
        @Override
        public float getValue(int ValueIndex) {
           if(ValueIndex == 0){
               return A;
           }
           if(ValueIndex == 1){
               return B;
           }
           //should through error but for now
           return 0;
        }

        @Override
        public int getValueCount() {
            return 2;
        }
    }
