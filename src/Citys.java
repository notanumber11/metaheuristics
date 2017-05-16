import java.util.ArrayList;
import java.util.Random;

/**
 * Created by denis on 5/01/17.
 */
public class Citys implements Cloneable,Comparable<Citys> {
    public static  int SIZE = -1;
    public ArrayList<Integer> m_Citys = new ArrayList<>();
    public Distances m_DistanceMatriz;


    private Citys(){}

    public Citys(Distances distances,boolean readFromFile,boolean voraz){
        this.m_DistanceMatriz = distances;
        if(readFromFile){
            if(voraz){
                generateVorazSolution(getCityValueFromFile());
            }
            else{
                getInitialCitysFromFile();
            }
        }
        else{
            if(voraz){
                Random m_RandomGenerator = new Random();
                int  randomInt = m_RandomGenerator.nextInt(SIZE)+1;
                generateVorazSolution(randomInt);
            }
            else{
                generateRandomSolution(SIZE);
            }
        }
    }


    public Citys(Distances distances, double percentage){
        this.m_DistanceMatriz = distances;
        // A percentage of the individual is random
        generateRandomSolution((int)(SIZE * percentage));
        //A percentage of the individual is voraz
        int firstCity = m_Citys.get(m_Citys.size()-1);
        m_Citys.remove(m_Citys.size()-1);
        generateVorazSolution(firstCity);
    }



    private int getCityValueFromFile(){
        return (int)Math.floor(Reader.getNextCity()*9)+1;
    }


    
    public void getInitialCitysFromFile(){
        for(int i = 0; i < SIZE; i++){
            int randomInt = getCityValueFromFile();
            while (m_Citys.contains(randomInt)) {
                randomInt++;
                if (randomInt > SIZE) {
                    randomInt = 1;
                }
            }
            m_Citys.add(randomInt);
        }
    }


    
    
    public void  generateRandomSolution(int size){
        int randomInt;
        Random m_RandomGenerator = new Random();
        for(int i=0;i<size;i++){
            randomInt = m_RandomGenerator.nextInt(size)+1;
            while(m_Citys.size()<=i){
                if(!m_Citys.contains(randomInt)){
                    m_Citys.add(i,randomInt);
                }
                else{
                    randomInt++;
                    if(randomInt>size){
                        randomInt=1;
                    }
                }
            }
        }
    }

    public void  generateVorazSolution(int firstCity){
        m_Citys.add(firstCity);
        int closerCity = firstCity; /*getCloserCity(m_RandomGenerator.nextInt(SIZE)+1);*/
        while(m_Citys.size()<SIZE){
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

            if(!m_Citys.contains(i)){
                if(m_DistanceMatriz.getDistance(i,actualCity)<distance){
                    nextCity = i;
                    distance = m_DistanceMatriz.getDistance(i,actualCity);
                }
            }
        }
        m_Citys.add(nextCity);
        return nextCity;
    }

    public int getCost(){
        int costFirstStep = m_DistanceMatriz.getDistance(m_Citys.get(0),0);
        int costLastStep = m_DistanceMatriz.getDistance(m_Citys.get(m_Citys.size()-1),0);
        int costMiddleStep = 0;
        for(int i = 1; i< m_Citys.size(); i++ ){
            costMiddleStep +=m_DistanceMatriz.getDistance(m_Citys.get(i), m_Citys.get(i-1));
        }

        int sol = costFirstStep + costLastStep + costMiddleStep;
        return sol;
    }


    public Citys clone(){
        Citys citys = new Citys();
        citys.m_Citys = (ArrayList<Integer>)this.m_Citys.clone();
        citys.m_DistanceMatriz = m_DistanceMatriz;
        return citys;
    }



    @Override
    public int compareTo(Citys o) {
        return  this.getCost() - o.getCost() ;
    }
}
