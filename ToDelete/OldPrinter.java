import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by denis on 11/11/16.
 */
public class OldPrinter {

    private OldPrinter(){}


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



    public static void printPermuta(Solution solution,int posX,int posY,int numberOfPermuta){
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


/*    SOLUCIÓN INICIAL:
    RECORRIDO: 76 54 34 33 55 12 45 57 56 41 15 66 72 35 99 28 92 61 38 52 4 74 18 63 91 75 32 16 42 70 90 89 19 50 36 47 31 62 95 46 7 94 80 65 13 5 60 53 58 82 3 43 26 2 69 84 14 25 37 68 48 51 22 11 30 21 40 59 44 64 23 85 98 6 93 39 9 27 29 8 77 73 10 49 88 67 96 78 1 87 86 81 97 17 24 83 71 20 79
    FUNCION OBJETIVO (km): 4636
    TEMPERATURA INICIAL: 66.883342*/

    public static void initPrint(Practica3 practica3){
        System.out.println("SOLUCION INICIAL");
        printRecorrido(practica3);
        System.out.println("\tFUNCION OBJETIVO (km): " + practica3.getCost(practica3.actualSolution));
        System.out.println("\tTEMPERATURA INICIAL: " + formatter.format(practica3.initTemperature));
        System.out.println("");
    }




    static NumberFormat formatter = new DecimalFormat("#0.000000");

    private static void printRecorrido(Practica3 practica3) {
        System.out.print("\tRECORRIDO: ");
        int i = 0;
        for (i=0;i<practica3.actualSolution.size()-1;i++) {
            System.out.print(practica3.actualSolution.get(i)+" ");
        }
        System.out.println(practica3.actualSolution.get(i)+ " ");
    }


    public static void printIteracion(Practica3 practica3){
        System.out.println("ITERACION: " + (practica3.iteration+1));

        System.out.println("\tINTERCAMBIO: " + "("+practica3.intercambioX + ", " + practica3.intercambioY +")");
        printRecorrido(practica3);
        System.out.println("\tFUNCION OBJETIVO (km): " + practica3.getCost(practica3.candidateSolution));
        System.out.println("\tDELTA: " + (int)practica3.delta);
        System.out.println("\tTEMPERATURA: " + formatter.format(practica3.temperature));
        System.out.println("\tVALOR DE LA EXPONENCIAL: " + formatter.format(practica3.exponencial));
        if(practica3.flagAceptada){
            System.out.println("\tSOLUCION CANDIDATA ACEPTADA");
        }
        System.out.println("\tCANDIDATAS PROBADAS: " + practica3.numeroCandidatas +", ACEPTADAS: "+practica3.numeroAceptaciones);
        System.out.println();
    }

    public static void printEnfriamiento(Practica3 practica3){
        System.out.println("============================");
        System.out.println("ENFRIAMIENTO: " + practica3.numeroEnfriamiento);
        System.out.println("============================");
        System.out.println("TEMPERATURA: " + formatter.format(practica3.temperature));
        System.out.println();
    }

    public static void printBestSolution(Practica3 practica3){
        System.out.println("MEJOR SOUCION:");
        printRecorrido(practica3);
        System.out.println("\tFUNCION OBJETIVO (km): " + practica3.bestFinalResultEver);
        System.out.println("\tITERACION: "+ (practica3.bestIteration+1));
        System.out.println("\tmu = " +practica3.mu + " phi = "+ practica3.redondito);
    }

/*    ITERACION: 1
        INTERCAMBIO: (98, 65)
        RECORRIDO: 76 54 34 33 55 12 45 57 56 41 15 66 72 35 99 28 92 61 38 52 4 74 18 63 91 75 32 16 42 70 90 89 19 50 36 47 31 62 95 46 7 94 80 65 13 5 60 53 58 82 3 43 26 2 69 84 14 25 37 68 48 51 22 11 30 79 40 59 44 64 23 85 98 6 93 39 9 27 29 8 77 73 10 49 88 67 96 78 1 87 86 81 97 17 24 83 71 20 21
        FUNCION OBJETIVO (km): 4492
        DELTA: -144
        TEMPERATURA: 66.883342
        VALOR DE LA EXPONENCIAL: 8.610673
        SOLUCION CANDIDATA ACEPTADA
        CANDIDATAS PROBADAS: 1, ACEPTADAS: 1*/



}
