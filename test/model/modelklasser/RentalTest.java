package model.modelklasser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RentalTest {

    Order order;
    Price priceWithPant;


    @BeforeEach
    void setUp() {
        order = new Order();
        priceWithPant = mock(Price.class);
    }

    /**
     * Udregner samlet deposit værdi for alle ordrelinjer i en ordre
     */
    @Test
    void TC1_calculateDeposit() {
        // Arrange

        // Simulerede 3 forskellige priser
        when(priceWithPant.getProduct().getDepositPrice().getValue()).thenReturn(1.0,1.5,3.0);
        when(priceWithPant.getUnit()).thenReturn(Unit.DKK);

        // Setting up our orderlines
        OrderLine orderLine1 = order.createOrderLine(1,priceWithPant);
        OrderLine orderLine2 = order.createOrderLine(1,priceWithPant);
        OrderLine orderLine3 = order.createOrderLine(1,priceWithPant);

        // Act
        double result = order.calculateSumPriceForUnit(Unit.DKK);

        //Assert
        assertEquals(5.5,result);
        assertTrue(order.getOrderLines().contains(orderLine1));
        assertTrue(order.getOrderLines().contains(orderLine2));
        assertTrue(order.getOrderLines().contains(orderLine3));

    }
}
