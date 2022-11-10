package model.controller;

import gui.OrderControllerInterface;
import model.modelklasser.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OrderController implements OrderControllerInterface {

    //Field Variables
    private StorageInterface storage;
    private static int orderNumber = 1000;

    /**
     * Private ordercontroller supporting singleton pattern
     *
     * @param storage
     */
    public OrderController(StorageInterface storage) {
        this.storage = storage;
    }


    /**
     * Gets a list of all the situation there is from storage
     *
     * @return
     */
    public List<Situation> getSituations() {
        return storage.getSituations();
    }

    /**
     * Creates an order. Does NOT add the order to storage
     *
     * @return
     */
    public Order createOrder() {
        orderNumber++;
        Order order = new Order(orderNumber);
        return order;
    }

    /**
     * Saves an order to the storage
     *
     * @param order The order to be saved
     */
    public void saveOrder(Order order) {
        storage.addOrder(order);
    }

    /**
     * Sets an enddate for the order
     * @param order
     * @param date
     */

    public void setEndDateForOrder(Order order, LocalDate date) {
        order.setEndDate(date);
    }

    /**
     * Sets a percentage discount for the order
     *
     * @param order              the order to set the discount for
     * @param percentageDiscount a percentage representing a discount
     */
    public void setDiscountForOrder(Order order, double percentageDiscount) {
        order.setPercentDiscount(percentageDiscount);
    }

    /**
     * Sets a fixed total price for the entire order
     *
     * @param order           the order to set the discount for
     * @param fixedTotalPrice the total price for the entire order, overwriting any calculated sum
     */
    public void setFixedPriceForOrder(Order order, double fixedTotalPrice, Unit fixedTotalPriceUnit) {
        order.setFixedPrice(fixedTotalPrice);
        order.setFixedPriceUnit(fixedTotalPriceUnit);
    }

    public List<Order> getOrders() {
        return storage.getOrders();
    }

    public OrderLine createOrderLineForOrder(Order order, int amount, Price price) {
        OrderLine currentOrderLine = order.createOrderLine(amount, price);
        return currentOrderLine;
    }

    public PaymentMethod getPaymentMethodForOrder(Order order) {
        return order.getPaymentMethod();
    }

    /**
     * Set the paymentmethod for the given order
     *
     * @param order         an Order
     * @param paymentMethod the method of payment for the order
     */
    public void setPaymentMethodForOrder(Order order, PaymentMethod paymentMethod) {
        order.setPaymentMethod(paymentMethod);
    }

    /**
     * Method to create a new rental and adding it to storage
     *
     * @param name        of the person who is making the rental
     * @param description of what he rents
     * @param endDate     of the time he is finished with the rental
     * @return
     */
    public Rental createRental(String name, String description, LocalDate endDate) {
        orderNumber++;
        Rental rental = new Rental(name, description, endDate, orderNumber);
        storage.addOrder(rental);
        return rental;
    }

    /**
     * Takes the storage list of all orders and returns all the instances where order is rental due to the polymorphic effect.
     *
     * @return
     */
    public List<Rental> getRentals() {
        ArrayList<Rental> rentals = new ArrayList<>();
        for (Order order : storage.getOrders()) {
            if (order instanceof Rental) {
                rentals.add((Rental) order);
            }
        }
        return rentals;
    }

    /**
     * Loops through all orders and checks if its an instance of rental and adds it to the list of rentals
     * @return
     */
    public List<Rental> getActiveRentals() {
        ArrayList<Rental> rentals = new ArrayList<>();
        for (Order order : storage.getOrders()) {
            if (order instanceof Rental) {
                if (order.getPaymentMethod() == null) {
                    rentals.add((Rental) order);
                }
            }
        }
        return rentals;
    }

    /**
     * Gets all done rentals by seeing if there has been added a paymentmethod, meaning it has been paid for
     * @return
     */
    public List<Rental> getDoneRentals() {
        ArrayList<Rental> rentals = new ArrayList<>();
        for (Order order : storage.getOrders()) {
            if (order instanceof Rental) {
                if (order.getPaymentMethod() != null) {
                    rentals.add((Rental) order);
                }
            }
        }
        return rentals;
    }

    /**
     * Creates a new Tour object, with a planned date and time
     *
     * @param endDate the date this tour is planned for
     * @param time    the time this tour is planned for
     * @return the new Tour object
     */
    public Tour createTour(LocalDate endDate, LocalTime time) {
        orderNumber++;
        Tour tour = new Tour(endDate, time,orderNumber);
        return tour;
    }

    /**
     * Returns a list of all Tours in the system.
     *
     * @return A list of Tours
     */
    public List<Tour> getTours() {
        List<Tour> list = new ArrayList<>();
        for (Order order : storage.getOrders()) {
            if (order instanceof Tour) {
                list.add((Tour) order);
            }
        }
        return list;
    }

    /**
     * Returns all current ProductCategories
     *
     * @return a list of all ProductCategories.
     */
    public List<ProductCategory> getProductCategories() {
        return storage.getProductCategories();
    }



    public void initContent() {
        Rental rental1 = createRental("Jens Hansen", "Lejer 1 hane, 1 fustage Klosterbryg og 2 krus", LocalDate.of(2022, 12, 31));
        Rental rental2 = createRental("Peter Pedal", "Lejer 1 bar med flere haner og 2 fustager Blondie", LocalDate.of(2022, 11, 15));
        Rental rental3 = createRental("Klods Hans", "Lejer 2 haner og 1 fustage med India Pale Ale", LocalDate.of(2022, 11, 29));
        Rental rental4 = createRental("Frank Stein", "Lejer 2 Trækasser med 12 øl og 24 glas", LocalDate.now());

        rental1.createOrderLine(1, getProductCategories().get(9).getProducts().get(0).getPrices().get(0));
        rental1.createOrderLine(1, getProductCategories().get(5).getProducts().get(0).getPrices().get(0));
        rental1.createOrderLine(2, getProductCategories().get(10).getProducts().get(0).getPrices().get(0));
        rental2.createOrderLine(1, getProductCategories().get(9).getProducts().get(2).getPrices().get(0));
        rental2.createOrderLine(2, getProductCategories().get(5).getProducts().get(4).getPrices().get(0));
        rental3.createOrderLine(1, getProductCategories().get(9).getProducts().get(1).getPrices().get(0));
        rental3.createOrderLine(1, getProductCategories().get(5).getProducts().get(6).getPrices().get(0));
        rental4.createOrderLine(2, getProductCategories().get(11).getProducts().get(5).getPrices().get(0));
        rental4.createOrderLine(24, getProductCategories().get(10).getProducts().get(0).getPrices().get(0));

        Tour tour1 = createTour(LocalDate.of(2022, 12, 31), LocalTime.of(14, 30));
        Tour tour2 = createTour(LocalDate.of(2022, 11, 16), LocalTime.of(15, 00));
        Tour tour3 = createTour(LocalDate.of(2022, 12, 10), LocalTime.of(10, 00));

        tour1.setName("John");
        tour1.setDescription("Potentiel lærling");
        tour2.setName("Hans");
        tour2.setDescription("Studerende fra EAAA");
        tour3.setName("BoMedVenner");
        tour3.setDescription("bajerTur");

        tour1.createOrderLine(1, getProductCategories().get(12).getProducts().get(0).getPrices().get(0));
        tour2.createOrderLine(1, getProductCategories().get(12).getProducts().get(1).getPrices().get(0));
        tour3.createOrderLine(1, getProductCategories().get(12).getProducts().get(2).getPrices().get(0));

        saveOrder(tour1);
        saveOrder(tour2);
        saveOrder(tour3);
    }
}
