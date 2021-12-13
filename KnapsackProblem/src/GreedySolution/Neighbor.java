package GreedySolution;

import java.util.ArrayList;
import java.util.LinkedList;

public class Neighbor {
    private LinkedList<Knapsack> knapsackList;
    private LinkedList<Item> itemList;
    private int totalValue;

    public Neighbor(LinkedList<Knapsack> knapsackList, int totalValue) {
//        this.itemList = itemList;
        this.knapsackList = knapsackList;
        this.totalValue = totalValue;
    }

    public LinkedList<Knapsack> getKnapsackList() {
        return knapsackList;
    }

    public LinkedList<Item> getItemList() {
        return itemList;
    }

    public int getTotalValue() {
        return totalValue;
    }
}
