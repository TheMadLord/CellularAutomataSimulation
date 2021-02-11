package cellular_automata_simulation.cellular_automata;

/**
 * Used for updating a grid based on a Cellular Automata
 */
public class CellularAutomataHandler {
    
    /**
     * The Cellular Automata to use for updating the grid
     */
    private CellularAutomata ca;
    
    /**
     * The grid that the CellularAutomata updates
     */
    private Grid grid;
    
    /**
     * Constructor
     * 
     * @param ca an initial cellular automata 
     * @param g the grid on which the cellular automata "lives"
     */
    public CellularAutomataHandler(CellularAutomata ca, Grid g){
        
        this.ca = ca;
        grid = g;
    }

    /**
     * Getter for the current grid
     * 
     * @return the current grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Setter for the grid
     * 
     * @param grid 
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /**
     * Getter for the current Cellular Automata
     * 
     * @return the current Cellular Automata
     */
    public CellularAutomata getCellularAutomata() {
        return ca;
    }
    
    /**
     * Setter for the current Cellular Automata
     * 
     * @param ca 
     */
    public void setCelluarAutomata(CellularAutomata ca) {
        this.ca = ca;
    }
    
    /**
     * Steps the cellular automata forward by 1 second of time
     */
    public void step(){
        update(1);
    }
    
    /**
     * Updates the cellular automata 
     * 
     * @param deltaTime the amount of time to update the cellular automata
     */
    public void update(float deltaTime){
        int w = grid.getWidth();
        int h = grid.getHeight();
        float[][] eval =ca.getEvalGrid();
        //for each cell
        for(int y = 0;  y < h; y++){
            for(int x = 0; x < w; x++){
                //calculate neighbors
                State[][] neighbors = grid.getNeighborhood(x,y);
                State l = ca.getStateHandler().getBaseStae();
                //do Laplacian calucaltions on neighborhood
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        l = ca.getLaplacian(neighbors[i][j],l,eval[i][j]);
                    }
                }
                //calculate new cell state based on Laplacian
                State s = ca.evalRule(grid.get(x, y), l, deltaTime);
                grid.set(x, y, s);
            }
        }
        //update grid
        grid.flip();
    }
}
