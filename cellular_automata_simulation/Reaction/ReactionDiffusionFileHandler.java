package cellular_automata_simulation.Reaction;

import cellular_automata_simulation.cellular_automata.*;

import java.io.*;
import java.util.Scanner;

public class ReactionDiffusionFileHandler extends FileHandler {

    public ReactionDiffusionFileHandler() {
        FileHandler.Register(this);
    }

    @Override
    public String getName() {
        return "Reaction";
    }

    @Override
    public CellularAutomataHandler parse(Scanner file) {
        ReactionDiffusion rd = new ReactionDiffusion(0,0,0,0);
        int width = Integer.parseInt(file.next());
        int height = Integer.parseInt(file.next());
        rd.DiffuseA = Float.parseFloat(file.next());
        rd.DiffuseB = Float.parseFloat(file.next());
        rd.Feed = Float.parseFloat(file.next());
        rd.Kill = Float.parseFloat(file.next());
        Grid g = new Grid(width, height, new ReactionDiffusionState(0,0));
        for(int x = 0; x < width; x++){
                for(int y = 0; y < height; y++){
                    float a = Float.parseFloat(file.next());
                    float b = Float.parseFloat(file.next());
                    g.set(x, y, new ReactionDiffusionState(a,b));
                }
                
        }
        g.flip();
        return new CellularAutomataHandler(rd, g);
    }

    @Override
    public void write(PrintWriter file, CellularAutomataHandler cah) {
        Grid g = cah.getGrid();
        ReactionDiffusion rd = (ReactionDiffusion)cah.getCellularAutomata();
        int w = g.getWidth();
        int h = g.getHeight();
        file.println(w + " " + h);
        file.println(rd.DiffuseA + " " + rd.DiffuseB);
        file.println(rd.Feed + " " + rd.Kill);
        byte v = 0;
        byte i = 0;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                file.print(g.get(x, y).getValue(0)+" "+g.get(x, y).getValue(1)+ " ");
            }
            file.println();
        }
    }

    @Override
    public Class getHandledClass() {
        return ReactionDiffusion.class;
    }

}
