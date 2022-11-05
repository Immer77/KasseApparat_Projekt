package model.controller;

import gui.ProductOverviewControllerInterface;
import model.modelklasser.Product;
import model.modelklasser.ProductCategory;
import model.modelklasser.Situation;

import java.util.List;

public class ProductOverviewController implements ProductOverviewControllerInterface {
    //Fields ---------------------------------------------------------------------------------------------------------
    private StorageInterface storage;

    //Constructors ---------------------------------------------------------------------------------------------------

    /**
     * Creates a new ProductOverviewController object.
     * @param storage the storage this object should use, using the StorageInterface interface.
     */
    public ProductOverviewController(StorageInterface storage) {
        this.storage = storage;
    }

    //Methods - Get, Set & Add ---------------------------------------------------------------------------------------

    /**
     * Bruges til test
     * @param storage
     * @return
     */
    public static ProductOverviewControllerInterface getProductOverviewControllerTest(StorageInterface storage) {
        ProductOverviewControllerInterface unique_ProductOverviewController = new ProductOverviewController(storage);
        return unique_ProductOverviewController;
    }

    //Methods - Other ------------------------------------------------------------------------------------------------

    /**
     * Creates a new ProductCategory
     * @param title the Title of the new category. Cannot be null
     * @param description a description of the new category
     * @return returns the new ProductCategory
     */
    public ProductCategory createProductCategory (String title, String description) {
        ProductCategory current = new ProductCategory(title, description);
        storage.addProductCategory(current);
        return current;
    }

    /**
     * Creates a new product in the given category
     * @param productCategory the ProductCategory to which the new Product will belong
     * @param name the name of the product. cannot be null.
     * @param description the description of the product
     * @return returns the new Product.
     */
    public Product createProductForCategory(ProductCategory productCategory, String name, String description) {
        Product current = productCategory.createProduct(name, description);
        return current;
    }

    /**
     * Returns all current ProductCategories
     * @return a list of all ProductCategories.
     */
    public List<ProductCategory> getProductCategories () {
        return storage.getProductCategories();
    }

    /**
     * Returns a list of all situations
     * @return a List of Situations
     */
    public List<Situation> getSituations () {
        return storage.getSituations();
    }


    /**
     * Creates initial contents for storage
     */
    public void initContent() {
        ProductCategory pc1 = createProductCategory("Flaskeøl", "Øl på flaske");
        pc1.createProduct("Frigatten Jylland", "60cl, 8%");
        pc1.createProduct("Extra Pilsner", "60cl, 5%");

        ProductCategory pc2 = createProductCategory("Spiritus", "Spiritus på flaske");
        pc2.createProduct("Whiskey", "Whiskey fra Aarhus");
        pc2.createProduct("Gin", "Ginormous");
    }

}
