
public class Data {

    private static double stDev(double[] vector){

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


    public static void main(String args[]){

        double temperature = 1.5; //Bottom temperature
        int width = 50; //Grid Size
        int height = 50;
        int iterations = 10000000; //Number of iterations per run
        int n = (3*iterations)/10; //Fraction of data points used to analyse
        int noSimulations = 50;
        double range = 2.5; //2.5 good
        Glauber isingGrid = new Glauber(width, height, temperature);
        int measureEvery = 10*isingGrid.sweep(); //Once every 10 sweeps good

        int length = n/(measureEvery);

        double[] magnetisation = new double[length];
        double[] susceptibility = new double[noSimulations];
        double tempStep = range/(double)noSimulations;





        //
        for (int i=0; i<(noSimulations); i++) {
            temperature = temperature+tempStep;
            isingGrid.allTrue();
            isingGrid.setTemperature(temperature);
            int count = 0;
            int magnetisationCount = 0;

            for (int j=0; j<iterations; j++) {
                isingGrid.update();

               if (j>iterations-(n+1)){
                   count++;
                   if (count%measureEvery==0) {
                       magnetisation[magnetisationCount] = Math.abs(isingGrid.getMagnetisation());
                       magnetisationCount++;
                   }
               }

            }

            susceptibility[i] = stDev(magnetisation)/((double)isingGrid.sweep()*temperature);

           // magnetisation[i] = Math.abs(magnetisation[i]);

            log(Double.toString(temperature) + " " + Double.toString(susceptibility[i]));


        }

    }

    private static void log(String s){
        System.out.println(s);
    }

}
