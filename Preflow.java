import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Preflow{
    public static void main(String[] args) throws FileNotFoundException {
        
        File graphFile = new File("/home/magizache/preflow/CPU/large_graph.edgelist");
        Scanner scanner = new Scanner(graphFile);
        int[][] graph = new int[50000][3];

        //Each edge is in format, (from, to, capacity)
        int i = 0;
        while (scanner.hasNextLine()) {
            String line =  scanner.nextLine();
            String[] lineContent = line.split(" ");
            //From
            graph[i][0] = Integer.parseInt(lineContent[0]);
            //To
            graph[i][1] = Integer.parseInt(lineContent[1]);
            //Capacity
            graph[i][2] = Integer.parseInt(lineContent[2]);


        }
        scanner.close();

        final int SOURCE_NODE_ID = 0;
        final int SINK_NODE_ID = 999;

        int maximumFlow = preflowPush(graph);

        System.out.println("Maximum flow is equal to: " + maximumFlow);

    }   

    private static int preflowPush(int[][] graph){
        return 0;
    }
    
}