package model.modelklasser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.PrivateKey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderTest {
    Price price;
    Order order;


    @BeforeEach
    void setUp() {
        price = mock(Price.class);
        order = new Order();
    }

    @Test
    void TC1_calculateSumPriceForUnit() {
        // Arrange

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

    /**
     * Test af Createorderline
     */
    @Test
    void TCx_standard_dkk_flaskeøl_1stkFrigattenJylland_0Rabat_ingenAftaltTotal() {
        // Arrange
        int amount = 1;
        when(price.getValue()).thenReturn(35.0);

        // Act
        OrderLine orderLine = order.createOrderLine(amount, price);


        // Assert
        assertTrue(order.getOrderLines().contains(orderLine));
        assertEquals(35,orderLine.getPrice().getValue());

    }

    @Test
    void TCx_standard_dkk_flaskeøl_15stkFrigattenJylland_0Rabat_ingenAftaltTotal() {
        // Arrange
        int amount = 1;
        when(price.getValue()).thenReturn(525.0);

        // Act
        OrderLine orderLine = order.createOrderLine(amount, price);


        // Assert
        assertTrue(order.getOrderLines().contains(orderLine));
        assertEquals(525,orderLine.getPrice().getValue());
    }


}