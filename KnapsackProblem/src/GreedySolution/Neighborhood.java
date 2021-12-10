package GreedySolution;

import java.util.Arrays;
import java.util.LinkedList;

public class Neighborhood {
    int[] neighborhood;
    GreedyKnapsackOptimization gno;

    public Neighborhood(int nbrOfItems, GreedyKnapsackOptimization gno) {
        neighborhood=new int[nbrOfItems];
        Arrays.fill(neighborhood, -1);
        this.gno =gno;
    }

    public void setNeighborhood(LinkedList<Knapsack> knapsackList) {
        for (Knapsack knapsack:knapsackList) {
            LinkedList<Item> itemsInKnapsack=knapsack.getIncludedItems();
            for (Item item:itemsInKnapsack) {
                neighborhood[item.getItemNbr()]=knapsack.getKnapsackNbr();
            }
        }
    }

    public void rotate3items(Item newItem) {
        Item item1, item2, item3;
        Knapsack knapsack1, knapsack2, knapsack3;
        for (int itemNbr:neighborhood) {
            Knapsack knapsack=gno.getKnapsackList().get(neighborhood[itemNbr]);
            LinkedList<Item> itemsInKnapsack=knapsack.getIncludedItems();
            for (int i=0; i<itemsInKnapsack.size(); i++) {
                if(itemsInKnapsack.get(i).getItemNbr()==itemNbr) {
                    if(knapsack.itemExchange(itemsInKnapsack.get(i), newItem)) {
                        item1=itemsInKnapsack.get(i);
                        knapsack1=knapsack;
                        rotate2ndItem(item1, knapsack1);
                        // byt ut mot item i annan väska
                    }
                }
            }
        }
    }

    private void rotate2ndItem(Item item1, Knapsack knapsack1) {
        Item item2, item3;
        Knapsack knapsack2, knapsack3;
        for (int itemNbr:neighborhood) {
            Knapsack knapsack=gno.getKnapsackList().get(neighborhood[itemNbr]);
            LinkedList<Item> itemsInKnapsack=knapsack.getIncludedItems();
            if(knapsack.getKnapsackNbr()!=knapsack1.getKnapsackNbr()) {
                for (int i=0; i<itemsInKnapsack.size(); i++) {
                    if(itemsInKnapsack.get(i).getItemNbr()==itemNbr) {
                        if(knapsack.itemExchange(itemsInKnapsack.get(i), item1)) {
                            item2=itemsInKnapsack.get(i);
                            knapsack2=knapsack;
                            rotate3rdItem(item2, knapsack2);
                            // byt ut mot item i annan väska
                        }
                    }
                }
            }
        }
    }

    private void rotate3rdItem(Item item2, Knapsack knapsack2) {
        Item item3;
        Knapsack knapsack3;
        for (int itemNbr:neighborhood) {
            Knapsack knapsack=gno.getKnapsackList().get(neighborhood[itemNbr]);
            LinkedList<Item> itemsInKnapsack=knapsack.getIncludedItems();
            if(knapsack.getKnapsackNbr()!=knapsack2.getKnapsackNbr()) {
                for (int i=0; i<itemsInKnapsack.size(); i++) {
                    if(itemsInKnapsack.get(i).getItemNbr()==itemNbr) {
                        if(knapsack.itemExchange(itemsInKnapsack.get(i), item2)) {
                            item2=itemsInKnapsack.get(i);
                            knapsack2=knapsack;
                            rotate3rdItem(item2, knapsack2);
                            // byt ut mot item i annan väska
                        }
                    }
                }
            }
        }
    }
}
