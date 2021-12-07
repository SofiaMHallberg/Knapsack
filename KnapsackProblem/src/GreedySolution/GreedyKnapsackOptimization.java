package GreedySolution;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;

public class GreedyKnapsackOptimization {

    private LinkedList<Item> itemList;
    private LinkedList<Knapsack> knapsackList;
    private int totalValue = 0;
    private int nbrOfKnapsacks;

    public void readInput() throws IOException {
        BufferedReader inputData = new BufferedReader(new InputStreamReader(new FileInputStream("files\\knapsackInput")));
        nbrOfKnapsacks = Integer.parseInt(inputData.readLine());
        knapsackList = new LinkedList<>();
        for (int i = 0; i < nbrOfKnapsacks; i++) {
            Knapsack knapsack = new Knapsack(Integer.parseInt(inputData.readLine()));
            knapsackList.add(knapsack);
        }

        int nbrOfItems = Integer.parseInt(inputData.readLine());
        itemList = new LinkedList();
        for (int i = 0; i < nbrOfItems; i++) {
            String[] itemValues = inputData.readLine().split(" ");
            Item newItem = new Item(Integer.parseInt(itemValues[0]), Integer.parseInt(itemValues[1]));
            itemList.add(newItem);
        }
        itemList.sort(Collections.reverseOrder());
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println("itemNbr " + i + ", value = " + itemList.get(i).getValue() + ", weight = " + itemList.get(i).getWeight());
        }
        for (int i = 0; i < knapsackList.size(); i++) {
            System.out.println("knapsack " + i + ", capacity = " + knapsackList.get(i).getCapacity());
        }
    }

    public LinkedList<Knapsack> fillOneKnapsackAtTheTime() {
        reset();
        for (int knapsackNbr = 0; knapsackNbr < knapsackList.size(); knapsackNbr++) {
            for (int itemNbr = 0; itemNbr < itemList.size(); itemNbr++) {
                Item item = itemList.get(itemNbr);
                if (item.isAvailable()) {
                    if (knapsackList.get(knapsackNbr).addItem(item)) {
                        itemList.get(itemNbr).setAvailability(false);
                    }
                }
            }
        }
        System.out.println("fillOneKnapsackAtTheTime: " + calculateTotalValue(knapsackList));
        return knapsackList;
    }

    public void fillAllKnapsacksAtOnce() {
        reset();
    }

    public static int calculateTotalValue(LinkedList<Knapsack> list) {
        int totalValue = 0;
        for (Knapsack knapsack : list) {
            double value = knapsack.getCurrentValue();
            int weight = knapsack.getCurrentWeight();
            System.out.println("value: " + value + ", weight: " + weight);
            totalValue += value;
        }
        return totalValue;
    }

    public void reset() {
        for (Item item : itemList) {
            item.setAvailability(true);
        }
        for (Knapsack knapsack : knapsackList) {
            knapsack.resetKnapsack();
        }
    }

    public LinkedList<Item> getItemList() {
        return itemList;
    }

    public void neighborhoodSearch() {
        LinkedList<Knapsack> bestSoFar;
        int bestValue;
        Item nextAvailableItem = null;
        for (Item item : itemList) {
            if (item.isAvailable()) {
                nextAvailableItem = item;
                break;
            }
        }
        if (nextAvailableItem != null) {
            for (Knapsack knapsack : knapsackList) {
                for (Item item : knapsack.getIncludedItems()) {
                    if (item.getBenefit() < nextAvailableItem.getBenefit()) {
                        if (knapsack.itemExchange(item, nextAvailableItem)) {
                            nextAvailableItem = item;
                        }
                    }
                }
            }
        }
    }


    public static void main(String[] args) throws IOException {
        GreedyKnapsackOptimization optimizeKnapsack = new GreedyKnapsackOptimization();
        optimizeKnapsack.readInput();
        LinkedList<Knapsack> knapsackList = optimizeKnapsack.fillOneKnapsackAtTheTime();
        LinkedList<Item> itemList = optimizeKnapsack.getItemList();
    }
}
