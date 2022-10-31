package model.controller;

import gui.ProductOverviewControllerInterface;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import model.modelklasser.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;

import java.util.ArrayList;


class ProductOverviewControllerTest {
    StorageInterface storage;
    ProductOverviewControllerInterface controller;


    /**
     *
     */
    @BeforeEach
    void setUp() {
        storage = mock(Storage.class);
        controller = ProductOverviewController.getProductOverviewController(storage);
    }

    @Test
    void TC1_createProductCategory() {
        // Arrange
        String title = "Øl";
        String description = "6.0% alc. ekstra pilsner";

        // Act
        ProductCategory productCategory = controller.createProductCategory(title,description);

        // Assert
        assertEquals("Øl",productCategory.getTitle());
        assertEquals("6.0% alc. ekstra pilsner",productCategory.getDescription());
        verify(storage).addProductCategory(productCategory);
    }
}