import java.util.*;

/**
 * Created by denis on 5/01/17.
 */
public class Practica4 {

    // Input Parameters
    protected  int numberOfIndividuals = 100;
    // Care with this percentage
    protected   double vorazPercentage = 0.5;
    protected   double percentageOfElitism = 0.02;
    protected   double sizeOfElitism =  percentageOfElitism*numberOfIndividuals;
    protected   double probabilityOfCrossover = 0.9;
    protected   double probabilityOfMutation = 0.01;
    protected   int numberOfIterations = 1000;

    // Results
    protected Citys bestSolutionEver;
    protected int bestIterationEver;

    // Iteration number
    protected int iteration = 0;

    // Work parameters
    protected Distances m_Distances;
    protected ArrayList<Citys> allIndividuals = new ArrayList<>();
    protected ArrayList<Citys> intermediateIndividuals = new ArrayList<>();
    protected ArrayList<Citys> bestIndividuals = new ArrayList<>();

    public Practica4(String distancesPath){
        // Loading the distance matrix
        m_Distances = new Distances();
        m_Distances.LoadDistances(distancesPath);

    }

    public void start(){
        // We obtain the first
        loadInitialData();
        // Init the best solution
        bestSolutionEver = allIndividuals.get(0);
        bestIterationEver = 1;

        // Iterations
        while(iteration< numberOfIterations){


            // Obtain intermediate individuals and bestIndividuals
            obtainBestElements((int)sizeOfElitism);
            // Make the binary tournament
            binaryTournament();
            //Make the crossover
            orderCrossover();
            //Make the mutation
            mutation();
            // Make the obtainBestElements
            replace();
        }
        NewPrinter.printBestIteration(bestSolutionEver, bestIterationEver);

    }

    public void loadInitialData() {
        System.out.println("POBLACION INICIAL");
        // Obtain initial data
        for(int i = 0; i < (1-vorazPercentage)*numberOfIndividuals; i++ ){
            Citys citys = new Citys(m_Distances,true,false);
            allIndividuals.add(citys);
        }
        for(int i = 0; i <vorazPercentage * numberOfIndividuals; i++){
            Citys citys = new Citys(m_Distances,true,true);
            allIndividuals.add(citys);
        }
        NewPrinter.printIndividuals(allIndividuals);
    }



    // Obtain the best  elements from all the individuals
    public void obtainBestElements(int numberOfBestElements){

        ArrayList<Integer> arrayOfIndex = new ArrayList<>();
        ArrayList<Integer> distances = new ArrayList<>();

        for(int i = 0; i < numberOfBestElements ; i++){
            arrayOfIndex.add(i);
            distances.add(allIndividuals.get(i).getCost());
        }



        // Loop for all the individuals
        for(int i = numberOfBestElements; i < allIndividuals.size(); i++){
            int indexToReplace = 0;
            boolean newValue = false;
            int actualDistance = allIndividuals.get(i).getCost();

            // If the actual individual is best than any of the actual
            for(int j = 0; j < arrayOfIndex.size() ; j++){
                if(actualDistance < distances.get(j)){
                    newValue = true;
                    break;
                }
            }

            // If the actual individual is better we should replace it for the worst of the list
            if(newValue){
                for(int k = 1 ; k < distances.size(); k++){
                    if(distances.get(k)>distances.get(indexToReplace)){
                        indexToReplace = k;
                    }
                }
                arrayOfIndex.remove(indexToReplace);
                distances.remove(indexToReplace);
                arrayOfIndex.add(i);
                distances.add(allIndividuals.get(i).getCost());
            }

        }

        // Fill the bestIndividuals array
        bestIndividuals.clear();
        for(int i = 0; i < arrayOfIndex.size();i++){
            bestIndividuals.add(allIndividuals.get(arrayOfIndex.get(i)));
        }
    }


    public void binaryTournament(){
        intermediateIndividuals.clear();
        iteration++;
        System.out.println("ITERACION: "+ iteration + ", SELECCION");
        for(int i = 0; i < numberOfIndividuals - sizeOfElitism; i++){
            int number1 = -1;
            int number2= -2;
            int distance1 = -1;
            int distance2 = -1;
            int winner = -1;

            number1 = getTornamentNumber();
            number2 = getTornamentNumber();


            distance1 = allIndividuals.get(number1).getCost();
            distance2 = allIndividuals.get(number2).getCost();

            // If 1 if the best
            if(distance1<=distance2){
                intermediateIndividuals.add(allIndividuals.get(number1).clone());
                winner = number1;
            }
            else{
                intermediateIndividuals.add(allIndividuals.get(number2).clone());
                winner = number2;
            }
            NewPrinter.printTournament(i, number1, number2, winner);
        }
        System.out.println();
    }


    public void orderCrossover(){
        Citys citys1;
        Citys citys2;

        System.out.println("ITERACION: "+iteration+", CRUCE ");
        for(int i = 0; i < (numberOfIndividuals- sizeOfElitism) ; i+=2){

            citys1 = intermediateIndividuals.get(i);
            citys2 = intermediateIndividuals.get(i+1);
            double randomNumber = getNextDoubleNumber();

            System.out.println("\tCRUCE: ("+i+", "+(i+1)+") (ALEATORIO: "+String.format(Locale.ROOT, "%.6f", randomNumber)+")");
            System.out.print("\t\tPADRE: = {FUNCION OBJETIVO (km): "+citys1.getCost()+", ");
            NewPrinter.printWay("RECORRIDO: ",citys1);
            System.out.println("}");
            System.out.print("\t\tPADRE: = {FUNCION OBJETIVO (km): "+citys2.getCost()+", ");
            NewPrinter.printWay("RECORRIDO: ",citys2);
            System.out.println("}");



            if(randomNumber<= probabilityOfCrossover){
                int corte1 = getCrossOverNumber();
                int corte2 = getCrossOverNumber();
                System.out.println("\t\tCORTES: ("+corte1+", "+corte2+")");
                int temp;
                if(corte1>corte2){
                    temp = corte1;
                    corte1 = corte2;
                    corte2 = temp;
                }
                cut(citys1,citys2,corte1,corte2);
                System.out.print("\t\tHIJO: = {FUNCION OBJETIVO (km): "+citys1.getCost()+", ");
                NewPrinter.printWay("RECORRIDO: ",citys1);
                System.out.println("}");
                System.out.print("\t\tHIJO: = {FUNCION OBJETIVO (km): "+citys2.getCost()+", ");
                NewPrinter.printWay("RECORRIDO: ",citys2);
                System.out.println("}");

            }
            else{
                System.out.println("\t\tNO SE CRUZA");
            }
            System.out.println();
        }

    }

    private void cut(Citys citys1, Citys citys2,int index1, int index2){
        ArrayList<Integer> array1 ;
        ArrayList<Integer> array2 ;

        int size = citys1.m_Citys.size();

        array1 = new ArrayList<Integer>(citys1.m_Citys.subList(index1,index2+1));
        array2 = new ArrayList<Integer>(citys2.m_Citys.subList(index1,index2+1));

        ArrayList<Integer> numbersToAddToArray1 = new ArrayList<>();
        ArrayList<Integer> numbersToAddToArray2 = new ArrayList<>();

        for(int i = 0; i < citys2.m_Citys.size(); i++){
            int pos = (index2+1+i)%citys2.m_Citys.size();
            if(!array1.contains(citys2.m_Citys.get(pos))) {
                numbersToAddToArray1.add(citys2.m_Citys.get(pos));
            }

            if(!array2.contains(citys1.m_Citys.get(pos))) {
                numbersToAddToArray2.add(citys1.m_Citys.get(pos));
            }

        }

        int position = index2+1;
        boolean start = false;
        for(int i = 0; i < numbersToAddToArray1.size() ; i++){
            if(position>=size){
                position = position%size;
                start = true;
            }
            if(start){
                array1.add(position,numbersToAddToArray1.get(i));
                array2.add(position,numbersToAddToArray2.get(i));
            }
            else{
                array1.add(numbersToAddToArray1.get(i));
                array2.add(numbersToAddToArray2.get(i));
            }
            position = position +1;
        }




        citys1.m_Citys = array1;
        citys2.m_Citys = array2;

    }

    public void mutation(){
        System.out.println("ITERACION: "+ iteration + ", MUTACION");
        for(int i = 0; i < numberOfIndividuals- sizeOfElitism; i++){
            Citys citys = intermediateIndividuals.get(i);
            System.out.println("\tINDIVIDUO "+i);
            NewPrinter.printWay("\tRECORRIDO ANTES: " ,citys);
            System.out.println();
            for(int j = 0; j < citys.m_Citys.size();j++){
                double randomNumber = getNextDoubleNumber();
                System.out.print("\t\tPOSICION: "+j+" (ALEATORIO "+String.format(Locale.ROOT, "%.6f", randomNumber)+") ");
                if(randomNumber > probabilityOfMutation){
                    System.out.println("NO MUTA");
                }
                else{
                    int exchangeNumber = getCrossOverNumber();
                    System.out.println("INTERCAMBIO CON: "+exchangeNumber);

                    Collections.swap(citys.m_Citys,j,exchangeNumber);
                }
            }
            NewPrinter.printWay("\tRECORRIDO DESPUES: " ,citys);
            System.out.println("");
            System.out.println("");
        }
        System.out.println();
    }

    // Replace the elements the last iteration with the news
    public void replace() {
        System.out.println("ITERACION: "+iteration+", REEMPLAZO");
        allIndividuals = (ArrayList<Citys>) intermediateIndividuals.clone();
        Collections.sort(allIndividuals);
        Collections.sort(bestIndividuals);

        if(allIndividuals.get(0).getCost()<bestSolutionEver.getCost()){
            bestSolutionEver = allIndividuals.get(0);
            bestIterationEver = iteration;
        }

        for(int i = 0; i  < bestIndividuals.size(); i++){
            allIndividuals.add(i,bestIndividuals.get(bestIndividuals.size()-1-i));
        }

        NewPrinter.printIndividuals(allIndividuals);

    }

    public int getBestCost(){
        return bestSolutionEver.getCost();
    }

    public int getBestIterationEver(){
        return bestIterationEver;
    }

    protected int getTornamentNumber(){
        return (int)Math.floor(Reader.getNextCity()*numberOfIndividuals);
    }

    protected double getNextDoubleNumber(){
        return Reader.getNextCity();
    }

    protected int getCrossOverNumber(){
        return (int)Math.floor(Reader.getNextCity()*Citys.SIZE);
    }



}
