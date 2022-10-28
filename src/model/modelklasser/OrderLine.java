package model.modelklasser;

public class OrderLine {
    private int amount;
    // forced association --> 1 Product
    private Price price;

    OrderLine(int amount, Price price) {
        this.amount = amount;
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Price getPrice() {
        return price;
    }

    public double calculateOrderLinePrice(){
        double result = 0.0;
        result = price.getValue() * amount;
        return result;
    }
}
