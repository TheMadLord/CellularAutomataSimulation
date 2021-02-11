package cellular_automata_simulation.cellular_automata;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * A Tessellated surface for cellular automata to live o.
 */
public class Grid {

    /**
     * which of the sub grids we are reading from used to control read and
     * writing overlaps
     */
    private int currentGrid;

    /**
     * the actual grid, contest of 2 grids of the same size
     */
    private State[][][] grids;

    /**
     * Constructor Initialize the grid based on an image.
     *
     * @param image the Image to initialize from.
     * @param sg the state group to initialize the grid with
     */
    public Grid(BufferedImage image, StateGroup sg) {
        this.seed(image, sg);
    }

    /**
     * Constructor Initialize the grid with random states
     *
     * @param width the width of the new grid
     * @param height the height of the new grid
     * @param rng the random number generator to use to generate the
     * @param sg the state group to initialize the grid with
     */
    public Grid(int width, int height, Random rng, StateGroup sg) {
        this.seed(width, height, rng, sg);
    }

    /**
     * Constructor Initialize the grid with a checker board of states
     *
     * @param width the width of the new grid
     * @param height the height of the new grid
     * @param checkerSize the length in cells of each checker grid.
     * @param sg the state group to initialize the grid with
     */
    public Grid(int width, int height, int checkerSize, StateGroup sg) {
        this.seed(width, height, checkerSize, sg);
    }

    /**
     * Constructor Initialize the grid filled with a single state
     *
     * @param width the width of the new grid
     * @param height the height of the new grid
     * @param state the state to initialize the grid with
     */
    public Grid(int width, int height, State state) {
        grids = new State[2][width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grids[1][i][j] = grids[0][i][j] = state;
            }
        }
    }

    /**
     * Reinitialize the grid with random states
     *
     * @param width the width of the new grid
     * @param height the height of the new grid
     * @param rng the random number generator to use to generate the
     * @param sg the state group to initialize the grid with
     */
    public void seed(int width, int height, Random rng, StateGroup sg) {
        grids = new State[2][width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grids[1][i][j] = grids[0][i][j] = sg.getRandomState(rng);
            }
        }
    }

    /**
     * Reinitialize the grid with a checker board of states
     *
     * @param width the width of the new grid
     * @param height the height of the new grid
     * @param checkerSize the length in cells of each checker grid.
     * @param sg the state group to initialize the grid with
     */
    public void seed(int width, int height, int checkerSize, StateGroup sg) {
        if (checkerSize <= 0 || checkerSize > height || checkerSize > width) {
            throw new Error("INVAID CHECKER SIZE");
        }
        State[] states = sg.getAllStates();
        grids = new State[2][width][height];
        int cl = states.length * checkerSize;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grids[1][i][j] = grids[0][i][j] = states[((j % cl) / checkerSize + (i % cl) / checkerSize) % states.length];
            }
        }
    }

    /**
     * Reinitialize the grid based on an image.
     *
     * @param image the Image to initialize from.
     * @param sg the state group to initialize the grid with
     */
    public void seed(BufferedImage image, StateGroup sg) {
        int w = image.getWidth();
        int h = image.getHeight();
        float f = (255 * 3 + 1);
        grids = new State[2][w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int clr = image.getRGB(i, j);
                int r = (clr & 0x00ff0000) >> 16;
                int g = (clr & 0x0000ff00) >> 8;
                int b = clr & 0x000000ff;
                grids[1][i][j] = grids[0][i][j] = sg.getState((r + g + b) / f);
            }
        }
    }

    /**
     * Finalizes all write since the last flip.
     */
    public void flip() {
        currentGrid = (currentGrid + 1) % 2;
    }

    /**
     * Calculates a 3 by 3 array of neighboring states to the cell at (x,y)
     *
     * @param x the x coordinate of center of the neighborhood
     * @param y the x coordinate of center of the neighborhood
     * @return a 3 by 3 array
     */
    public State[][] getNeighborhood(int x, int y) {
        State[][] ret = new State[3][3];
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x2 = x + i;
                int y2 = y + j;

                x2 = (x2 < 0) ? getWidth() - 1 : ((x2 >= getWidth()) ? 0 : x2);
                y2 = (y2 < 0) ? getHeight() - 1 : ((y2 >= getHeight()) ? 0 : y2);
                ret[i + 1][j + 1] = get(x2, y2);

            }

        }
        return ret;
    }

    /**
     * Getter for the width of the grid in cells
     *
     * @return the width of the grid
     */
    public int getWidth() {
        return grids[0].length;
    }

    /**
     * Getter for the height of the grid in cells
     *
     * @return the height of the grid
     */
    public int getHeight() {
        return grids[0][0].length;
    }

    /**
     * Getter for the state of a given cell
     * 
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @return the state of the cell at (x,y)
     */
    public State get(int x, int y) {
        return grids[currentGrid][x][y];
    }

    /**
     * Setter for the state of a given cell
     * 
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @param value the state to set the cell to.
     */
    public void set(int x, int y, State value) {
        grids[(currentGrid + 1) % 2][x][y] = value;
    }
}
