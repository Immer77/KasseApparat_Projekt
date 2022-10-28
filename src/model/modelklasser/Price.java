package model.modelklasser;

public class Price {
    private double value;
    private Unit unit;
    private Situation situation;



    private Product product;

    Price(double value, Unit unit, Situation situation, Product product) {
        this.value = value;
        this.unit = unit;
        this.situation = situation;
        this.product = product;
    }

    public double getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }

    public Product getProduct() {
        return product;
    }



    public Situation getSituation() {
        return situation;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ""+value+" "+unit;
    }
}
