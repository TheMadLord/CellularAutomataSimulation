package cellular_automata_simulation.cellular_automata_view;

import cellular_automata_simulation.cellular_automata.*;

import java.awt.*;
import javax.swing.*;

/**
 * Custom JPanel for drawing the grid of a Cellular Automata
 */
public class GridViewer extends JPanel{
    
    /**
     * Reference to the grid to draw
     */
    private Grid grid;
    
    /**
     * should the grid lines be draw or not
     */
    private boolean showGrid;
    /**
     * Color of the grid lines
     */
    private Color gridColor;
    
    /**
     * The CelluarAutomataColorHandler to use to resolve the colors of states in
     * the grid
     */
    private CellularAutomataColorHandler cach;
    
    /**
     * Constructor 
     * 
     * @param grid the grid to draw to screen.
     * @param colorHandler  the colorHandler to use to resolve colors
     */
    public GridViewer(Grid grid, CellularAutomataColorHandler colorHandler){
        super();
        this.grid = grid;
        cach = colorHandler;
        gridColor = Color.LIGHT_GRAY;
        showGrid= false;
    }
    
    /**
     * Setter for whether or not to show the grid lines
     * 
     * @param show to show the grid lines
     */
    public void showGrid(Boolean show){
        showGrid = show;
    }
    
    /**
     * Getter for whether or not to show the grid lines
     * 
     * @return if the grid lines are showing 
     */
    public boolean gridShowing(){
        return showGrid;
    }

    /**
     * Getter for the color of the grid lines
     * 
     * @return the color of the grid lines
     */
    public Color getGridColor() {
        return gridColor;
    }

    /**
     * Setter for the color of the grid lines
     * 
     * @param gridColor the new color of the grid lines
     */
    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    /**
     * Getter for the grid being drawn
     * 
     * @return the grid being drawn
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Setter for the grid being drawn
     * 
     * @param grid the new grid to draw
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /**
     * Getter for the Color Handler currently being used
     * 
     * @return Color Handler currently being used
     */
    public CellularAutomataColorHandler getColorHandler() {
        return cach;
    }

    /**
     * Setter for the color Handler currently being used
     * @param cach color Handler to used
     */
    public void setColorHandler(CellularAutomataColorHandler cach) {
        this.cach = cach;
    }

    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(grid == null){
            return;
        }
        int w = this.getWidth()/grid.getWidth();
        int h = this.getHeight()/grid.getHeight();
        int size = Math.min(w, h);
        if(w <= 0 || h <= 0){
            return;
        }
        size = Math.max(size, 1);
        for (int x = 0; x < grid.getWidth(); x++) {   
            for (int y = 0; y < grid.getHeight(); y++) {;
                    g.setColor(cach.getColor(grid.get(x, y)));
                    g.fillRect(x*size, y*size, size, size);
            }
        }
        g.setColor(Color.BLACK);
        g.drawString("Toggle Pause(P)    Step(Space)    Toggle Grid(G)",10,grid.getHeight()*size+ 10);
        if(!showGrid){
            return;
        }
        g.setColor(this.gridColor);
        for (int i = 1; i < grid.getWidth(); i++) {   
                    g.drawLine(i*size, 0, i*size,grid.getHeight()*size);
        }    
        for (int i = 1; i < grid.getHeight(); i++) {   
                    g.drawLine(0,i*size, grid.getWidth()*size, i*size);
        }
    }
}
