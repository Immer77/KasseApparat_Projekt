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
     * Saves an order to the storage
     * @param order The order to be saved
     */
    void saveOrder(Order order);

    /**
     * Sets a percentage discount for the order
     * @param order the order to set the discount for
     * @param percentageDiscount a percentage representing a discount
     */
    void setDiscountForOrder(Order order, double percentageDiscount);

    /**
     * Sets a fixed total price for the entire order
     * @param order the order to set the discount for
     * @param fixedTotalPrice the total price for the entire order, overwriting any calculated sum
     */
    void setFixedPriceForOrder (Order order,  double fixedTotalPrice);


    /**
     * Initializes content for the Graphical user interface
     */
    void initContent();

    public OrderLine createOrderLineForOrder (Order order, int amount, Price price);
}
