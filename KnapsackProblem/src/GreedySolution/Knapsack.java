package GreedySolution;

import java.util.LinkedList;

public class Knapsack {
    private int capacity;
    private LinkedList<Item> includedItems;

    public Knapsack(int capacity) {
        this.capacity = capacity;
        includedItems = new LinkedList<>();
    }

    public boolean addItem(Item item) {
        if ((getCurrentWeight() + item.getWeight()) > capacity) {
            return false;
        }
        else {
            includedItems.add(item);
            System.out.println("item with weight " + item.getWeight() + " added to knapsack with capacity " + capacity);
            return true;
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentWeight() {
        int totalWeight = 0;
        for (Item item: includedItems) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }

    public double getCurrentValue() {
        int totalValue = 0;
        for (Item item: includedItems) {
            totalValue += item.getValue();
        }
        return totalValue;
    }

    public void resetKnapsack() {
        includedItems.clear();
    }
}
