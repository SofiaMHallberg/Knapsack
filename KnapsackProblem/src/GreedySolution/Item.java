package GreedySolution;

public class Item implements Comparable<Item> {
    private int weight;
    private int value;
    private double benefit;
    private boolean available;

    public Item(int value, int weight) {
        this.weight=weight;
        this.value=value;
        benefit = (double) value / weight;
        available=true;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public double getBenefit() {
        return benefit;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailability(boolean available) {
        this.available = available;
    }

    @Override
    public int compareTo(Item item) {
        return Double.compare(benefit, item.getBenefit());
    }
}
