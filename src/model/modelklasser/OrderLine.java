package model.modelklasser;

import java.util.ArrayList;

public class OrderLine {
    private int amount;
    // forced association --> 1 Product
    private Product product;

    protected OrderLine(int amount, Product product) {
        this.amount = amount;
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public Product getProduct() {
        return product;
    }

    public double calculateTotalPrice(Unit unit, Situation situation){
        //TODO
    }
}
