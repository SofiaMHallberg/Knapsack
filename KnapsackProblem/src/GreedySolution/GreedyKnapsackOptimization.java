package GreedySolution;

import java.io.*;
import java.util.LinkedList;

public class GreedyKnapsackOptimization {
    private int[] weightConstraints;
    private LinkedList<Item> itemList;
    public void readInput() throws IOException {
        BufferedReader inputData = new BufferedReader(new InputStreamReader(new FileInputStream("files\\knapsackInput")));
        int nbrOfKnapsacks = Integer.parseInt(inputData.readLine());
        weightConstraints = new int[nbrOfKnapsacks];
        for (int i = 0; i < nbrOfKnapsacks; i++)
            weightConstraints[i] = Integer.parseInt(inputData.readLine());

        int nbrOfItems=Integer.parseInt(inputData.readLine());
        itemList=new LinkedList();
        for(int i=0; i<nbrOfItems; i++) {
            String[] itemValues=inputData.readLine().split(" ");
            Item newItem=new Item(Integer.parseInt(itemValues[0]), Integer.parseInt(itemValues[1]));
            itemList.add(newItem);
        }
        System.out.println("Done");
    }

    public static void main(String[] args) throws IOException {
        GreedyKnapsackOptimization optimizeKnapsack=new GreedyKnapsackOptimization();
        optimizeKnapsack.readInput();
    }
}
