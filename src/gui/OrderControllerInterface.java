package gui;

import model.modelklasser.Order;
import model.modelklasser.Situation;
import model.modelklasser.Unit;

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
}
