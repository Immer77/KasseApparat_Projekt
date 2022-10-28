package model.controller;

import gui.ProductOverviewControllerInterface;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import model.modelklasser.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ProductOverviewControllerTest {


    @BeforeEach
    void setUp() {

    }

    @Test
    void TC1_createProductCategory() {
        // Arrange
        String title = "Øl";
        String description = "6.0% alc. ekstra pilsner";
        StorageInterface mockStorage = mock(StorageInterface.class);
        ProductOverviewControllerInterface controller = ProductOverviewController.getProductOverviewController(mockStorage);

        // Act
        ProductCategory productCategory = controller.createProductCategory(title,description);

        // Assert
        assertTrue(mockStorage.getProductCategories().contains(productCategory));



    }
}