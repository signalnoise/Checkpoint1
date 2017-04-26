import java.util.Random;

public class Data {

    private static Random rand;

    private static double var(double[] vector){

        double avgOfSq = 0;
        double sqOfAvg = 0;

        for (int j=0; j<vector.length; j++){

            avgOfSq += Math.pow(vector[j],2);
            sqOfAvg += vector[j];

        }

        avgOfSq = avgOfSq/(double)vector.length;
        sqOfAvg = sqOfAvg/(double)vector.length;
        sqOfAvg = Math.pow(sqOfAvg, 2);

        return avgOfSq - sqOfAvg;

    }

    private static double avg(double[] vector){
        double sum = 0;

        for (int j=0; j<vector.length; j++){

            sum += vector[j];

        }

        return sum/vector.length;
    }

    private static double bootstrap(double[] vector, double prefactor){

        rand = new Random();

        double[] random = new double[vector.length];
        double[] bootstrap = new double[10];

        for (int j = 0; j<10; j++) {

            for (int i = 0; i < vector.length; i++) {
                random[i] = vector[rand.nextInt(vector.length)];
            }

            bootstrap[j] = prefactor*var(random);


        }

        return Math.sqrt(var(bootstrap));
    }


    public static void main(String args[]){
        int wait;
        int width; //Grid Size
        int height;

        double temperature = 1.5; //Bottom temperature
        double range = 2.5; //2.5 good
        boolean glauber;
        int dataPoints;
        int noSimulations;
        int dimension = 50;

        try {
             glauber = Boolean.parseBoolean(args[0]);
             dimension = Integer.parseInt(args[1]);
             dataPoints = Integer.parseInt(args[2]);
             noSimulations = Integer.parseInt(args[3]);

        } catch (Exception e){
            glauber = true;
            dataPoints = 100;
            noSimulations = 100;
            log("Error: Arguments required are isGlauber, dimension, dataPoints, noSimulations.");
            System.exit(1);
        }

        width = dimension;
        height = dimension;
        int N = width*height;

        IsingDynamics isingGrid;

        if (glauber){
            isingGrid = new Glauber(width, height, temperature);
            wait = 100*isingGrid.sweep();
        }
        else {
            isingGrid = new Kawasaki(width, height, temperature);
            wait = 400*isingGrid.sweep();
        }

        int measureEvery = 10*isingGrid.sweep(); //Once every 10 sweeps good
        int n = (dataPoints+1)*measureEvery; //Length of measurement array plus one
        int iterations = wait+n; //Number of iterations per run


        int length = (n/(measureEvery))-1;

        double[] magnetisation = new double[length];
        double[] susceptibility = new double[noSimulations];
        double[] avgMag = new double[noSimulations];
        double[] avgEnergy = new double[noSimulations];
        double[] energy = new double[length];
        double[] heatCapacity = new double[noSimulations];
        double[] tempArray = new double[noSimulations];
        double[] susError = new double[noSimulations];
        double[] heatError = new double[noSimulations];
        double tempStep = range/(double)noSimulations;





        //
        for (int i=0; i<(noSimulations); i++) {

            temperature = temperature+tempStep;
            tempArray[i] = temperature;
            if (glauber) {
                isingGrid.allTrue();
            }
            else{
                //isingGrid.fillRandomly();
                isingGrid.fillCheckerboard();
            }
            isingGrid.setTemperature(temperature);

            int count = 0;
            int magnetisationCount = 0;

            for (int j=0; j<iterations; j++) {
                isingGrid.update();

               if (j>wait){
                   count++;
                   if (count%measureEvery==0) {
                       magnetisation[magnetisationCount] = Math.abs(isingGrid.getMagnetisation());
                       energy[magnetisationCount] = (double)isingGrid.getEnergy();
                       magnetisationCount++;

                   }
               }

            }

            susceptibility[i] = var(magnetisation)/((double)N*temperature);
            susError[i] = bootstrap(magnetisation, Math.pow((double)N*temperature,-1));
            heatCapacity[i] = var(energy)/((double)N*Math.pow(temperature,2));
            heatError[i] = bootstrap(energy,Math.pow((double)N*Math.pow(temperature,2),-1));
            avgMag[i] = avg(magnetisation);
            avgEnergy[i] = avg(energy);

            log(Double.toString(temperature) + " "
                    + Double.toString(susceptibility[i]) + " "
                    + Double.toString(susError[i]) + " "
                    + Double.toString(avgMag[i]) + " "
                    + Double.toString(heatCapacity[i]) + " "
                    + Double.toString(heatError[i]) + " "
                    + Double.toString(avgEnergy[i]));

        }

    }

    private static void log(String s){
        System.out.println(s);
    }

}
