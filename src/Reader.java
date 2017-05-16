import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by denis on 5/01/17.
 */
public class Reader {



    public static ArrayList<Double> randomNumbers = new ArrayList<>();

    private static int count = -1;

    public static boolean readNumbersFromFile(String path){
        count = -1;
        Scanner input = null;
        try {
            input = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("No se encuentra el archivo " + path);
            e.printStackTrace();
            return false;
        }
        while (input.hasNextLine()) {
            double valueRead = Double.parseDouble(input.nextLine());
            randomNumbers.add(valueRead);
        }
        return true;
    }

    public static double getNextCity(){
        count++;
        return randomNumbers.get(count);
    }



}
