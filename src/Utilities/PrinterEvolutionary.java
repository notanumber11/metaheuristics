package Utilities;

import General.Citys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by denis on 5/01/17.
 */
public class PrinterEvolutionary {

    public static NumberFormat formatter = new DecimalFormat("#0.000000");

    private static PrintStream ps_console = System.out;

    public static void activePrintToConsole() {
        // Set console print stream.
        System.setOut(ps_console);
    }

    public static void activePrintToFile(String pathFile) {

        File file2 = new File(pathFile);
        file2.delete();
        File file = new File(pathFile);
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

    public static void printIndividuals(ArrayList<Citys> Individuals){
        for(int i = 0; i < Individuals.size(); i++){
            System.out.print("INDIVIDUO " + i + " = {FUNCION OBJETIVO (km): " + Individuals.get(i).getCost() + ", ");
            printWay("RECORRIDO: ",Individuals.get(i));
            System.out.println("}");
        }
        System.out.println();
    }

    public static void printWay(String initText ,Citys citys) {
        System.out.print(initText);
        for(int j = 0; j < citys.m_Citys.size() ; j++){
            System.out.print(citys.m_Citys.get(j)+ " ");
        }
    }

    public static void printTournament(int i, int number1, int number2, int winner) {
        System.out.println(	"\tTORNEO "+i+": "+number1+" "+number2+" GANA " + winner);
    }


    public static void printBestIteration(Citys bestSolutionEver, int bestIterationEver) {
        System.out.println();
        System.out.println();
        System.out.println("MEJOR SOLUCION: ");
        PrinterEvolutionary.printWay("RECORRIDO: " ,bestSolutionEver);
        System.out.println();
        System.out.println("FUNCION OBJETIVO (km): "+bestSolutionEver.getCost());
        System.out.println("ITERACION: "+ bestIterationEver);
    }


}
