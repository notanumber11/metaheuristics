package Utilities;

import General.Solution;
import SimulatedAnnealing.Annealing;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by denis on 11/11/16.
 */
public class PrinterAnnealing {

    private PrinterAnnealing(){}


    public static void generateOrderSolutionTest(Solution solution) {
        solution.m_MatrizSolution.clear();
        for(int i = 0; i< solution.SIZE; i++){
            solution.m_MatrizSolution.add(i,i+1);
        }
    }



    public static void printSolutionPath(String previousText,Solution solution) {
        System.out.print(previousText + " [ ");
        for (int value: solution.m_MatrizSolution) {
            System.out.print(" "+ value + " ");
        }
        System.out.println("] ");
    }

    public static void printSolutionTrace(Solution solution,int number) {
        System.out.print("SOLUCION S_" + number + " ->");
       // printRecorrido(solution);
    }



    public static void printPermuta(Solution solution, int posX, int posY, int numberOfPermuta){
        System.out.print("\tVECINO V_"+numberOfPermuta+" -> Intercambio: ("+posX+", "+posY+");");
      //  printRecorrido(solution);
    }



    public static void printMatriz(ArrayList<ArrayList<Integer>> matriz){
        System.out.println("********************************************");
        for(int i = 0; i < matriz.size();i++){
            for(int j=0;j < matriz.get(i).size();j++){
                //    System.out.print("[ " + i + " ] " + "[ " + j + " ] " + " = " + m_ExplorerPath.get(i).get(j));
                System.out.print(matriz.get(i).get(j) + "  ");
            }
            System.out.println();
        }
        System.out.println("********************************************");
    }



    public static void initPrint(Annealing annealing){
        System.out.println("SOLUCION INICIAL");
        printRecorrido(annealing);
        System.out.println("\tFUNCION OBJETIVO (km): " + annealing.getCost(annealing.actualSolution));
        System.out.println("\tTEMPERATURA INICIAL: " + formatter.format(annealing.initTemperature));
        System.out.println("");
    }




    static NumberFormat formatter = new DecimalFormat("#0.000000");

    private static void printRecorrido(Annealing annealing) {
        System.out.print("\tRECORRIDO: ");
        int i = 0;
        for (i=0; i< annealing.actualSolution.size()-1; i++) {
            System.out.print(annealing.actualSolution.get(i)+" ");
        }
        System.out.println(annealing.actualSolution.get(i)+ " ");
    }


    public static void printIteracion(Annealing annealing){
        System.out.println("ITERACION: " + (annealing.iteration+1));

        System.out.println("\tINTERCAMBIO: " + "("+ annealing.intercambioX + ", " + annealing.intercambioY +")");
        printRecorrido(annealing);
        System.out.println("\tFUNCION OBJETIVO (km): " + annealing.getCost(annealing.candidateSolution));
        System.out.println("\tDELTA: " + (int) annealing.delta);
        System.out.println("\tTEMPERATURA: " + formatter.format(annealing.temperature));
        System.out.println("\tVALOR DE LA EXPONENCIAL: " + formatter.format(annealing.exponencial));
        if(annealing.flagAceptada){
            System.out.println("\tSOLUCION CANDIDATA ACEPTADA");
        }
        System.out.println("\tCANDIDATAS PROBADAS: " + annealing.numeroCandidatas +", ACEPTADAS: "+ annealing.numeroAceptaciones);
        System.out.println();
    }

    public static void printEnfriamiento(Annealing annealing){
        System.out.println("============================");
        System.out.println("ENFRIAMIENTO: " + annealing.numeroEnfriamiento);
        System.out.println("============================");
        System.out.println("TEMPERATURA: " + formatter.format(annealing.temperature));
        System.out.println();
    }

    public static void printBestSolution(Annealing annealing){
        System.out.println("MEJOR SOLCION:");
        printRecorrido(annealing);
        System.out.println("\tFUNCION OBJETIVO (km): " + annealing.bestFinalResultEver);
        System.out.println("\tITERACION: "+ (annealing.bestIteration+1));
        System.out.println("\tmu = " + annealing.mu + " phi = "+ annealing.redondito);
    }



}
