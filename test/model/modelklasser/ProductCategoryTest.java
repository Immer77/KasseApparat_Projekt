package model.modelklasser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryTest {
    ProductCategory productCategory;

    @BeforeEach
    void setUp() {
        productCategory = new ProductCategory("Øl","Bajselademadder");
    }

    /**
     * Testing to see if when product is created it succesfully adds it to the productcategory list.
     */
    @Test
    void TC1_createProduct() {
        // Arrange
        String name = "Extra Pilsner";
        String description = "5,1% alc. En pilsner der er lidt mere pilsner";

        // Act
        Product productTest = productCategory.createProduct(name,description);

        // Assert
        assertTrue(productCategory.getProducts().contains(productTest));
    }
}