package model.controller;

import model.modelklasser.Order;
import model.modelklasser.Situation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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


        //Act


        //Assert

    }


    @Test
    void setFixedPriceForOrder() {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void getOrders() {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void createOrderLineForOrder() {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void getPaymentMethodForOrder() {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void setPaymentMethodForOrder() {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void createRental() {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void getRentals() {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void getActiveRentals() {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void getDoneRentals() {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void createTour() {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void getTours() {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void getProductCategories() {
        //Arrange


        //Act


        //Assert

    }
}