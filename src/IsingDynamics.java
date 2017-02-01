

import java.util.Random;




public abstract class IsingDynamics {

    boolean[][] grid;
    Random rand;
    int width;
    int height;
    double temperature;

    //Defines the dynamics of the simulation
    public void update(){
        this.chooseSpins();
        this.metropolis();
    }

    abstract void flip();

    abstract double energyChange();

    abstract void chooseSpins();

    //Returns the value of the grid at i, j
    boolean getBoolean(int i, int j){
        return grid[i][j];
    }

    //Getter and setter for spins
    public int getSpin(int i, int j) {
        return spinOf(i, j);
    }

    public void setSpin(int i, int j, boolean bool) {
        this.grid[i][j] = bool;
    }

    //These methods implement the boundary conditions on the grid and return a numerical value for the spin
    private int right(int i,int j) {
        if (i==width-1){
            i=0;
        }
        else i++;
        return spinOf(i,j);
    }

    private int left(int i, int j) {
        if (i==0){
            i=width-1;
        }
        else i--;
        return spinOf(i,j);
    }

    private int above(int i, int j){
        if (j==height-1) {
            j=0;
        }
        else {
            j++;
        }
        return spinOf(i,j);
    }

    private int below(int i, int j) {
        if (j==0){
            j=height-1;
        }
        else {
            j--;
        }
        return spinOf(i,j);
    }

    //Spin of
    private int spinOf(int i, int j){
        if (grid[i][j]) return 1;
        else return -1;
    }

    //Returns the sum of the products of the nearest neighbour spins to i, j
    double energyOf(int i, int j){
        return -(spinOf(i,j))*(this.left(i,j) + this.right(i,j) + this.above(i,j) + this.below(i,j));
    }

    //Implements the metropolis algorithm
    public void metropolis() {
        double deltaE = this.energyChange();
        if (deltaE<0) this.flip();
        if (deltaE >= 0) {
            double p = Math.exp(-deltaE / this.temperature);
            double r = rand.nextDouble();
            if (r<p){
                this.flip();
            }
        }
    }

    //Fills the grid with random boolean values
    public void fillRandomly() {
        for (int i=0; i<this.grid.length; i++) {
            for (int j=0; j<this.grid[i].length; j++) {
                grid[i][j] = rand.nextBoolean();
            }
        }

    }

    //Makes it easier to print
    private static void log(String s){
        System.out.println(s);
    }
}
