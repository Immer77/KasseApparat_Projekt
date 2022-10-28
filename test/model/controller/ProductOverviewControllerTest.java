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

    @Test
    void TC1_createProductCategory() {
        // Arrange
        StorageInterface storage = Storage.getStorage();
        ProductOverviewControllerInterface controller = ProductOverviewController.getProductOverviewController(storage);
        String title = "Øl";
        String description = "6.0% alc. ekstra pilsner";

        // Act
        ProductCategory productCategory = controller.createProductCategory(title,description);

        // Assert
        assertTrue(controller.getProductCategories().contains(productCategory));
    }
}