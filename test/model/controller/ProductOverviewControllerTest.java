package model.controller;

import gui.ProductOverviewControllerInterface;
import model.modelklasser.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductOverviewControllerTest {
    StorageInterface mockStorage;

    @BeforeEach
    void setUp() {

    }

    @Test
    void TC1_createProductCategory() {
        // Arrange
        String title = "Øl";
        String description = "6.0% alc. ekstra pilsner";
        mockStorage = mock(StorageInterface.class);
        ProductOverviewControllerInterface controller = ProductOverviewController.getProductOverviewController(mockStorage);
        when(controller.createProductCategory(title,description));

        // Act
        ProductCategory productCategory = controller.createProductCategory(title,description);

        // Assert




    }
}