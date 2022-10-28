package model.modelklasser;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private String name;
    private String description;
    // composition --> 0..* Price
    private final ArrayList<Price> prices = new ArrayList<>();


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

    public ArrayList<Price> getPrices() {
        return new ArrayList<>(prices);
    }

    public Price createPrice(double value, Unit unit, Situation situation){
        Price price = new Price(value, unit, situation, this);
        prices.add(price);
        return price;
    }

    public void removePrice(Price price){
        if (prices.contains(price)){
            prices.remove(price);
        }
    }
}
