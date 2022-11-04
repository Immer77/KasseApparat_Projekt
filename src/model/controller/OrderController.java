package model.controller;

import gui.OrderControllerInterface;
import gui.ProductOverviewControllerInterface;
import model.modelklasser.*;
import storage.Storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OrderController implements OrderControllerInterface {

    //Field Variables
    private StorageInterface storage;


    /**
     * Private ordercontroller supporting singleton pattern
     * @param storage
     */
    public OrderController(StorageInterface storage){
        this.storage = storage;
    }


    /**
     * Bruges til test for ikke at have singleton pattern til at melde fejl
     * @param storage
     * @return
     */
    public static OrderControllerInterface getOrderControllerTest(StorageInterface storage){
        OrderControllerInterface unique_OrderController = new OrderController(storage);
        return unique_OrderController;

    }

    /**
     * Creates a situation and adds it to the situations list
     * @param name of the situation
     * @return situation
     */
    public Situation createSituation(String name) {
        Situation situation = new Situation(name);
        storage.addSituation(situation);
        return situation;
    }

    /**
     * Gets a list of all the situation there is from storage
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
        Order order = new Order();
        return order;
    }

    /**
     * Saves an order to the storage
     * @param order The order to be saved
     */
    public void saveOrder(Order order) {
        storage.addOrder(order);
    }

    public void setEndDateForOrder(Order order, LocalDate date){
        order.setEndDate(date);
    }

    /**
     * Sets a percentage discount for the order
     * @param order the order to set the discount for
     * @param percentageDiscount a percentage representing a discount
     */
    public void setDiscountForOrder(Order order, double percentageDiscount){
        order.setPercentDiscount(percentageDiscount);
    }

    /**
     * Sets a fixed total price for the entire order
     * @param order the order to set the discount for
     * @param fixedTotalPrice the total price for the entire order, overwriting any calculated sum
     */
    public void setFixedPriceForOrder (Order order,  double fixedTotalPrice, Unit fixedTotalPriceUnit){
        order.setFixedPrice(fixedTotalPrice);
        order.setFixedPriceUnit(fixedTotalPriceUnit);
    }

    public List<Order> getOrders() {
        return storage.getOrders();
    }

    public OrderLine createOrderLineForOrder (Order order, int amount, Price price) {
        OrderLine currentOrderLine = order.createOrderLine(amount, price);
        return currentOrderLine;
    }

    public PaymentMethod getPaymentMethodForOrder (Order order) {
        return order.getPaymentMethod();
    }

    /**
     * Set the paymentmethod for the given order
     * @param order an Order
     * @param paymentMethod the method of payment for the order
     */
    public void setPaymentMethodForOrder(Order order, PaymentMethod paymentMethod) {
        order.setPaymentMethod(paymentMethod);
    }

    /**
     * Method to create a new rental and adding it to storage
     * @param name of the person who is making the rental
     * @param description of what he rents
     * @param endDate of the time he is finished with the rental
     * @return
     */
    public Rental createRental(String name, String description, LocalDate endDate) {
        Rental rental = new Rental(name,description,endDate);
        storage.addOrder(rental);
        return rental;
    }

    /**
     * Takes the storage list of all orders and returns all the instances where order is rental due to the polymorphic effect.
     * @return
     */
    public List<Rental> getRentals(){
        ArrayList<Rental> rentals = new ArrayList<>();
        for(Order order : storage.getOrders()){
            if(order instanceof Rental){
                rentals.add((Rental) order);
            }
        }
        return rentals;
    }

    /**
     * Creates a new Tour object, with a planned date and time
     * @param endDate the date this tour is planned for
     * @param time the time this tour is planned for
     * @return the new Tour object
     */
    public Tour createTour (LocalDate endDate, LocalTime time) {
        Tour tour = new Tour(endDate, time);
        return tour;
    }

    /**
     * Returns a list of all Tours in the system.
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
     * @return a list of all ProductCategories.
     */
    public List<ProductCategory> getProductCategories () {
        return storage.getProductCategories();
    }


    public void initContent() {
        Situation sit1 = createSituation("Standard");
        Situation sit2 = createSituation("Fredagsbar");

        Price price = storage.getProductCategories().get(0).getProducts().get(0).createPrice(70,Unit.DKK,sit2);
        Price price1 = storage.getProductCategories().get(0).getProducts().get(0).createPrice(35,Unit.DKK,sit1);
        Price price2 = storage.getProductCategories().get(0).getProducts().get(0).createPrice(2,Unit.Klip,sit2);

        Price price3 = storage.getProductCategories().get(0).getProducts().get(1).createPrice(75,Unit.DKK,sit2);
        Price price4 = storage.getProductCategories().get(0).getProducts().get(1).createPrice(35,Unit.DKK,sit1);
        Price price5 = storage.getProductCategories().get(0).getProducts().get(1).createPrice(2,Unit.Klip,sit2);

        Price price6 = storage.getProductCategories().get(1).getProducts().get(0).createPrice(170,Unit.DKK,sit2);
        Price price7 = storage.getProductCategories().get(1).getProducts().get(0).createPrice(57,Unit.DKK,sit1);
        Price price8 = storage.getProductCategories().get(1).getProducts().get(0).createPrice(1,Unit.Klip,sit2);

        Price price9 = storage.getProductCategories().get(1).getProducts().get(1).createPrice(35,Unit.DKK,sit1);

        Rental rental1 = createRental("Julius Seerup", "Udlejer 2000 bajere til en stille fredag aften",LocalDate.of(2022,12,31));
        Rental rental2 = createRental("Peter Immersen", "Udlejer 2 mokai",LocalDate.now());
        Rental rental3 = createRental("Magnus Mejlgaard", "Udlejer 300 Sweet temptations fustager",LocalDate.now());
        Rental rental4 = createRental("Kristoffer Frank", "Udlejer en halv øl som han har svært ved at få ned, svagdrikker", LocalDate.now());

        rental1.createOrderLine(3,price1);
        rental2.createOrderLine(4,price);
        rental3.createOrderLine(5,price6);
        rental4.createOrderLine(1,price3);





    }
}
