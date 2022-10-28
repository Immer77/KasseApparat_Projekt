package model.modelklasser;

public class OrderLine {
    private int amount;
    // forced association --> 1 Product
    private Product product;

    OrderLine(int amount, Product product) {
        this.amount = amount;
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public Product getProduct() {
        return product;
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
