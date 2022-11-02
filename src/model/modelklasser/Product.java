package model.modelklasser;

import java.util.ArrayList;

public class Product {

    private String name;
    private String description;
    // composition --> 0..* Price
    private final ArrayList<Price> prices = new ArrayList<>();
    private Price depositPrice;


    /**
     * creates object of the Product class
     *
     * @param name name of the product
     * @param description a short description of the product
     */
    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }


    // Getters ------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        String returnValue = name;
        if (!description.isBlank()) {
            returnValue += " ("+description+")";
        }
        return returnValue;
    }

    // gets a copy of the list of prices
    public ArrayList<Price> getPrices() {
        return new ArrayList<>(prices);
    }


    public Price getDepositPrice() {
        return depositPrice;
    }

    /**
     * Removes the price object
     * @param price
     */
    public void removePrice(Price price){
        if (prices.contains(price)){
            prices.remove(price);
        }
    }

    /**
     * Creates a price
     * @param value the price value
     * @param unit whether its DKK or punches
     * @param situation what situation it is
     * @return Returns a price object
     */
    public Price createPrice(double value, Unit unit, Situation situation){
        Price price = new Price(value, unit, situation, this);
        prices.add(price);
        return price;
    }

    public Price createDeposit(double value, Unit unit, Situation situation){
        Price deposit = new Price(value, unit,situation, this);
        this.depositPrice = deposit;
        return deposit;
    }

}
