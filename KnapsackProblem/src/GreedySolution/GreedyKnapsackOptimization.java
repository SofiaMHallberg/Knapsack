package GreedySolution;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class GreedyKnapsackOptimization {

    private LinkedList<Item> itemList;
    private LinkedList<Knapsack> knapsackList;
    private int nbrOfKnapsacks;
    private int nbrOfItems;


    /**
     * This method generates a random number of knapsacks and items (within a given range). The knapsacks are added
     */
    private void generateRandomData() {
        itemList = new LinkedList<>();
        knapsackList = new LinkedList<>();
        Random random = new Random();
        nbrOfKnapsacks = random.nextInt(3) + 2;
        nbrOfItems = random.nextInt(15) + 10;
        for (int i = 0; i < nbrOfItems; i++) {
            Item item = new Item(random.nextInt(15) + 5, random.nextInt(50) + 5, i);
            itemList.add(item);
            System.out.println(i + ": Item created. value: " + item.getValue() + ", weight: " + item.getWeight() + ".");
        }
        for (int i = 0; i < nbrOfKnapsacks; i++) {
            Knapsack knapsack = new Knapsack(random.nextInt(50) + 50, i);
            knapsackList.add(knapsack);
            System.out.println("Knapsack " + knapsack.getKnapsackNbr() + " created. capacity: " + knapsack.getCapacity() + ".");
        }

    }

    public void readInput() throws IOException {
        BufferedReader inputData = new BufferedReader(new InputStreamReader(new FileInputStream("files\\knapsackInput")));
        nbrOfKnapsacks = Integer.parseInt(inputData.readLine());
        knapsackList = new LinkedList<>();
        for (int i = 0; i < nbrOfKnapsacks; i++) {
            Knapsack knapsack = new Knapsack(Integer.parseInt(inputData.readLine()), i);
            knapsackList.add(knapsack);
        }

        nbrOfItems = Integer.parseInt(inputData.readLine());
        itemList = new LinkedList();
        for (int i = 0; i < nbrOfItems; i++) {
            String[] itemValues = inputData.readLine().split(" ");
            Item newItem = new Item(Integer.parseInt(itemValues[0]), Integer.parseInt(itemValues[1]), i);
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

    public void fillOneKnapsackAtTheTime() {
        reset();
        for (int knapsackNbr = 0; knapsackNbr < nbrOfKnapsacks; knapsackNbr++) {
            for (int itemNbr = 0; itemNbr < nbrOfItems; itemNbr++) {
                Item item = itemList.get(itemNbr);
                if (item.isAvailable()) {
                    if (knapsackList.get(knapsackNbr).addItem(item)) {
                        itemList.get(itemNbr).setAvailability(false);
                    }
                }
            }
        }
        for (int i = 0; i < knapsackList.size(); i++) {
            System.out.println("AFTER GREEDY ALGORITHM: Knapsack " + knapsackList.get(i).getKnapsackNbr() + ". value = "
                    + knapsackList.get(i).getCurrentValue() + ". weight = " + knapsackList.get(i).getCurrentWeight());
        }
        System.out.println("TOTAL VALUE AFTER GREEDY ALGORITHM: " + calculateTotalValue(knapsackList));
    }

    public void printItemList() {
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println("item nbr " + i + "'s availability is " + itemList.get(i).isAvailable());
        }
    }

    public static int calculateTotalValue(LinkedList<Knapsack> list) {
        int totalValue = 0;
        for (Knapsack knapsack : list) {
            double value = knapsack.getCurrentValue();
            int weight = knapsack.getCurrentWeight();
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
        int initialTotalValue = calculateTotalValue(knapsackList);
        boolean exchangeIsMade = false;

        for (int knapsackNbr = 0; knapsackNbr < nbrOfKnapsacks; knapsackNbr++) {
            Knapsack knapsack = knapsackList.get(knapsackNbr);
            LinkedList<Item> itemsInKnapsack = knapsack.getIncludedItems();
            for (int itemNbr = 0; itemNbr < itemsInKnapsack.size(); itemNbr++) {
                Item item = itemsInKnapsack.get(itemNbr);
//                for (int nextKnapsack = knapsackNbr + 1; nextKnapsack < nbrOfKnapsacks; nextKnapsack++) {
                for (int nextKnapsack = 0; nextKnapsack < nbrOfKnapsacks; nextKnapsack++) {
                    if (nextKnapsack == knapsackNbr) {
                        break;
                    }
                    Knapsack otherKnapsack = knapsackList.get(nextKnapsack);
                    if (knapsack.removeItem(item)) {
                        if (otherKnapsack.addItem(item)) {
                            exchangeIsMade = true;
                        }
                    }
                }
            }
            fillKnapsackWithFreedSpace(knapsack);
        }
        for (int i = 0; i < nbrOfKnapsacks; i++) {
            for (int j = 0; j < nbrOfItems; j++) {
                if (itemList.get(j).isAvailable()) {
                    if (knapsackList.get(i).addItem(itemList.get(j)))
                    {
                        System.out.println("SISTA KOLLEN VAR BRA");
                    }
                }
            }
        }
        if (!exchangeIsMade) {
            System.out.println("No better solution found. \n\n");
        } else if (initialTotalValue < calculateTotalValue(knapsackList)) {
            for (int i = 0; i < knapsackList.size(); i++) {
                System.out.println("Knapsack " + knapsackList.get(i).getKnapsackNbr() + ". value = "
                        + knapsackList.get(i).getCurrentValue() + ". weight = " + knapsackList.get(i).getCurrentWeight());
            }
            System.out.println("totalValue improved from " + initialTotalValue + " to " + calculateTotalValue(knapsackList) + "\n\n");
        } else System.out.println("Exchange was made but the total value was not improved.\n\n");
    }

    private void fillKnapsackWithFreedSpace(Knapsack knapsack) {
        LinkedList<Item> availableItems = getAvailableItems();
        for (int availableItemNbr = 0; availableItemNbr < availableItems.size(); availableItemNbr++) {
            Item item = availableItems.get(availableItemNbr);
            knapsack.addItem(item);
        }
    }

    private LinkedList<Item> getAvailableItems() {
        LinkedList<Item> availableItems = new LinkedList<>();
        for (Item item : itemList) {
            if (item.isAvailable()) {
                availableItems.add(item);
            }
        }
        return availableItems;
    }


    public LinkedList<Knapsack> getKnapsackList() {
        return knapsackList;
    }

    public static void main(String[] args) throws IOException {
        GreedyKnapsackOptimization optimizeKnapsack = new GreedyKnapsackOptimization();
        for (int i = 0; i < 10; i++) {
            optimizeKnapsack.generateRandomData();
            optimizeKnapsack.fillOneKnapsackAtTheTime();
            optimizeKnapsack.neighborhoodSearch();
        }
    }
}


