package GreedySolution;

public class Item {
    private int weight;
    private int value;
    private boolean available;

    public Item(int value, int weight) {
        this.weight=weight;
        this.value=value;
        available=true;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public boolean isAvailable() {
        return available;
    }
}
