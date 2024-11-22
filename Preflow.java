import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Preflow{
    public static void main(String[] args) throws FileNotFoundException {
        
        File graphFile = new File("/home/magizache/preflow/CPU/large_graph.edgelist");
        Scanner scanner = new Scanner(graphFile);

        
        while (scanner.hasNextLine()) {
            String line =  scanner.nextLine();

        }
    }
}