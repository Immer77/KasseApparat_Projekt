package model.controller;

import model.modelklasser.Product;
import model.modelklasser.ProductCategory;

import java.util.List;

public class ProductOverviewController {
    //Fields ---------------------------------------------------------------------------------------------------------
    private static ProductOverviewController unique_ProductOverviewController;
    private StorageInterface storage;

    //Constructors ---------------------------------------------------------------------------------------------------

    /**
     * Creates a new ProductOverviewController object.
     * @param storage the storage this object should use, using the StorageInterface interface.
     */
    private ProductOverviewController(StorageInterface storage) {
        this.storage = storage;
    }

    //Methods - Get, Set & Add ---------------------------------------------------------------------------------------
    /**
     * Returns the unique ProductOverviewController. If none exists, creates one.
     * @return unique ProductOverviewController object
     */
    public static ProductOverviewController getProductOverviewController (StorageInterface storage) {
        if (unique_ProductOverviewController == null) {
            unique_ProductOverviewController = new ProductOverviewController(storage);
        }
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
    public Product createProduct (ProductCategory productCategory, String name, String description) {
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

}
