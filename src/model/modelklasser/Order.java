package model.modelklasser;

import java.util.ArrayList;

public class Order {
    private double sumPrice;
    private double percentDiscount;
    private Situation situation;
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

    public double getSumPrice() {
        return sumPrice;
    }

    public double getPercentDiscount() {
        return percentDiscount;
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

    public void createOrderLine(Product product, int amount){
        //TODO
    }

    public void calculateSumPrice(){
        for (OrderLine orderLine : orderLines){
            sumPrice += orderLine.calculateOrderLinePrice();
        }
    }
}
