package model.controller;

import model.modelklasser.ProductCategory;

public class ProductController {
    //Fields ---------------------------------------------------------------------------------------------------------
    private ProductController unique_ProductController;
    private StorageInterface storage;

    //Constructors ---------------------------------------------------------------------------------------------------

    /**
     * Creates a new ProductController object.
     * @param storage the storage this object should use, using the StorageInterface interface.
     */
    private ProductController (StorageInterface storage) {
        this.storage = storage;
    }

    //Methods - Get, Set & Add ---------------------------------------------------------------------------------------

    /**
     * Returns the unique ProductController. If none exists, creates one.
     * @return unique ProductController object
     */
    public ProductController getProductController () {
        if (unique_ProductController == null) {
            unique_ProductController = new ProductController();
        }
        return unique_ProductController;
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
        Product current = ProductCategory.createProduct (String name, String description );
        return current;
    }

    /**
     * Returns all current ProductCategories
     * @return a list of all ProductCategories.
     */
    public List<ProductCategory> getProductCategories () {
        return storage.getProductCategories;
    }

}
