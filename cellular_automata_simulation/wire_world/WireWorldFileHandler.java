package cellular_automata_simulation.wire_world;


import java.io.*;
import java.util.Scanner;

import cellular_automata_simulation.cellular_automata.*;

public class WireWorldFileHandler extends FileHandler {

    public WireWorldFileHandler() {
        FileHandler.Register(this);
    }

    @Override
    public String getName() {
        return "WireWorld";
    }

    @Override
    public CellularAutomataHandler parse(Scanner file) {
        int width = Integer.parseInt(file.next());
        int height = Integer.parseInt(file.next());
        State empty = WireWorld.handler.getState(0);
        Grid g = new Grid(width, height, empty);
        int x, y;
        x =  y = 0;
        System.out.println(this.getName());
        System.out.println(width + " " + height);
        while(file.hasNext()){
            float v = Float.parseFloat(file.next());
            g.set(x, y,WireWorld.handler.getState(v));
            x++;
            if(x >= width){
                x =0;
                y++;
            }
        }
        g.flip();
        return new CellularAutomataHandler(new WireWorld(), g);
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
                file.print(g.get(x, y).getValue(0) + " ");
            }
            file.println();
        }
    }

    @Override
    public Class getHandledClass() {
        return WireWorld.class;
    }

}
