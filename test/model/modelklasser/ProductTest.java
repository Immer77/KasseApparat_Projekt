package model.modelklasser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductTest {
    // Field variable
    Product product;

    /**
     * Setup method for setting up a product that we will use when testing
     */
    @BeforeEach
    void setUp() {
        product = new Product("Ekstra pilsner", "Lidt mere pilsner");
    }

    /**
     * test case for the creation of a price object
     */
    @Test
    void TC1_createPrice_dkk_fredagsbar_0() {
        // Arrange
        double value = 0;
        Situation situation = mock(Situation.class);

        // Act
        Price price = product.createPrice(value,Unit.DKK,situation);


        // Assert
        assertEquals(0,price.getValue());
        assertTrue(product.getPrices().contains(price));
        assertEquals(situation,price.getSituation());
    }

    @Test
    void TC2_createPrice_dkk_butik_35() {
        // Arrange
        double value = 35;
        Situation situation = mock(Situation.class);

        // Act
        Price price = product.createPrice(value,Unit.DKK,situation);


        // Assert
        assertEquals(35,price.getValue());
        assertTrue(product.getPrices().contains(price));
        assertEquals(situation,price.getSituation());
    }
}