package model.modelklasser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderLineTest {

    /**
     * Test the calculateordelineprice with amount of 3
     */
    @Test
    void TC1_calculateOrderLinePriceWithAmountThree() {
        // Arrange
        Price price = mock(Price.class);
        int amount = 3;
        OrderLine orderLine = new OrderLine(amount,price);
        when(price.getValue()).thenReturn(35.0);

        // Act
        double result = orderLine.calculateOrderLinePrice();

        // Assert
        assertEquals(105,result);
    }

    /**
     * Test the calculateordelineprice with amount of 0
     */
    @Test
    void TC2_calculateOrderLinePriceWithAmountZero() {
        // Arrange
        Price price = mock(Price.class);
        int amount = 0;
        OrderLine orderLine = new OrderLine(amount,price);
        when(price.getValue()).thenReturn(35.0);

        // Act
        double result = orderLine.calculateOrderLinePrice();

        // Assert
        assertEquals(0,result);
    }

    /**
     * Test the calculateordelineprice with amount -3
     * expects to throw an exception
     */
    @Test
    void TC3_calculateOrderLinePriceThatHoldsANegativeAmount() {
        // Arrange
        Price price = mock(Price.class);
        int amount = -3;
        OrderLine orderLine = new OrderLine(amount,price);
        when(price.getValue()).thenReturn(35.0);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,() -> {
            orderLine.calculateOrderLinePrice();
        });


                // Assert
        assertEquals(exception.getMessage(),"Number must not be negative");
    }


}