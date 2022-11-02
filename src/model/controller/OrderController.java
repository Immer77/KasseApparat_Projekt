package model.controller;

import gui.OrderControllerInterface;
import model.modelklasser.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderController implements OrderControllerInterface {

    //Field Variables
    private static OrderControllerInterface unique_OrderController;
    private StorageInterface storage;

    /**
     * Private ordercontroller supporting singleton pattern
     * @param storage
     */
    private OrderController(StorageInterface storage){
        this.storage = storage;
    }

    /**
     * Singleton pattern where we only have one unique ordercontroller
     * @param storage programming to interfaces
     * @return current controller or new unique controller
     */
    public static OrderControllerInterface getOrderController(StorageInterface storage){
        if(unique_OrderController == null){
            unique_OrderController = new OrderController(storage);
        }
        return unique_OrderController;

    }

    /**
     * Bruges til test for ikke at have singleton pattern til at melde fejl
     * @param storage
     * @return
     */
    public static OrderControllerInterface getOrderControllerTest(StorageInterface storage){
        unique_OrderController = new OrderController(storage);
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

    public Rental createRental(String name, String description, LocalDate endDate) {
        Rental rental = new Rental(name,description,endDate);
        rental.setStartDate(LocalDate.now());
        storage.addOrder(rental);
        return rental;
    }

    public List<Rental> getRentals(){
        ArrayList<Rental> rentals = new ArrayList<>();
        for(Order order : storage.getOrders()){
            if(order instanceof Rental){
                rentals.add((Rental) order);
            }
        }
        return rentals;
    }





    public void initContent() {
        Situation sit1 = createSituation("Standard");
        Situation sit2 = createSituation("Fredagsbar");

        storage.getProductCategories().get(0).getProducts().get(0).createPrice(70,Unit.DKK,sit2);
        storage.getProductCategories().get(0).getProducts().get(0).createPrice(35,Unit.DKK,sit1);
        storage.getProductCategories().get(0).getProducts().get(0).createPrice(2,Unit.Klip,sit2);

        storage.getProductCategories().get(0).getProducts().get(1).createPrice(75,Unit.DKK,sit2);
        storage.getProductCategories().get(0).getProducts().get(1).createPrice(35,Unit.DKK,sit1);
        storage.getProductCategories().get(0).getProducts().get(1).createPrice(2,Unit.Klip,sit2);

        storage.getProductCategories().get(1).getProducts().get(0).createPrice(170,Unit.DKK,sit2);
        storage.getProductCategories().get(1).getProducts().get(0).createPrice(57,Unit.DKK,sit1);
        storage.getProductCategories().get(1).getProducts().get(0).createPrice(1,Unit.Klip,sit2);

        storage.getProductCategories().get(1).getProducts().get(1).createPrice(35,Unit.DKK,sit1);

        createRental("Julius Seerup", "Udlejer 2000 bajere til en stille fredag aften",LocalDate.now());
        createRental("Peter Immersen", "Udlejer 2 mokai",LocalDate.now());
        createRental("Magnus Mejlgaard", "Udlejer 300 Sweet temptations fustager",LocalDate.now());
        createRental("Kristoffer Frank", "Udlejer en halv øl som han har svært ved at få ned, svagdrikker", LocalDate.now());


    }
}
