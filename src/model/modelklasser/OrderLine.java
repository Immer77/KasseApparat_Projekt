package model.modelklasser;

import java.util.ArrayList;

public class OrderLine {
    private int amount;
    // forced association --> 1 Product
    private Price price;

    public OrderLine(int amount, Price price) {
        this.amount = amount;
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public Price getPrice() {
        return price;
    }

    public double calculateTotalPrice(Unit unit, Situation situation){
        //TODO
        return 0.0;
    }
}
