package General;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Created by denis on 11/11/16.
 */


public  class Distances {

    public Distances(String path){
        this.LoadDistances(path);
    }

    public ArrayList<ArrayList<Integer>> m_DistanceMatriz = new ArrayList();

    public int getDistance(int i, int j){
        try{
            if(j>i){
                return m_DistanceMatriz.get(j-1).get(i);
            }
            return m_DistanceMatriz.get(i-1).get(j);
        }
        catch (Exception e){
            System.out.println("El cambio era "+ i + " " +j);
            throw e;
        }
    }


    public boolean LoadDistances(String path){
        m_DistanceMatriz.clear();
        Scanner input = null;
        try {
            input = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return  false;
        }

        while(input.hasNextLine())
        {
            Scanner colReader = new Scanner(input.nextLine());
            ArrayList col = new ArrayList();
            while(colReader.hasNextInt())
            {
                col.add(colReader.nextInt());
            }
            m_DistanceMatriz.add(col);
        }
        // After read distances we know the size of the citys
        Citys.SIZE = m_DistanceMatriz.size();
        return true;
    }


}
