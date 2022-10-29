package model.modelklasser;

import java.util.ArrayList;

public class Order {
    private double percentDiscount;
    private Situation situation;
    private double fixedPrice;
    // composition --> 0..* OrderLine
    private final ArrayList<OrderLine> orderLines = new ArrayList<>();


    public Order(Situation situation) {
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

    public void removeOrderLine(OrderLine orderLine) {
        if (orderLines.contains(orderLine)) {
            orderLines.remove(orderLine);
        }
    }

    public double getPercentDiscount() {
        return percentDiscount;
    }


    public Situation getSituation() {
        return situation;
    }

    public void setPercentDiscount(double percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public double getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(double fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public double calculateSumPriceForUnit(Unit unit){
        double sumPrice = 0.0;
        for (OrderLine orderLine : orderLines){
            if (orderLine.getPrice().getUnit().equals(unit)) {
                sumPrice += orderLine.calculateOrderLinePrice();
            }
        }
        return sumPrice;
    }
}
