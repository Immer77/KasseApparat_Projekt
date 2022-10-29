package gui;

import model.modelklasser.*;

import java.util.List;

public interface OrderControllerInterface {
    // Creates and gets alle the situations
    Situation createSituation(String name);
    List<Situation> getSituations();

    //Create
    Order createOrder(Situation situation);
    List<Order> getOrders();

    void removeOrder(Order order);

    void initContent();

    public OrderLine createOrderLineForOrder (Order order, int amount, Price price);
}
