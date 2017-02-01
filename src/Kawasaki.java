import java.util.Random;

public class Kawasaki extends IsingDynamics {

    private int i;
    private int j;
    private int k;
    private int l;

    public Kawasaki(int w, int h, double T) {
        rand = new Random();
        width = w;
        height = h;
        grid = new boolean[width][height];
        temperature = T;
    }

    //Exchanges a pair of 'spins'
    @Override
    void flip(){
        boolean one = grid[i][j];
        boolean two = grid[k][l];
        grid[i][j] = two;
        grid[k][l] = one;
    }

    //Evaluates the energy change before and after
    @Override
    double energyChange(){
        double before = energyOf(i,j) + energyOf(k,l);
        this.flip();
        double after = energyOf(i,j) + energyOf(k,l);
        this.flip();
        return after - before;
    }

    //Assigns random values to indices i,j,k,l such that i!=k and k!=l
    @Override
    void chooseSpins(){
        this.i = rand.nextInt(width);
        this.j = rand.nextInt(height);
        this.k = rand.nextInt(width);
        this.l = rand.nextInt(height);
        while(i==k && j==l){
            k = rand.nextInt(width);
            l = rand.nextInt(height);
        }
    }

    //Quantifies the difference in energy if the two spins are adjacent
    private int energyCorrection(int i, int j, int k, int l){
        boolean one = i-k == 1;
        boolean two = j-l == 1;
        boolean three = i-k == -1;
        boolean four = j-l == -1;
        // XOR for non contradictory expressions
        boolean five = (one || two || three || four) && !(one && two || one && four || three && two || three && four);
        if (five){
            return 1;
        }
        else return 0;
    }

    private static void log(String s){
        System.out.println(s);
    }

}
