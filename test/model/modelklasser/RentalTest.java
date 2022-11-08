package model.modelklasser;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RentalTest {

    Order order;
    Rental rental;
    Price priceWithPant;


    @BeforeEach
    void setUp() {
        String name = "ØlTilHavefest";
        String description = "Øl til min solo havefest";
        LocalDate endDate = LocalDate.of(2022,12,1);
        order = new Order();
        rental = new Rental(name,description,endDate);
    }

    /**
     * Udregner samlet deposit værdi for alle ordrelinjer i en ordre
     */
    @Test
    void TC1_calculateDepositForOneBeer() {
        // Arrange
        Situation situation = mock(Situation.class);
        Product product1 = new Product("Øl1","noget øl");
        Price pantPris1 = product1.createDeposit(1.0,Unit.DKK,situation);



        // Setting up our orderlines
        OrderLine orderLine1 = rental.createOrderLine(1,pantPris1);



        // Act
        double result = rental.calculateDeposit();

        //Assert
        assertEquals(1.0,result);
        assertTrue(rental.getOrderLines().contains(orderLine1));
    }

    @Test
    void TC2_calculateDepositForThreeBeers() {
        // Arrange
        Situation situation = mock(Situation.class);
        Product product1 = new Product("Øl1","noget øl");
        Price pantPris1 = product1.createDeposit(1.0,Unit.DKK,situation);
        Product product2 = new Product("Øl2","noget øl");
        Price pantPris2 = product2.createDeposit(1.5,Unit.DKK,situation);
        Product product3 = new Product("Øl3","noget øl");
        Price pantPris3 = product3.createDeposit(3.0,Unit.DKK,situation);


        // Setting up our orderlines
        OrderLine orderLine1 = rental.createOrderLine(1,pantPris1);
        OrderLine orderLine2 = rental.createOrderLine(1,pantPris2);
        OrderLine orderLine3 = rental.createOrderLine(1,pantPris3);


        // Act
        double result = rental.calculateDeposit();

        //Assert
        assertEquals(5.5,result);
        assertTrue(rental.getOrderLines().contains(orderLine1));
        assertTrue(rental.getOrderLines().contains(orderLine2));
        assertTrue(rental.getOrderLines().contains(orderLine3));

    }
}
