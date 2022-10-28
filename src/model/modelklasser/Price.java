package model.modelklasser;

public class Price {
    private double value;
    private Unit unit;
    private Situation situation;

    Price(double value, Unit unit, Situation situation) {
        this.value = value;
        this.unit = unit;
        this.situation = situation;
    }

    public double getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
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
