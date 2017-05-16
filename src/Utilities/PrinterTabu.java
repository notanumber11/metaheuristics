package Utilities;

import General.Solution;

import java.awt.*;
import java.util.ArrayList;


/**
 * Created by denis on 11/11/16.
 */
public class PrinterTabu {

    private PrinterTabu(){}


    public static void initPrint(Solution solution){
        System.out.println("RECORRIDO INICIAL");
        System.out.print("\tRECORRIDO: ");
        printTrace(solution);
        System.out.println("");
    }

    public static void printIteration(Solution solution, int numberIteration, int posx, int posy, int iteractionsWithoutImprove, ArrayList<Point> tabu){
        System.out.println("ITERACION: " + numberIteration);
        System.out.println("\tINTERCAMBIO: ("+posx + ", " + posy + ")");
        System.out.print("\tRECORRIDO: ");
        printTrace(solution);
        System.out.println("\tITERACIONES SIN MEJORA: " + iteractionsWithoutImprove);
        printTabu(tabu);
        System.out.println();
    }

    public static void printReset(int resetNumber){
        System.out.println("***************");
        System.out.println("REINICIO: " + resetNumber);
        System.out.println("***************");
        System.out.println();
    }

    public static void printTabu(ArrayList<Point> tabu){
        System.out.println("\tLISTA TABU:");
        for(int i=0;i < tabu.size(); i++){
            System.out.println("\t"+ (int)tabu.get(i).getX() + " " + (int)tabu.get(i).getY());
        }
    }


    public static void printFinalSolution(Solution solution, int iteracion){
        System.out.println("MEJOR SOLUCION: ");
        System.out.print("\tRECORRIDO: ");
        printTrace(solution);
        System.out.println("\tITERACION: " + iteracion);
    }

    private static void printTrace(Solution solution) {
        int i = 0;
        for (i=0;i<solution.m_MatrizSolution.size()-1;i++) {
            System.out.print(solution.m_MatrizSolution.get(i)+" ");
        }
        System.out.println(solution.m_MatrizSolution.get(i)+ " ");
        System.out.println("\tCOSTE (km): " + solution.getCost());
    }




}
