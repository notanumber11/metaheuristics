package TabuAlgorithm;

import General.Solution;
import Utilities.PrinterTabu;
import Utilities.Timer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by denis on 11/11/16.
 */

public class Tabu {

    public final int tenencia = 100;
    public final int numberOfIterations = 10000;

    private Solution m_Solution;
    private int size;
    private ArrayList<ArrayList<Integer>> m_ExplorerPath = new ArrayList();


    public int iterationNumber = 0;
    public int iterationsWithoutImprove = 0;
    public int resetNumber = 0;
    public int bestEverCost = 0;
    public int bestIteration = 0;

    private Timer timer = new Timer();

    private ArrayList<Point> tabuList = new ArrayList<Point>(100);



    public Tabu(Solution solution){
        this.m_Solution = solution;
        resetExplorerPathMatrix();
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



    public void startTabu(){
        timer.start();
        PrinterTabu.initPrint(m_Solution);
        bestEverCost = m_Solution.getCost();
        while(iterationNumber<numberOfIterations){
            if(iterationsWithoutImprove==tenencia){
                iterationsWithoutImprove = 0;
                // The matriz solution is the last optimal solution
                getLastSolution();
                resetNumber++;
              //  tabuList.clear();
                PrinterTabu.printReset(resetNumber);
            }
            generateIteration();
        }
        getLastSolution();
        PrinterTabu.printFinalSolution(m_Solution,bestIteration);
        timer.end();
      //  Utilities.PrinterTabu.printMatriz(m_ExplorerPath);
    }

    // Changes for practica2
    public void generateIteration(){
        iterationNumber++;
        int  actualCost= 0;
        int bestCost = 10000000;
        int finalPosX = 0;
        int finalPosY = 0;

        for(int i = 0 ; i < size; i++){
            for(int j = 0; j< i; j++){
                Collections.swap(m_Solution.m_MatrizSolution, i, j);
                actualCost = m_Solution.getCost();
                Collections.swap(m_Solution.m_MatrizSolution, j, i);
                if(actualCost<bestCost){
                    Point point = new Point(i,j);
                    // Check if the point is in the tabu list
                    if(!tabuList.contains(point)){
                        bestCost = actualCost;
                        finalPosX = i;
                        finalPosY = j;
                    }
                }
            }
        }

        if(tabuList.size()<tenencia){
            tabuList.add(new Point(finalPosX,finalPosY));
        }
        else{
            tabuList.remove(0);
            tabuList.add(new Point(finalPosX,finalPosY));
        }

        Collections.swap(m_Solution.m_MatrizSolution, finalPosX, finalPosY);


        if(bestEverCost<=bestCost){
            iterationsWithoutImprove++;
        }
        // If the neighbour improve the solution
        else{
            addNewSolution();
            bestIteration = iterationNumber;
            iterationsWithoutImprove = 0;
            bestEverCost = bestCost;
        }

       // Utilities.PrinterTabu.printTabu(tabuList);
        PrinterTabu.printIteration(m_Solution,iterationNumber,finalPosX,finalPosY,iterationsWithoutImprove, tabuList);

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
}
