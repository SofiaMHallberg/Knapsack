package GreedySolution;

/**
 * @author Sofia Hallberg, Oscar Kareld
 */
public class Item implements Comparable<Item> {
    private final int value;
    private final int weight;
    private final int itemNbr;
    private final double benefit;
    private boolean available;

    public Item(int value, int weight, int itemNbr) {
        this.value=value;
        this.weight=weight;
        this.itemNbr=itemNbr;
        benefit = (double) value / weight;
        available=true;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public int getItemNbr() {
        return itemNbr;
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
