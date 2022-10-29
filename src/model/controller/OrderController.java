package model.controller;

import gui.OrderControllerInterface;
import model.modelklasser.*;

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
     * Creates an order and adds it to the storage
     * @param situation
     * @return
     */
    public Order createOrder(Situation situation) {
        Order order = new Order(situation);
        storage.addOrder(order);
        return order;
    }

    public void removeOrder(Order order) {
        storage.removeOrder(order);
    }

    public List<Order> getOrders() {
        return storage.getOrders();
    }

    public OrderLine createOrderLineForOrder (Order order, int amount, Price price) {
        OrderLine currentOrderLine = order.createOrderLine(amount, price);
        return currentOrderLine;
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
    }
}
