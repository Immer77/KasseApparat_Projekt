package gui;

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
    public Situation createSituation(String name);

    /**
     * Returns a list of all situations
     * @return a List of Situations
     */
    List<Situation> getSituations ();

}
