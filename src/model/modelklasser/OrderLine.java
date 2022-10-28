package model.modelklasser;

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

    public double calculateOrderLinePrice(Unit unit, Situation situation){
        double result = 0.0;
        for (Price price : product.getPrices()){
            if (price.getSituation() == situation){
                if (price.getUnit() == unit){
                    result = price.getValue() * amount;
                }
            }
        }
        return result;
    }
}
