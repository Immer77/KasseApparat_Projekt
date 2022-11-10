package gui;

import model.modelklasser.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface OrderControllerInterface {

    /**
     * Gets all the situations
     *
     * @return list of situations
     */
    List<Situation> getSituations();

    /**
     * Creates an order
     *
     * @return
     */
    Order createOrder();

    /**
     * gets a list of all the orders
     *
     * @return list of orders
     */
    List<Order> getOrders();

    /**
     * Saves an order to the storage
     *
     * @param order The order to be saved
     */
    void saveOrder(Order order);

    /**
     * Sets a percentage discount for the order
     *
     * @param order              the order to set the discount for
     * @param percentageDiscount a percentage representing a discount
     */
    void setDiscountForOrder(Order order, double percentageDiscount);

    /**
     * Sets a fixed total price for the entire order
     *
     * @param order           the order to set the discount for
     * @param fixedTotalPrice the total price for the entire order, overwriting any calculated sum
     */
    void setFixedPriceForOrder(Order order, double fixedTotalPrice, Unit fixedTotalPriceUnit);

    PaymentMethod getPaymentMethodForOrder(Order order);

    /**
     * Set the paymentmethod for the given order
     *
     * @param order         an Order
     * @param paymentMethod the method of payment for the order, given as a String
     */
    void setPaymentMethodForOrder(Order order, PaymentMethod paymentMethod);

    /**
     * Initializes content for the Graphical user interface
     */
    void initContent();

    OrderLine createOrderLineForOrder(Order order, int amount, Price price);

    Rental createRental(String name, String description, LocalDate startDate);

    List<Rental> getRentals();

    List<Rental> getDoneRentals();

    List<Rental> getActiveRentals();

    void setEndDateForOrder(Order order, LocalDate date);

    /**
     * Creates a new Tour object, with a planned date and time
     *
     * @param endDate the date this tour is planned for
     * @param time    the time this tour is planned for
     * @return the new Tour object
     */
    Tour createTour(LocalDate endDate, LocalTime time);

    /**
     * Returns a list of all Tours in the system.
     *
     * @return A list of Tours
     */
    List<Tour> getTours();

    /**
     * Get all productcategoires
     *
     * @return
     */
    List<ProductCategory> getProductCategories();
}
