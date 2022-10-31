package model.controller;

import gui.OrderControllerInterface;
import gui.ProductOverviewControllerInterface;
import model.modelklasser.Order;
import model.modelklasser.Situation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import javax.lang.model.util.SimpleElementVisitor14;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
    void TC1_createSituationWithNameFredagsbar() {
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
    void TC1_createOrderWithSituationFredagsbar() {
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

    @Test
    void TC1_CreateOrderLineForOrder() {
        // Arrange

    }
}