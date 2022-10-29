package gui;

import model.modelklasser.*;

import java.util.List;

public interface OrderControllerInterface {
    /**
     * Creates a situation
     * @param name name of the situation
     * @return object of situation
     */
    Situation createSituation(String name);

    /**
     * Gets all the situations
     * @return list of situations
     */
    List<Situation> getSituations();

    /**
     * Creates an order
     * @param situation takes in a specific situation
     * @return
     */
    Order createOrder(Situation situation);

    /**
     * gets a list of all the orders
     * @return list of orders
     */
    List<Order> getOrders();

    /**
     * Removes the order from the list
     * @param order
     */
    void removeOrder(Order order);

    /**
     * Initializes content for the Graphical user interface
     */
    void initContent();

    public OrderLine createOrderLineForOrder (Order order, int amount, Price price);
}
