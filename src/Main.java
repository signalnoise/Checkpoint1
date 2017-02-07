

import java.awt.Color;

public class Main {

    static int width;
    static int height;
    static Visualisation vis;

    private static void setColors(IsingDynamics dy){
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                if (dy.getBoolean(i, j)) {
                    vis.set(i, j, Color.MAGENTA);
                }
                else {
                    vis.set(i,j, Color.cyan);
                }
            }
        }
    }

    public static void main(String[] args){
        double temperature = 1000;
        width = 50;
        height = 50;
        vis = new Visualisation(width,height);
        Glauber glauber = new Glauber(width, height, temperature);
        //Kawasaki glauber = new Kawasaki(width, height, temperature);
        glauber.fillRandomly();setColors(glauber);
        vis.draw();
        int count = 0;
        while(true){
            glauber.update();
            count++;
            if (count%glauber.sweep()==0){
                setColors(glauber);
                vis.draw();
            }
            if (count == 1000000){
                glauber.setTemperature(2.3);
            }

        }

    }

}
