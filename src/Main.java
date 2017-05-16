
import EvolutionaryAlgorithm.BaseEvolutionary;
import EvolutionaryAlgorithm.BaseEvolutionaryA;
import EvolutionaryAlgorithm.BaseEvolutionaryB;
import General.Distances;
import General.Solution;
import SimulatedAnnealing.Annealing;
import TabuAlgorithm.Tabu;
import Utilities.PrinterEvolutionary;
import Utilities.Timer;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        // Load File with the distances between citys
        String distancesPath = "InputFiles/distancias_ce_100.txt";


        // Init distances and solution matrix, this is used to the tabu and annealing algorithm
        Distances distances = new Distances(distancesPath);
        Solution solution = new Solution(distances);


        // TABU ALGORITHM
        PrinterEvolutionary.activePrintToFile("OutputFiles/resultsTabu.txt");
        Tabu tabu = new Tabu(solution);
        tabu.startTabu();

        // ANNEALING ALGORITHM
        PrinterEvolutionary.activePrintToFile("OutputFiles/resultsAnnealing.txt");
        Annealing annealing = new Annealing(solution);
        annealing.startAnnealing();

        // EVOLUTIONARY ALGORITHM VERSION 1
        PrinterEvolutionary.activePrintToFile("OutputFiles/resultsEvolutionaryA.txt");
        BaseEvolutionary baseEvolutionary = new BaseEvolutionaryA(distancesPath);
        baseEvolutionary.start();
//
        // EVOLUTIONARY ALGORITHM VERSION 2
        PrinterEvolutionary.activePrintToFile("OutputFiles/resultsEvolutionaryB.txt");
        baseEvolutionary = new BaseEvolutionaryB(distancesPath);
        baseEvolutionary.start();

        }





}


