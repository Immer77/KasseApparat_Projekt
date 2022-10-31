package model.modelklasser;

import java.util.ArrayList;

public class Order {
    private double percentDiscount;
    private Situation situation;
    /**
     * Saves a fixed price for the whole order. Set to -1.0 if it is not in use
     */
    private double fixedPrice;
    private Unit fixedPriceUnit;

    private PaymentMethod paymentMethod;
    // composition --> 0..* OrderLine
    private final ArrayList<OrderLine> orderLines = new ArrayList<>();

    // Constructor

    public Order(Situation situation) {
        this.situation = situation;
    }

    /**
     * Gets the list of orderlines on the order
     * @return
     */
    public ArrayList<OrderLine> getOrderLines() {
        return new ArrayList<>(orderLines);
    }

    /**
     * Creates an orderline
     * @param amount amount that goes on the orderline
     * @param price the price of the orderline
     * @return orderline
     */
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

    // GETTERS AND SETTERS --------------------------------------------------------------------------------------
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

    public Unit getFixedPriceUnit() {
        return fixedPriceUnit;
    }

    public void setFixedPriceUnit(Unit fixedPriceUnit) {
        this.fixedPriceUnit = fixedPriceUnit;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }





    /**
     * Calculates the price based on the unit whether its Dkk/punches
     * @param unit Dkk/Punches
     * @return
     */
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
