package model.controller;

import gui.OrderControllerInterface;
import model.modelklasser.Order;
import model.modelklasser.Situation;
import model.modelklasser.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import storage.Storage;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderControllerTest {

    StorageInterface storage;
    OrderControllerInterface orderController;

    @BeforeEach
    void setUp() {
        storage = Storage.getStorage();
        orderController = OrderController.getOrderController(storage);
    }

    @Test
    void TC1_createSituation() {
        // Arrange
        String name = "Fredagsbar";

        // Act
        Situation situation = orderController.createSituation(name);

        // Assert
        assertEquals("Fredagsbar",situation.getName());
        assertTrue(orderController.getSituations().contains(situation));
    }

    @Test
    void TC1_createOrder() {
        // Arrange
        // Since we are located on the same architechtural layer we can mock the class without an interface.
        Situation mocksituation = mock(Situation.class);

        // Act
        Order order = orderController.createOrder(mocksituation);


        // Assert
        assertTrue(orderController.getOrders().contains(order));
        assertEquals(mocksituation,order.getSituation());
    }

}