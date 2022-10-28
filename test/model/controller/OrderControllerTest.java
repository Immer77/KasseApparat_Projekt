package model.controller;

import com.sun.javafx.print.Units;
import gui.OrderControllerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class OrderControllerTest {

    StorageInterface storageInterface;
    OrderControllerInterface orderController;

    @BeforeEach
    void setUp() {
        orderController = OrderController.getOrderController(storageInterface);
    }

    @Test
    void TC1_createSituation() {
        // Arrange

        // Act

        // Assert

    }

    @Test
    void TC1_createOrder() {
        // Arrange
        Unit unit = new Mock(Unit.class);
        Situation situation = new Mock(Situation.class);

        // Act
        Order order = orderController.createOrder(unit,situation);

        // Assert
        assertTrue(orderController.getOrders().contains(order));
    }

    @Test
    void TC2_createOrder() {
        // Arrange
        Unit unit = new Mock(Unit.class);
        Situation situation = new Mock(Situation.class);

        // Act
        Order order = orderController.createOrder(unit,situation);

        // Assert
        assertTrue(orderController.getOrders().contains(order));
    }

    @Test
    void TC3_createOrder() {
        // Arrange
        Unit unit = new Mock(Unit.class);
        Situation situation = new Mock(Situation.class);

        // Act
        Order order = orderController.createOrder(unit,situation);

        // Assert
        assertTrue(orderController.getOrders().contains(order));
    }

    @Test
    void TC4_createOrder() {
        // Arrange
        Unit unit = new Mock(Unit.class);
        Situation situation = new Mock(Situation.class);

        // Act
        Order order = orderController.createOrder(unit,situation);

        // Assert
        assertTrue(orderController.getOrders().contains(order));
    }
}