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

    @Test
    void calculateSumPriceForUnit() {
        // Arrange
        Price price = mock(Price.class);
        when(price.getUnit()).thenReturn(Unit.DKK);
        Situation situation = mock(Situation.class);
        Order order = new Order(situation);

        // Opsætter vores mockedorderlines som der skal løbes igennem
        OrderLine mockOrderlineOne = mock(OrderLine.class);
        OrderLine mockOrderlineTwo = mock(OrderLine.class);
        OrderLine mockOrderlineThree = mock(OrderLine.class);
        ArrayList<OrderLine> listOfMockedOrderLines = new ArrayList<>();
        listOfMockedOrderLines.add(mockOrderlineOne);
        listOfMockedOrderLines.add(mockOrderlineTwo);
        listOfMockedOrderLines.add(mockOrderlineThree);

        when(order.getOrderLines()).thenReturn(listOfMockedOrderLines);
        when(mockOrderlineOne.getPrice()).thenReturn(price);
        when(mockOrderlineTwo.getPrice()).thenReturn(price);
        when(mockOrderlineThree.getPrice()).thenReturn(price);
        when(mockOrderlineOne.calculateOrderLinePrice()).thenReturn(30.0);
        when(mockOrderlineTwo.calculateOrderLinePrice()).thenReturn(30.0);
        when(mockOrderlineThree.calculateOrderLinePrice()).thenReturn(30.0);

        // Act
        double result = order.calculateSumPriceForUnit(Unit.DKK);

        //Assert
        assertTrue(order.getOrderLines().contains(mockOrderlineOne));
        assertTrue(order.getOrderLines().contains(mockOrderlineTwo));
        assertTrue(order.getOrderLines().contains(mockOrderlineThree));
        assertEquals(90,result);

    }
}