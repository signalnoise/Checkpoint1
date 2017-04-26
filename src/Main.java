


import java.awt.Color;

public class Main {

    static int width;
    static int height;
    static Visualisation vis;

    private static void setColors(IsingDynamics dy){
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                if (dy.getBoolean(i, j)) {
                    vis.set(i, j, Color.cyan);
                }
                else {
                    vis.set(i,j, Color.magenta);
                }
            }
        }
    }

    public static void main(String[] args){
        double temperature = 2;
        boolean glauber = true;
        int dimension = 1;
        try {
            glauber = Boolean.parseBoolean(args[0]);
            dimension = Integer.parseInt(args[1]);
        }catch (Exception e){
            log("Error: Arguments required are of form <boolean>, <integer>");
            System.exit(1);
        }

        width = dimension;
        height = dimension;
        vis = new Visualisation(width,height);

        IsingDynamics dynamics;

        if (glauber){
            dynamics = new Glauber(width, height, temperature);
        } else {
            dynamics = new Kawasaki(width, height, temperature);
        }


        //Fills the grid randomly, colors the squares draw then starts looping
        dynamics.fillRandomly();
        setColors(dynamics);
        vis.draw();
        int count = 0;

        while(true){
            dynamics.update();
            count++;
            if (count%(dynamics.sweep())==0){
                setColors(dynamics);
                vis.draw();
                dynamics.setTemperature(vis.getTemperature());
            }

        }

    }

    private static void log(String s){
        System.out.println(s);
    }

}
