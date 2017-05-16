package EvolutionaryAlgorithm;

import General.Citys;
import Utilities.PrinterEvolutionary;

import java.util.Random;

/**
 * Created by denis on 7/01/17.
 */
public class BaseEvolutionaryA extends BaseEvolutionary {

    private Random m_RandomGenerator = new Random();

    public BaseEvolutionaryA(String distancesPath) {
        super(distancesPath);
    }

    @Override
    public void loadInitialData() {
        System.out.println("POBLACION INICIAL");
        // Obtain initial data
        for(int i = 0; i < (1-vorazPercentage)*numberOfIndividuals; i++ ){
            Citys citys = new Citys(m_Distances,false,false);
            allIndividuals.add(citys);
        }
        for(int i = 0; i < vorazPercentage*numberOfIndividuals; i++){
            Citys citys = new Citys(m_Distances,false,true);
            allIndividuals.add(citys);
        }
        PrinterEvolutionary.printIndividuals(allIndividuals);
    }

    @Override
    protected int getTornamentNumber(){
       return m_RandomGenerator.nextInt(numberOfIndividuals);
    }

    @Override
    protected double getNextDoubleNumber(){
        return  m_RandomGenerator.nextDouble();
    }

    @Override
    protected int getCrossOverNumber(){

        return m_RandomGenerator.nextInt(Citys.SIZE);
    }

}
