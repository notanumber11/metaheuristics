
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.exit;



public class Main {
    static final  int NUMBER_OF_EXECUTIONS = 10;

    public static void main(String[] args) throws IOException {
        boolean obtain10Results = false;
        boolean improve = false;
        Timer timer = new Timer();

        Practica4 practica4 = null;
        //Practica 4
        System.out.println("Processing to file: OutputFiles/results.txt");
        NewPrinter.activePrintToFile();

        // Reading from arguments
        if( args.length == 1 ) {
                // Get distances from file
                if(improve){
                    practica4 = new Practica4Improvement(args[0]);
                }
                else{
                        practica4 = new Practica4Random(args[0]);
                    }
        }
        else if (args.length == 2){
            // Get distances from file
            practica4 = new Practica4(args[0]);
            // Get randomNumbers from file
            Reader.readNumbersFromFile(args[1]);

        }
        else{
            System.out.println("Wrong argument numbers");
            exit(-1);
        }

        if(obtain10Results){
            measureAndDesviationx10(args[0]);
        }
        else{
         //   timer.start();
            practica4.start();
           // timer.end();
            // timer.print();
        }

        }


    private static void getMeasureAndDesviation(ArrayList<Integer> listOFResults, ArrayList<Integer> listOfIterations) {
        NewPrinter.activePrintToConsole();
        int result = 0;
        int resultIteration = 0;
        for(int i = 0; i < listOFResults.size();i++){
            System.out.println("Resultado " + i + " " + listOFResults.get(i) + " Iteracion " + listOfIterations.get(i));
            result += listOFResults.get(i);
            resultIteration += listOfIterations.get(i);
        }

        double measure = result/listOFResults.size();

        double iteration = resultIteration/listOfIterations.size();

        double temp = 0;
        for(double a :listOFResults){
            temp += (a-measure)*(a-measure);
        }
        double desv = Math.sqrt(temp/listOFResults.size());

        double temp2 = 0;
        for(double a :listOfIterations){
            temp2 += (a-iteration)*(a-iteration);
        }
        double desv2 = Math.sqrt(temp2/listOfIterations.size());


        System.out.println(" Media = " + measure + " Desviacion " + desv);
        System.out.println("Iteracion media = " + iteration + " Desviacion " + desv2);
    }


    private static void measureAndDesviationx10(String distancePath) {

        ArrayList<Integer> listOFResults = new ArrayList<>();
        ArrayList<Integer> listOFIterations = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_EXECUTIONS; i++){
            Practica4 practica4 = new Practica4Random(distancePath);
            practica4.start();
            listOFResults.add(practica4.getBestCost());
            listOFIterations.add(practica4.getBestIterationEver());
            NewPrinter.activePrintToConsole();
            System.out.println("Iteracion " + i + " Coste: " + listOFResults.get(i) + " Iteracion : " + listOFIterations.get(i));
            NewPrinter.activePrintToFile();

        }

        getMeasureAndDesviation(listOFResults,listOFIterations);
    }


}


