package model.modelklasser;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private double sumPrice;
    private double percentDiscount;
    private Unit unit;
    private Situation situation;
    // composition --> 0..* OrderLine
    private final ArrayList<OrderLine> orderLines = new ArrayList<>();

    public Order(Unit unit, Situation situation) {
        this.unit = unit;
        this.situation = situation;
    }

    public ArrayList<OrderLine> getOrderLines() {
        return new ArrayList<>(orderLines);
    }
    public OrderLine createOrderLine(int amount, Price price) {
        OrderLine orderLine = new OrderLine(amount, price);
        orderLines.add(orderLine);
        return orderLine;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public double getPercentDiscount() {
        return percentDiscount;
    }

    public Unit getUnit() {
        return unit;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public void setPercentDiscount(double percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public void calculateSumPrice(){
        //TODO
    }
}
