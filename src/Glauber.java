

import java.util.Random;

public class Glauber extends IsingDynamics {

    private int i;
    private int j;

    public Glauber(int w, int h, double T) {
        rand = new Random();
        width = w;
        height = h;
        grid = new boolean[width][height];
        temperature = T;
    }

    @Override
    double energyChange(){
        double before = energyOf(i,j);
        this.flip();
        double after = energyOf(i,j);
        this.flip();
        return after - before;
    }

    @Override
    void flip(){
        grid[i][j] = !grid[i][j];
    }

    @Override
    public void chooseSpins(){
        this.i = rand.nextInt(width);
        this.j = rand.nextInt(height);
    }
}