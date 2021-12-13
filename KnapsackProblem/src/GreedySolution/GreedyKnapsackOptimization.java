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

    private final int ITEM_RANGE = 100;
    private final int ITEM_MINIMUM = 40;

    private final int ITEM_LOWEST_WEIGHT = 5;
    private final int ITEM_WEIGHT_RANGE = 50;
    private final int ITEM_LOWEST_VALUE = 5;
    private final int ITEM_VALUE_RANGE = 25;

    private final int KNAPSACK_RANGE = 5;
    private final int KNAPSACK_MINIMUM = 3;

    private final int KNAPSACK_CAPACITY_RANGE = 50;
    private final int KNAPSACK_CAPACITY_MINIMUM = 50;

    private int improvedSearches = 0;


    /**
     * This method generates a random number of knapsacks and items (within a given range). The knapsacks are added
     * to the global variable knapsackList, and the items are added to itemList. Finally, it sorts the items from
     * highest benefit to lowest.
     */
    private void generateRandomData() {
        itemList = new LinkedList<>();
        knapsackList = new LinkedList<>();
        Random random = new Random();
        nbrOfKnapsacks = random.nextInt(KNAPSACK_RANGE) + KNAPSACK_MINIMUM;
        nbrOfItems = random.nextInt(ITEM_RANGE) + ITEM_MINIMUM;
        for (int i = 0; i < nbrOfItems; i++) {
            Item item = new Item(random.nextInt(ITEM_VALUE_RANGE) + ITEM_LOWEST_VALUE, random.nextInt(ITEM_WEIGHT_RANGE) + ITEM_LOWEST_WEIGHT, i);
            itemList.add(item);
        }
        for (int i = 0; i < nbrOfKnapsacks; i++) {
            Knapsack knapsack = new Knapsack(random.nextInt(KNAPSACK_CAPACITY_RANGE) + KNAPSACK_CAPACITY_MINIMUM, i);
            knapsackList.add(knapsack);
            System.out.println("Knapsack " + knapsack.getKnapsackNbr() + " created. capacity: " + knapsack.getCapacity() + ".");
        }
        itemList.sort(Collections.reverseOrder());

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

    /**
     * Our greedy algorithm iterates through all knapsacks and tries to add the items in order of its benefit value
     */
    public void greedyAlgorithm() {
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

    /**
     * Calculates the total value of all knapsacks in the provided list
     *
     * @param list all knapsacks
     * @return the total value
     */
    public static int calculateTotalValue(LinkedList<Knapsack> list) {
        int totalValue = 0;
        for (Knapsack knapsack : list) {
            double value = knapsack.getCurrentValue();
            totalValue += value;
        }
        return totalValue;
    }

    /**
     * Resets all items and all knapsacks
     */
    public void reset() {
        for (Item item : itemList) {
            item.setAvailability(true);
        }
        for (Knapsack knapsack : knapsackList) {
            knapsack.resetKnapsack();
        }
    }

    /**
     * Our solution to the neighborhood search. The algorithm iterates through one knapsack at a time,
     * trying to move its items to all the other knapsacks. After this, it once again tries to fill all knapsacks
     * with the available items. Finally, it checks if the current total value is an improvement on the initial
     * total value.
     */
    public void neighborhoodSearch() {
        int initialTotalValue = calculateTotalValue(knapsackList);
        boolean exchangeIsMade = false;

        for (int knapsackNbr = 0; knapsackNbr < nbrOfKnapsacks; knapsackNbr++) {
            Knapsack knapsack = knapsackList.get(knapsackNbr);
            LinkedList<Item> itemsInKnapsack = knapsack.getIncludedItems();
            for (int itemNbr = 0; itemNbr < itemsInKnapsack.size(); itemNbr++) {
                Item item = itemsInKnapsack.get(itemNbr);
                for (int nextKnapsack = 0; nextKnapsack < nbrOfKnapsacks; nextKnapsack++) {
                    if (nextKnapsack == knapsackNbr) {
                        continue;
                    }
                    Knapsack otherKnapsack = knapsackList.get(nextKnapsack);
                    if (otherKnapsack.addItem(item)) {
                        if (knapsack.removeItem(item)) {
                            item.setAvailability(false);
                            exchangeIsMade = true;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < nbrOfKnapsacks; i++) {
            LinkedList<Item> availableItems = getAvailableItems();
            for (int j = 0; j < availableItems.size(); j++) {
                if (knapsackList.get(i).addItem(itemList.get(j))) {
                    System.out.println("ADDED A PREVIOUSLY UNUSED ITEM TO A KNAPSACK");
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
            improvedSearches++;
        } else System.out.println("Exchange was made but the total value was not improved.\n\n");
    }


    /**
     * Iterates through all items and check if they're available
     * @return all available items.
     */
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
        int totalSearches = 1000;
        for (int i = 0; i < totalSearches; i++) {
            optimizeKnapsack.generateRandomData();
            optimizeKnapsack.greedyAlgorithm();
            optimizeKnapsack.neighborhoodSearch();
        }
        System.out.println("The neighborhood search improved " + ((double) optimizeKnapsack.getImprovedSearches() / (double) totalSearches * 100) + "% of the total searches.");
    }

    private int getImprovedSearches() {
        return improvedSearches;
    }
}


