import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by denis on 7/01/17.
 */
public class Practica4Improvement extends Practica4Random {


    double randomPercentage = 0.15;


    private int maxIterationsWithoutImprove;
    private int iterationsWithoutImprove = 0;
    private double percentageOfIterationsWithoutImprove = 0;


    public Practica4Improvement(String distancesPath) {

        super(distancesPath);
        System.out.println("Ejecutando PRACTICA MEJORADA");

        // Size of parameters
        numberOfIndividuals = 150;
        numberOfIterations = 1000;

        percentageOfIterationsWithoutImprove = 0.05;
        maxIterationsWithoutImprove  =  (int)((double) numberOfIterations * 0.1);

        // Changing replace method
        percentageOfElitism = 0.06;
        sizeOfElitism =  percentageOfElitism*numberOfIndividuals;

        // Changing the initial data ( now we use voraz, random, and for the rest of the individuals
        // We calculate for each one, the half of the citys with random, and the other half with voraz
        randomPercentage = 0;
        vorazPercentage = 1;

        // Changing the probability of crossover and mutatin
        probabilityOfCrossover = 0.95;
        probabilityOfMutation = 0.05;


    }


    @Override
    public void start(){
        // We obtain the first
        loadInitialData();
        // Init the best solution
        bestSolutionEver = allIndividuals.get(0);
        bestIterationEver = 1;

        // Iterations
        while(iteration< numberOfIterations){
            // Obtain intermediate individuals and bestIndividuals
            obtainBestElements((int)sizeOfElitism);

            if(iterationsWithoutImprove==maxIterationsWithoutImprove){
                int mean = 0;
                int initSize = allIndividuals.size();
                for(int i =0 ; i < allIndividuals.size(); i++){
                    mean +=allIndividuals.get(i).getCost();
                }

                mean /=allIndividuals.size();
                for (Citys city:allIndividuals) {
                    if(city.getCost()<=mean){
                        allIndividuals.remove(city);
                    }
                }

                while(allIndividuals.size()<initSize){
                    Citys city = new Citys(m_Distances,0.5);
                    allIndividuals.add(city);
                }

                probabilityOfMutation = probabilityOfMutation*2;

            }

            // Make the binary tournament
            binaryTournament();
            //Make the crossover
            orderCrossover();
            //Make the mutation
            mutation();
            // Make the obtainBestElements
            replace();
        }
        NewPrinter.printBestIteration(bestSolutionEver, bestIterationEver);

    }

    @Override
    public void loadInitialData() {
        System.out.println("POBLACION INICIAL");
        // Obtain initial data
        for(int i = 0; i < randomPercentage*numberOfIndividuals; i++ ){
            Citys citys = new Citys(m_Distances,false,false);
            allIndividuals.add(citys);
        }


        for(int i = 0; i < vorazPercentage*numberOfIndividuals; i++){
            Citys citys = new Citys(m_Distances,false,true);
            allIndividuals.add(citys);
        }

        for(int i = 0; i < (1- vorazPercentage+randomPercentage)*numberOfIndividuals; i++){
            Citys citys = new Citys(m_Distances,0.5);
            allIndividuals.add(citys);
        }


        NewPrinter.printIndividuals(allIndividuals);
    }


    // Replace the elements the last iteration with the news
    public void replace() {
        System.out.println("ITERACION: "+iteration+", REEMPLAZO");
        allIndividuals = (ArrayList<Citys>) intermediateIndividuals.clone();
        Collections.sort(allIndividuals);
        Collections.sort(bestIndividuals);

        if(allIndividuals.get(0).getCost()<bestSolutionEver.getCost()){
            bestSolutionEver = allIndividuals.get(0);
            bestIterationEver = iteration;
            iterationsWithoutImprove = 0;
        }
        else{
            iterationsWithoutImprove++;
        }

        for(int i = 0; i  < bestIndividuals.size(); i++){
            allIndividuals.add(i,bestIndividuals.get(bestIndividuals.size()-1-i));
        }

        NewPrinter.printIndividuals(allIndividuals);

    }

}
