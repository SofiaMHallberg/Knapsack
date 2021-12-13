package GreedySolution;

import java.util.LinkedList;

public class Knapsack {
    private int capacity;

    private int knapsackNbr;
    private LinkedList<Item> includedItems;

    public Knapsack(int capacity, int knapsackNbr) {
        this.capacity = capacity;
        this.knapsackNbr=knapsackNbr;
        includedItems = new LinkedList<>();
    }

    public int getKnapsackNbr() {
        return knapsackNbr;
    }

    public boolean addItem(Item item) {
        if ((getCurrentWeight() + item.getWeight()) > capacity) {
            return false;
        }
        else {
            includedItems.add(item);
            item.setAvailability(false);
            System.out.println("item " + item.getItemNbr() + " added to knapsack " + knapsackNbr);
            return true;
        }
    }

    public boolean removeItem(Item newItem) {
        for (Item itemInKnapsack : includedItems) {
            if (itemInKnapsack.hashCode() == newItem.hashCode()) {
                itemInKnapsack.setAvailability(true);
                System.out.println("Item " + newItem.getItemNbr() + " removed from knapsack " + knapsackNbr);
                if (includedItems.remove(itemInKnapsack)) {
                    System.out.println("REMOVED");
                }
                return true;
            }
        }
        return false;
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

    public LinkedList<Item> getIncludedItems() {
        return includedItems;
    }

    public void resetKnapsack() {
        includedItems.clear();
    }

    public boolean itemExchange(Item itemInKnapsack, Item newItem) {
        if (capacity <= (getCurrentWeight() - itemInKnapsack.getWeight() + newItem.getWeight())) {
            if (removeItem(itemInKnapsack)) {
                System.out.println("removing " + itemInKnapsack.getWeight() + " and adding " + newItem.getWeight() + " (weight)");
                addItem(newItem);
                return true;
            }
        }
        return false;
    }
}
