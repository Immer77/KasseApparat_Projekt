package model.modelklasser;

public class OrderLine {
    private int amount;
    // forced association --> 1 Product
    private Price price;

    // package private constructor
    OrderLine(int amount, Price price) {
        this.amount = amount;
        this.price = price;
    }

    // Getters and setters
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Price getPrice() {
        return price;
    }

    /**
     * Calculates the orderlineprice
     * @return the result of the price for the orderline
     */
    public double calculateOrderLinePrice(){
        double result = 0.0;
        result = price.getValue() * amount;
        return result;
    }

    @Override
    public String toString() {
        return "\nAntal: " + amount + ", produkt: " + price;
    }
}
