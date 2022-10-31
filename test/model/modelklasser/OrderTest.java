package model.modelklasser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderTest {

    /**
     * Not done yet, need to consult with group members
     */
    @Test
    void TC1_calculateSumPriceForUnit() {
        // Arrange
        Price price = mock(Price.class);
        Situation situation = mock(Situation.class);
        Order order = new Order(situation);

        // Simulerede 3 forskellige priser
        when(price.getValue()).thenReturn(30.0,40.0,20.0);
        when(price.getUnit()).thenReturn(Unit.DKK);

        // Setting up our orderlines
        OrderLine orderLine1 = order.createOrderLine(1,price);
        OrderLine orderLine2 = order.createOrderLine(1,price);
        OrderLine orderLine3 = order.createOrderLine(1,price);

        // Act
        double result = order.calculateSumPriceForUnit(Unit.DKK);

        //Assert
        assertEquals(90,result);
        assertTrue(order.getOrderLines().contains(orderLine1));
        assertTrue(order.getOrderLines().contains(orderLine2));
        assertTrue(order.getOrderLines().contains(orderLine3));

    }
}