package model.controller;

import model.modelklasser.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {
    private OrderController controller;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = mock(Storage.class);
        controller = new OrderController(storage);
    }

    /**
     * Does it return an empty list of situations from storage?
     */
    @Test
    void getSituations() {
        //Arange
        List<Situation> emptyList = new ArrayList<>();
        when(storage.getSituations()).thenReturn(emptyList);

        //Act
        List<Situation> recievedList = controller.getSituations();

        //Assert
        assertEquals(emptyList, recievedList);
        verify(storage).getSituations();
    }


    /**
     * Saves a created order to storage
     */
    @Test
    void saveOrder() {
        //Arrange
        Order mockedOrder = mock(Order.class);

        //Act
        controller.saveOrder(mockedOrder);

        //Assert
        verify(storage).addOrder(mockedOrder);
    }

    @Test
    void setEndDateForOrder() {
        //Arrange
        Order mockedOrder = mock(Order.class);
        LocalDate date = LocalDate.now();

        //Act
        controller.setEndDateForOrder(mockedOrder, date);

        //Assert
        verify(mockedOrder).setEndDate(date);
    }

    @Test
    void setDiscountForOrder() {
        //Arrange
        double discount = 20;
        Order mockedOrder = mock(Order.class);

        when(mockedOrder.getPercentDiscount()).thenReturn(discount);


        //Act
        controller.setDiscountForOrder(mockedOrder,discount);


        //Assert
        assertEquals(20, mockedOrder.getPercentDiscount());
        verify(mockedOrder).setPercentDiscount(20);

    }


    @Test
    void setFixedPriceForOrder() {
        //Arrange
        double fixedPrice = 200;
        Order mockedOrder = mock(Order.class);

        when(mockedOrder.getFixedPrice()).thenReturn(fixedPrice);


        //Act
        controller.setFixedPriceForOrder(mockedOrder,fixedPrice, Unit.DKK);


        //Assert
        assertEquals(200, mockedOrder.getFixedPrice());

    }

    @Test
    void getOrders() {
        //Arrange


        //Act
        controller.getOrders();

        //Assert

        verify(storage).getOrders();

    }



    @Test
    void getPaymentMethodForOrder() {
        //Arrange
        Order order = mock(Order.class);

        when(order.getPaymentMethod()).thenReturn(PaymentMethod.Dankort);
        //Act
        controller.getPaymentMethodForOrder(order);

        //Assert
        assertEquals(PaymentMethod.Dankort,order.getPaymentMethod());
    }

    @Test
    void setPaymentMethodForOrder() {
        //Arrange
        Order order = new Order(3);

        //Act
        controller.setPaymentMethodForOrder(order,PaymentMethod.Dankort);

        //Assert
        assertEquals(PaymentMethod.Dankort,order.getPaymentMethod());



    }

    @Test
    void createRental() {
        //Arrange
        LocalDate date = LocalDate.now();
        String name = "Peter";
        String description = "Udlejer 15 øl";

        //Act
        Rental rental = controller.createRental(name,description,date);

        //Assert
        assertEquals("Peter",rental.getName());
        assertEquals("Udlejer 15 øl",rental.getDescription());
        assertEquals(LocalDate.now(),date);
        verify(storage).addOrder(rental);

    }

    @Test
    void getRentals() {
        //Arrange

        //Act
        controller.getRentals();
        //Assert
        verify(storage).getOrders();
    }

    @Test
    void createTour() {
        //Arrange
        LocalTime startdate = LocalTime.now();
        LocalDate enddate = LocalDate.of(1997,12,20);

        //Act
        Tour tour = controller.createTour(enddate,startdate);

        //Assert
        assertEquals(enddate,tour.getEndDate());
        assertEquals(startdate,tour.getTime());


    }

    @Test
    void getProductCategories() {
        //Arrange


        //Act
        controller.getProductCategories();

        //Assert
        verify(storage).getProductCategories();
    }
}