import com.sun.org.apache.bcel.internal.generic.InstructionTargeter;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by denis on 11/11/16.
 */
public class Utilities {

    private Utilities(){}


    public static void generateOrderSolutionTest(Solution solution) {
        solution.m_MatrizSolution.clear();
        for(int i = 0; i< solution.SIZE; i++){
            solution.m_MatrizSolution.add(i,i+1);
        }
    }

    private static PrintStream ps_console = System.out;

    public static void activePrintToConsole() {
        // Set console print stream.
        System.setOut(ps_console);
    }

    public static void activePrintToFile() {

        File file2 = new File("results.txt");
        file2.delete();
        File file = new File("results.txt");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Create new print stream for file.
        PrintStream ps = new PrintStream(fos);

        // Set file print stream.
        System.setOut(ps);
    }


    public static void generateBestSolutionTest(Solution solution) throws Exception {
        //842695731
        if(solution.m_MatrizSolution.size()==9){
            solution.m_MatrizSolution.clear();
            solution.m_MatrizSolution.add(0,8);
            solution.m_MatrizSolution.add(1,4);
            solution.m_MatrizSolution.add(2,2);
            solution.m_MatrizSolution.add(3,6);
            solution.m_MatrizSolution.add(4,9);
            solution.m_MatrizSolution.add(5,5);
            solution.m_MatrizSolution.add(6,7);
            solution.m_MatrizSolution.add(7,3);
            solution.m_MatrizSolution.add(8,1);
        }
        else{
            throw new Exception();
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
        printTrace(solution);
    }



    public static void printPermuta(Solution solution,int posX,int posY,int numberOfPermuta){
        System.out.print("\tVECINO V_"+numberOfPermuta+" -> Intercambio: ("+posX+", "+posY+");");
        printTrace(solution);
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
