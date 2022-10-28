package model.controller;

import gui.ProductOverviewControllerInterface;
import static org.junit.jupiter.api.Assertions.*;
import model.modelklasser.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;


class ProductOverviewControllerTest {


    @BeforeEach
    void setUp() {

    }

    @Test
    void TC1_createProductCategory() {
        // Arrange
        String title = "Øl";
        String description = "6.0% alc. ekstra pilsner";
        StorageInterface storage = Storage.getUnique_Storage();
        ProductOverviewControllerInterface controller = ProductOverviewController.getProductOverviewController(storage);

        // Act
        ProductCategory productCategory = controller.createProductCategory(title,description);

        // Assert
        assertTrue(storage.getProductCategories().contains(productCategory));



    }
}