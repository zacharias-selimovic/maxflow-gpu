import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Preflow{
    public static void main(String[] args) throws FileNotFoundException {
        
        File graphFile = new File("/home/magizache/preflow/CPU/large_graph.edgelist");
        Scanner scanner = new Scanner(graphFile);
        ArrayList<int[]> edgeList = new ArrayList<>();

    
        //Each edge is in format, (from, to, capacity)
        int i = 0;
        while (scanner.hasNextLine()) {
            String line =  scanner.nextLine();
            String[] lineContent = line.split(" ");
            //From
            int from = Integer.parseInt(lineContent[0]);
            //To
            int to = Integer.parseInt(lineContent[1]);
            //Capacity
            int cap = Integer.parseInt(lineContent[2]);
            int[] edge = new int[]{from, to, cap};
            edgeList.add(edge);
            i++;
        }
        scanner.close();

        final int NUM_NODES = 1000;
        final int SOURCE_NODE_ID = 0;
        final int SINK_NODE_ID = 999;
        
        long curr = System.currentTimeMillis();
        int maximumFlow = preflowPush(edgeList, NUM_NODES, SOURCE_NODE_ID, SINK_NODE_ID);
        long after = System.currentTimeMillis();

        System.out.println("Maximum flow is equal to: " + maximumFlow + " Algorithm took " + (after-curr) + " ms");

    }   

    private static int preflowPush(ArrayList<int[]> edges, int numNodes, int source, int sink) {
        // Create adjacency matrix for capacities
        int[][] capacity = new int[numNodes][numNodes];
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int cap = edge[2];
            capacity[from][to] += cap; // Sum capacities if multiple edges exist
        }

        // Initialize flow, height, and excess flow arrays
        int[][] flow = new int[numNodes][numNodes];
        int[] height = new int[numNodes];
        int[] excess = new int[numNodes];

        height[source] = numNodes;

        // Initialize preflow
        for (int i = 0; i < numNodes; i++) {
            if (capacity[source][i] > 0) {
                flow[source][i] = capacity[source][i];
                flow[i][source] = -capacity[source][i];
                excess[i] = capacity[source][i];
            }
        }

        // List of active nodes
        Queue<Integer> activeNodes = new LinkedList<>();
        HashSet<Integer> activeSet = new HashSet<>();
        for (int i = 0; i < numNodes; i++) {
            if (i != source && i != sink && excess[i] > 0) {
                activeNodes.add(i);
                activeSet.add(i);
            }
        }

        // Discharge process
        while (!activeNodes.isEmpty()) {
            int u = activeNodes.poll();
            activeSet.remove(u);
            discharge(u, capacity, flow, height, excess, activeNodes, activeSet, numNodes);
        }

        int maxFlow = 0;
        for (int i = 0; i < numNodes; i++) {
            maxFlow += flow[source][i];
        }

        return maxFlow;
    }

    private static void discharge(int u, int[][] capacity, int[][] flow, int[] height, int[] excess, Queue<Integer> activeNodes, HashSet<Integer> activeSet, int numNodes) {
        while (excess[u] > 0) {
            boolean pushed = false;
            for (int v = 0; v < numNodes; v++) {
                if (capacity[u][v] - flow[u][v] > 0 && height[u] == height[v] + 1) {
                    push(u, v, capacity, flow, excess);
                    if (v != 0 && v != 999 && excess[v] > 0 && !activeSet.contains(v)) {
                        activeNodes.add(v);
                        activeSet.add(v);
                    }
                    pushed = true;
                    break;
                }
            }
            if (!pushed) {
                relabel(u, capacity, flow, height, numNodes);
            }
        }
    }

    private static void push(int u, int v, int[][] capacity, int[][] flow, int[] excess) {
        int delta = Math.min(excess[u], capacity[u][v] - flow[u][v]);
        flow[u][v] += delta;
        flow[v][u] -= delta;
        excess[u] -= delta;
        excess[v] += delta;
    }

    private static void relabel(int u, int[][] capacity, int[][] flow, int[] height, int numNodes) {
        int minHeight = Integer.MAX_VALUE;
        for (int v = 0; v < numNodes; v++) {
            if (capacity[u][v] - flow[u][v] > 0) {
                minHeight = Math.min(minHeight, height[v]);
            }
        }
        if (minHeight < Integer.MAX_VALUE) {
            height[u] = minHeight + 1;
        }
    }

}