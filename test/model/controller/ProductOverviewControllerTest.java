package model.controller;

import gui.ProductOverviewControllerInterface;
import model.modelklasser.Product;
import model.modelklasser.ProductCategory;
import model.modelklasser.Situation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductOverviewControllerTest {
    StorageInterface storage;
    ProductOverviewControllerInterface controller;

    /**
     *
     */
    @BeforeEach
    void setUp() {
        storage = mock(Storage.class);
        controller = ProductOverviewController.getProductOverviewControllerTest(storage);
    }

    @Test
    void TC1_createProductCategory() {
        // Arrange
        String title = "Øl";
        String description = "6.0% alc. ekstra pilsner";

        // Act
        ProductCategory productCategory = controller.createProductCategory(title, description);

        // Assert
        assertEquals("Øl", productCategory.getTitle());
        assertEquals("6.0% alc. ekstra pilsner", productCategory.getDescription());
        verify(storage).addProductCategory(productCategory);
    }


    @Test
    void TC2_createProduct() {
        //Arrange
        ProductCategory productCategory = new ProductCategory("Øl", "6.0% alc. ekstra pilsner");
        String name = "Regnbue Bajer";
        String description = "6% alc. pride piler";

        //Act
        Product testProduct = controller.createProductForCategory(productCategory, name, description);

        //Assert
        assertEquals("Regnbue Bajer", testProduct.getName());
        assertEquals("6% alc. pride piler", testProduct.getDescription());
    }

    @Test
    void TC1_createSituationWithNameFredagsbar() {
        // Arrange
        String name = "Fredagsbar";

        // Act
        Situation situation = controller.createSituation(name);

        // Assert
        assertEquals("Fredagsbar", situation.getName());
        // Verifies that storage .addsituation gets called
        verify(storage).addSituation(situation);
    }

    @Test
    void TC2_CreateSituationWhereNameIsBlank() {
        // Arrange
        String name = "";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.createSituation(name));

        // Assert
        assertEquals("En ny salgssituation skal have et navn", exception.getMessage());
    }
}