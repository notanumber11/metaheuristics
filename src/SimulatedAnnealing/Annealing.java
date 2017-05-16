package SimulatedAnnealing;

import General.Distances;
import General.Solution;
import Utilities.PrinterAnnealing;
import Utilities.Timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by denis on 11/11/16.
 */

public class Annealing {

    private Solution m_Solution;
    private int size;
    private ArrayList<ArrayList<Integer>> m_ExplorerPath = new ArrayList();
    public int iterationNumber = 0;

    //-------------------------------------------

    public double initTemperature = 0;
    public double temperature = 0;
    public double mu = 0.01;
    public double redondito = 0.5;
    public double k  = 0;
    public int stopCritery = 10000;
    public double delta = 0;
    public double exponencial = 0;
    public int numeroEnfriamiento = 0;
    public int iteration = 0;
    public int intercambioX = 0;
    public int intercambioY = 0;
    public boolean flagAceptada = false;
    private Random m_RandomGenerator = new Random();
    public int bestIteration = 0;
    public int bestFinalResultEver = 10000000;


    //---------------------------------------------

    public ArrayList<Integer> actualSolution = new ArrayList(99);
    public ArrayList<Integer> candidateSolution = new ArrayList(99);
    public Distances m_DistanceMatriz;

    //--------------------------------------------

    public final int LIMITE_ACEPTACIONES = 20;
    public final int LIMITE_CANDIDATAS  = 80;
    public int numeroAceptaciones = 0;
    public int numeroCandidatas = 0;

    //--------------------------------------------


    private Timer timer = new Timer();


    public double calculateInitTemperature(){
        initTemperature = (mu/-Math.log(redondito))* m_Solution.getCost();
        m_Solution.initTemperature = initTemperature;
        return  initTemperature;
    }

    public double enfriamientoCauchy(){
        numeroEnfriamiento++;
        numeroCandidatas = 0;
        numeroAceptaciones = 0;
        temperature = initTemperature/(1+numeroEnfriamiento);
        PrinterAnnealing.printEnfriamiento(this);
        return temperature;
    }

    public Annealing(Solution solution){
        timer.start();
        //Init solution
        this.m_Solution = solution;
        // Init actual solution
        actualSolution = m_Solution.m_MatrizSolution;
        // Init distance matrix
        m_DistanceMatriz = m_Solution.m_DistanceMatriz;

        // ResetExplorer
        resetExplorerPathMatrix();
        timer.end();
    }

    //Reutilizable
    private void resetExplorerPathMatrix() {
        m_ExplorerPath.clear();
        size = m_Solution.SIZE;
        for(int i =0;i<size-1;i++){
            ArrayList row = new ArrayList();
            for(int j=0;j<(i+1);j++){
                row.add(0);
            }
            m_ExplorerPath.add(row);
        }
    }



    public void startAnnealing(){
        temperature = calculateInitTemperature();
        PrinterAnnealing.initPrint(this);

        int costActualSolution = m_Solution.getCost();
        actualSolution = m_Solution.m_MatrizSolution;

        double actualRandom = 0;

            for(iteration = 0; iteration < stopCritery ; iteration++){
                if(iteration == 82){
                    int a = 3;
                    //System.out.printf("Iteración " + iteration);
                }
                flagAceptada = false;
                // Selecciono solucion
                candidateSolution = selectSolution();
                // Obtengo coste
                delta = getCost(candidateSolution)-getCost(actualSolution);
                // Obtengo valor random entre 0 y 1
                actualRandom = obtainRandom();
                // Evaluo si actualizo mejors solución
                exponencial = Math.pow(Math.E,-delta /temperature);
                if(actualRandom< exponencial || delta < 0){
                    flagAceptada = true;
                    numeroAceptaciones = numeroAceptaciones +1;
                    actualSolution = clone(candidateSolution);
                    if(getCost(actualSolution)<bestFinalResultEver){
                        bestIteration = iteration;
                        bestFinalResultEver = getCost(actualSolution);
                    }

                }

                numeroCandidatas++;
                PrinterAnnealing.printIteracion(this);

                // Criterio de enfriamiento 1
            if(numeroAceptaciones == LIMITE_ACEPTACIONES){
                temperature = enfriamientoCauchy();
            }
            // Criterio de enfriamiento 2
            else if (numeroCandidatas == LIMITE_CANDIDATAS){
                temperature = enfriamientoCauchy();
            }
                if(iteration == 82){
                   // System.out.printf("Iteración " + iteration);
                    int a = 3;
                }

            }
            PrinterAnnealing.printBestSolution(this);
      //  Utilities.PrinterAnnealing.printMatriz(m_ExplorerPath);
    }

    public double obtainRandom(){
        if(m_Solution.randomReadByFile){
            return m_Solution.randomNumbers.get(iteration);
        }
        return m_RandomGenerator.nextDouble();
    }

    // Changes for practica3
    public ArrayList<Integer> selectSolution(){
        iterationNumber++;
        int  actualCost= 0;
        int bestCost = 10000000;

        candidateSolution = clone(actualSolution);
        for(int i = 0 ; i < size; i++){
            for(int j = 0; j< i; j++){
                Collections.swap(candidateSolution, i, j);
                actualCost = getCost(candidateSolution);
                Collections.swap(candidateSolution, j, i);
                if(actualCost<bestCost){
                        bestCost = actualCost;
                        intercambioX = i;
                        intercambioY = j;
                }
            }
        }


      //  System.out.println("El intercambio es " + intercambioX + " , " + intercambioY);
        Collections.swap(candidateSolution, intercambioX, intercambioY);
        return candidateSolution;

    }

    public void addNewSolution(){
        ArrayList<Integer> newSol = new ArrayList<>();
        for(int i=0;i<m_Solution.m_MatrizSolution.size();i++){
            newSol.add(m_Solution.m_MatrizSolution.get(i));
        }
        m_Solution.solutions.add(newSol);
    }

    public void getLastSolution(){
        ArrayList<Integer> bestSol  = m_Solution.solutions.get(m_Solution.solutions.size()-1);
        for(int i=0;i<m_Solution.m_MatrizSolution.size();i++){
            m_Solution.m_MatrizSolution.set(i,bestSol.get(i));
        }
    }

    public ArrayList<Integer> clone(ArrayList<Integer> arrayList) {
        ArrayList<Integer> cloneArrayList = new ArrayList<Integer>();
        for(int i = 0; i < arrayList.size();i++){
            cloneArrayList.add(arrayList.get(i));
        }
        return cloneArrayList;
    }

    public int getCost(ArrayList<Integer> solutionArray){
        int costFirstStep = m_DistanceMatriz.getDistance(solutionArray.get(0),0);
        int costLastStep = m_DistanceMatriz.getDistance(solutionArray.get(solutionArray.size()-1),0);
        int costMiddleStep = 0;
        for(int i = 1; i< solutionArray.size(); i++ ){
            costMiddleStep +=m_DistanceMatriz.getDistance(solutionArray.get(i), solutionArray.get(i-1));
        }

        int sol = costFirstStep + costLastStep + costMiddleStep;
        return sol;
    }
}
