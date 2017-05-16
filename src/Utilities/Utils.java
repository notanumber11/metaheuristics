package Utilities;

import EvolutionaryAlgorithm.BaseEvolutionary;
import EvolutionaryAlgorithm.BaseEvolutionaryA;

import java.util.ArrayList;

/**
 * Created by notanumber on 5/16/17.
 */
public class Utils {

    static final  int NUMBER_OF_EXECUTIONS = 10;

    public static void getMeasureAndDesviation(ArrayList<Integer> listOFResults, ArrayList<Integer> listOfIterations) {
        PrinterEvolutionary.activePrintToConsole();
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


    public static void measureAndDesviationx10(String distancePath) {

        ArrayList<Integer> listOFResults = new ArrayList<>();
        ArrayList<Integer> listOFIterations = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_EXECUTIONS; i++){
            BaseEvolutionary baseEvolutionary = new BaseEvolutionaryA(distancePath);
            baseEvolutionary.start();
            listOFResults.add(baseEvolutionary.getBestCost());
            listOFIterations.add(baseEvolutionary.getBestIterationEver());
//            PrinterEvolutionary.activePrintToConsole();
//            System.out.println("Iteracion " + i + " Coste: " + listOFResults.get(i) + " Iteracion : " + listOFIterations.get(i));
//            PrinterEvolutionary.activePrintToFile();

        }

        getMeasureAndDesviation(listOFResults,listOFIterations);
    }
}
