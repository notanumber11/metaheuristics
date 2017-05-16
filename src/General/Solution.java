package General;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.StrictMath.floor;

/**
 * Created by denis on 11/11/16.
 */

public class Solution {

    public static int SIZE = -1;
    public ArrayList<Integer> m_MatrizSolution = new ArrayList();
    public ArrayList<Integer> m_Trace = new ArrayList();
    public boolean solutionReadByFile = false;
    public ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
    public ArrayList<Double> randomNumbers = new ArrayList<>();
    public boolean randomReadByFile = false;


    private Random m_RandomGenerator = new Random();
    public Distances m_DistanceMatriz;
    public double initTemperature = 0;

    public Solution(Distances m_DistanceMatriz){
        SIZE = m_DistanceMatriz.m_DistanceMatriz.size();
        this.m_DistanceMatriz = m_DistanceMatriz;
        generateRandomSolution(SIZE);
//        generateVorazSolution();
        solutions.add(m_MatrizSolution);

    }


    public Solution(Distances m_DistanceMatriz, String path){
        this.m_DistanceMatriz = m_DistanceMatriz;
        generateVorazSolution();
        readRandomNumbers(path);
        solutions.add(m_MatrizSolution);
    }

    public void  generateVorazSolution(){
       // Half solutions are random
        generateRandomSolution(SIZE/2);

        int closerCity = getCloserCity(0);
        for(int i = SIZE/2; i < SIZE-1 ; i++){
            closerCity = getCloserCity(closerCity);
        }
    }

    public int getCloserCity(int actualCity){
        int nextCity = -1;

        int distance = 10000000;
        for(int i=1;i<=SIZE;i++){
            if(i==actualCity){
                continue;
            }

            if(!m_MatrizSolution.contains(i)){
                if(m_DistanceMatriz.getDistance(i,actualCity)<distance){
                   nextCity = i;
                    distance = m_DistanceMatriz.getDistance(i,actualCity);
                }
            }
        }
        m_MatrizSolution.add(nextCity);
       // System.out.print(nextCity + " ");
        return nextCity;
    }

    private void generateRandomSolution(int size) {
        int randomInt;
        for(int i=0;i<size;i++){
            randomInt = m_RandomGenerator.nextInt(SIZE)+1;
            while(m_MatrizSolution.size()<=i){
                if(!m_MatrizSolution.contains(randomInt)){
                    m_MatrizSolution.add(i,randomInt);
                }
                else{
                    randomInt++;
                    if(randomInt>SIZE){
                        randomInt=1;
                    }
                }
            }
        }
    }



    public int getCost(){
        int costFirstStep = m_DistanceMatriz.getDistance(m_MatrizSolution.get(0),0);
        int costLastStep = m_DistanceMatriz.getDistance(m_MatrizSolution.get(m_MatrizSolution.size()-1),0);
        int costMiddleStep = 0;
        for(int i = 1; i< m_MatrizSolution.size(); i++ ){
            costMiddleStep +=m_DistanceMatriz.getDistance(m_MatrizSolution.get(i), m_MatrizSolution.get(i-1));
        }

        int sol = costFirstStep + costLastStep + costMiddleStep;
        return sol;
    }


    public boolean readRandomNumbers(String path){
        randomReadByFile = true;
        Scanner input = null;
        try {
            input = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo " + path);
            e.printStackTrace();
            return false;
        }
        int line = 0;
        while (input.hasNextLine() ) {
            double value = Double.parseDouble(input.nextLine());
            randomNumbers.add(value);
        }
        return true;
    }



    public boolean readTraceFromFilePractica3(String path){
        solutionReadByFile = true;
        m_MatrizSolution.clear();
        Scanner input = null;
        try {
            input = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo " + path);
            e.printStackTrace();
            return false;
        }
        int line = 0;
        while (input.hasNextLine() && line < SIZE) {
            line++;
            double value = Double.parseDouble(input.nextLine());
            int randomInt = (int) (1 + floor(value * SIZE));
            while (m_MatrizSolution.contains(randomInt)) {
                randomInt++;
                if (randomInt > SIZE) {
                    randomInt = 1;
                }
            }
            m_MatrizSolution.add(randomInt);
        }
        return true;
    }


    public boolean readTraceFromFilePractica2(String path){
        solutionReadByFile = true;
        m_MatrizSolution.clear();
        Scanner input = null;
        try {
            input = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo " + path);
            e.printStackTrace();
            return false;
        }
        int line = 0;
        while (input.hasNextLine() && line < SIZE) {
            line++;
            double value = Double.parseDouble(input.nextLine());
            int randomInt = (int) (1 + floor(value * SIZE));
            while (m_MatrizSolution.contains(randomInt)) {
                randomInt++;
                if (randomInt > SIZE) {
                    randomInt = 1;
                }
            }
            m_MatrizSolution.add(randomInt);
        }
        return true;
    }
}
