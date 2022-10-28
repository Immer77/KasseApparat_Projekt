package model.controller;

import gui.OrderControllerInterface;
import model.modelklasser.Order;
import model.modelklasser.Product;
import model.modelklasser.Situation;
import model.modelklasser.Unit;

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

    public List<Situation> getSituations() {
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
