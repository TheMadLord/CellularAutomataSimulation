package cellular_automata_simulation.Reaction;

import cellular_automata_simulation.cellular_automata.*;

public class ReactionDiffusion implements CellularAutomata{
    private static float[][] evalGrid;
    public static StateGroup handler;
    public static ReactionDiffusionFileHandler fileHandler; 
    
    public float DiffuseA, DiffuseB, Feed, Kill;


    
    static{
        fileHandler = new ReactionDiffusionFileHandler();
        evalGrid = new float[][]{{0.05f,0.2f,0.05f},{0.2f,-1,0.2f},{0.05f,0.2f,0.05f}};
        handler = new ReactionDiffusionStateHandler();
    }
    public ReactionDiffusion(float DiffuseA, float DiffuseB, float Feed, float Kill) {
        this.DiffuseA = DiffuseA;
        this.DiffuseB = DiffuseB;
        this.Feed = Feed;
        this.Kill = Kill;
    }
    
    
    @Override
    public State evalRule(State current, State laplacian,float deltaTime){ 
        //A' = A + (Da * La - ABB + F * (1 -A))*DT
        float a = current.getValue(0);
        float b = current.getValue(1);
        float La = laplacian.getValue(0);
        float Lb = laplacian.getValue(1);
        float A = a + (DiffuseA * La - a*b*b + Feed * (1 - a)) * deltaTime;
        //B '= B + (Db * Lb + ABB - (K + F))*DT 
        float B = b + (DiffuseB * Lb + a*b*b - (Kill + Feed)*b) * deltaTime;
        return new ReactionDiffusionState(A,B);
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
        return new ReactionDiffusionState(s.getValue(0)*w+l.getValue(0), s.getValue(1)*w+l.getValue(1));
    }


}
 