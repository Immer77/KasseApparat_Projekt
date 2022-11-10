package gui;

import model.modelklasser.Price;
import model.modelklasser.Product;
import model.modelklasser.ProductCategory;
import model.modelklasser.Situation;

import java.util.List;

public interface ProductOverviewControllerInterface {

    /**
     * Creates a new ProductCategory
     * @param title the Title of the new category. Cannot be null
     * @param description a description of the new category
     * @return returns the new ProductCategory
     */
    ProductCategory createProductCategory (String title, String description);

    /**
     * Creates a new product in the given category
     * @param productCategory the ProductCategory to which the new Product will belong
     * @param name the name of the product. cannot be null.
     * @param description the description of the product
     * @return returns the new Product.
     */
    Product createProductForCategory(ProductCategory productCategory, String name, String description);

    /**
     * Returns all current ProductCategories
     * @return a list of all ProductCategories.
     */
    List<ProductCategory> getProductCategories ();

    /**
     * Creates a situation and adds it to the situations list
     * @param name of the situation
     * @return situation
     */
    Situation createSituation(String name);

    /**
     * Returns a list of all situations
     * @return a List of Situations
     */
    List<Situation> getSituations ();

    /**
     * Removes the price from the product
     * @param price the price to remove
     * @param product the product from which to remove the price
     */
    void removePriceFromProduct (Price price, Product product);

    /**
     * Sets a new title for this category
     * @param title the new title of the category
     * @param category the category to get an updated title
     */
    void setTitleForCategory (String title, ProductCategory category);

    /**
     * Sets a new description for a ProductCategory
     * @param description the new description
     * @param category the category to recieve the new description
     */
    void setDescriptionForCategory (String description, ProductCategory category);

    /**
     * Sets a new name for the provided Product
     * @param name the new name of the product
     * @param product the product to rename
     */
    void setNameForProduct (String name, Product product);

    /**
     * sets a new description for the provided Product
     * @param description the new description
     * @param product the product to describe
     */
    void setDescriptionForProduct (String description, Product product);

    /**
     * Removes the given Situation object from storage
     * @param situation the object to remove
     */
    void removeSituation (Situation situation);

}
