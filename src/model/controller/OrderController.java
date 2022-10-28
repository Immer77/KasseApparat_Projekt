package model.controller;

import gui.OrderControllerInterface;
import gui.ProductOverviewControllerInterface;

import java.util.List;

public class OrderController implements OrderControllerInterface {
    private static OrderControllerInterface unique_OrderController;
    private StorageInterface storage;

    private OrderController(StorageInterface storage){
        this.storage = storage;
    }

    public static OrderControllerInterface getOrderController(StorageInterface storage){
        if(unique_OrderController == null){
            unique_OrderController = new OrderController(storage);
        }
        return unique_OrderController;

    }

    public Situation createSituation(String name) {
        Situation situation = new Situation(name);
        storage.addSituation(situation);
        return situation;
    }

    public List<Situation> getSituation() {
        return storage.getSituations();
    }

    public Order createOrder(Unit unit, Situation situation) {
        Order order = new Order(unit, situation);
        storage.addOrder(order);
        return order;
    }

    public List<Order> getOrders() {
        return storage.getOrders();
    }
}
