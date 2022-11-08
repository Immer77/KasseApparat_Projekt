package model.controller;

import model.modelklasser.Order;
import model.modelklasser.ProductCategory;
import model.modelklasser.Situation;

import java.util.List;

public interface StorageInterface {
    /**
     * Methods that needs to be added to storage classes
     * @return
     */
    // Gets all productcategories aswell as adding a productcategory to storage
    List<ProductCategory> getProductCategories();
    void addProductCategory(ProductCategory productCategory);

    // Gets all orders aswell as adding order to all orders
    List<Order> getOrders();
    void addOrder(Order order);

    // Removesorder from storage
    void removeOrder(Order order);

    // Gets all Situations aswell as adding situation to situations
    List<Situation> getSituations();
    void addSituation(Situation situation);

    /**
     * Removes the given situation from storage
     * @param situation the situation object to remove from storage
     */
    public void removeSituation (Situation situation);




}
