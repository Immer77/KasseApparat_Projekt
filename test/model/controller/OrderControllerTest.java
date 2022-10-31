package model.controller;

import gui.OrderControllerInterface;
import model.modelklasser.Order;
import model.modelklasser.Situation;
import model.modelklasser.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import storage.Storage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    StorageInterface storage;
    OrderControllerInterface orderController;

    @BeforeEach
    void setUp() {
        // We need to mock the storage to make sure that we are creating a unit test
        storage = mock(Storage.class);
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
        // Verifies that .addsituation gets called on our mockedstorageobjekt
        verify(storage).addSituation(situation);
    }

    @Test
    void TC1_createOrder() {
        // Arrange
        Situation mocksituation = mock(Situation.class);
        ArrayList<Order> orders = new ArrayList<>();

        // Act
        Order order = orderController.createOrder(mocksituation);
        orders.add(order);

        // Assert
        assertEquals(mocksituation,order.getSituation());
        // Verifies that .addorder
        assertTrue(orders.contains(order));

    }

}