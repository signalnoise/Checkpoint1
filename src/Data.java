
public class Data {

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



    public static void main(String args[]){

        int width = 50; //Grid Size
        int height = 50;
        int N = width*height;
        double temperature = 0.0; //Bottom temperature
        double range = 4; //2.5 good

        //Glauber isingGrid = new Glauber(width, height, temperature);
        Kawasaki isingGrid = new Kawasaki(width, height, temperature);

        int measureEvery = 10*isingGrid.sweep(); //Once every 10 sweeps good
        int n = 101*measureEvery; //Length of measurement array plus one
        int wait = 100*isingGrid.sweep();
        int iterations = wait+n; //Number of iterations per run
        int noSimulations = 200;

        int length = (n/(measureEvery)) -1;

        double[] magnetisation = new double[length];
        double[] susceptibility = new double[noSimulations];
        double[] avgMag = new double[noSimulations];
        double[] energy = new double[length];
        double[] heatCapacity = new double[noSimulations];
        double[] tempArray = new double[noSimulations];
        double tempStep = range/(double)noSimulations;





        //
        for (int i=0; i<(noSimulations); i++) {

            temperature = temperature+tempStep;
            tempArray[i] = temperature;
            //isingGrid.allTrue();
            isingGrid.fillRandomly();
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
            heatCapacity[i] = var(energy)/((double)N*Math.pow(temperature,2));
            avgMag[i] = avg(magnetisation);


            log(Double.toString(temperature) + " "
                    + Double.toString(susceptibility[i]) + " "
                    + Double.toString(avgMag[i]) + " "
                    + Double.toString(heatCapacity[i]));

        }

    }

    private static void log(String s){
        System.out.println(s);
    }

}
