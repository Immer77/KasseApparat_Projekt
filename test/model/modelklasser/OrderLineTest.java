package model.modelklasser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderLineTest {

    @Test
    void TC1_calculateOrderLinePrice() {
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

    @Test
    void TC2_calculateOrderLinePrice() {
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

    @Test
    void TC3_calculateOrderLinePrice() {
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