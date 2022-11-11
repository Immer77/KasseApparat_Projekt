package model.modelklasser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryTest {
    // Field variables
    ProductCategory productCategory;

    /**
     * Setup method to set up the productcategory
     */
    @BeforeEach
    void setUp() {
        productCategory = new ProductCategory("Øl", "Bajselademadder");
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
        Product productTest = productCategory.createProduct(name, description);

        // Assert
        assertEquals("Extra Pilsner", productTest.getName());
        assertEquals("5,1% alc. En pilsner der er lidt mere pilsner", productTest.getDescription());
        assertTrue(productCategory.getProducts().contains(productTest));
    }

    @Test
    void TC2_setTitle_BlankTitleError() {
        //Arrange
        String blankTitle = "";
        String expectedMessage = "Titlen på en produktkategory kan ikke være blank";

        //Act & Assert
        Exception thrownExteption = assertThrows(IllegalArgumentException.class, () -> productCategory.setTitle(blankTitle));
        assertEquals(expectedMessage, thrownExteption.getMessage());
    }
}