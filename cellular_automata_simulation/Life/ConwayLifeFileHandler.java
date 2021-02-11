package cellular_automata_simulation.Life;

import cellular_automata_simulation.cellular_automata.*;

import java.io.*;
import java.util.Scanner;

/**
 * 
 * 
 */
public class ConwayLifeFileHandler extends FileHandler {

    public ConwayLifeFileHandler() {
        FileHandler.Register(this);
    }

    @Override
    public String getName() {
        return "Conway";
    }

    @Override
    public CellularAutomataHandler parse(Scanner file) {
        int width = Integer.parseInt(file.next());
        int height = Integer.parseInt(file.next());
        State dead = ConwayLife.handler.getState(0);
        State alive = ConwayLife.handler.getState(1);
        Grid g = new Grid(width, height, dead);
        for(int x = 0; x < width; x++){
                for(int y = 0; y < height; y++){
                    if(file.next().trim().equals("1.0")){
                        g.set(x, y, alive);
                    }
                }
                
        }
        g.flip();
        return new CellularAutomataHandler(new ConwayLife(), g);
    }

    @Override
    public void write(PrintWriter file, CellularAutomataHandler cah) {
        Grid g = cah.getGrid();
        int w = g.getWidth();
        int h = g.getHeight();
        file.println(w + " " + h);
        byte v = 0;
        byte i = 0;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                file.print(g.get(x, y).getValue(0)+" ");
            }
            file.println();
        }
    }

    @Override
    public Class getHandledClass() {
        return ConwayLife.class;
    }

}
