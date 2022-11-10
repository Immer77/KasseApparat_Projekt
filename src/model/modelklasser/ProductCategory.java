package model.modelklasser;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory {
    private String title;
    private String description;
    private List<Product> products = new ArrayList<>();

    /**
     * creates object of the ProductCategoy class
     *
     * @param title       name of the product category
     * @param description a short desciption of what the product category contains
     */
    public ProductCategory(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /**
     * creates an object of the Product and adds it to the products list on the product category
     *
     * @param name        name of the product
     * @param description a short description of the product
     * @return the product that is created and added
     */
    public Product createProduct(String name, String description) {
        Product product = new Product(name, description);
        products.add(product);
        return product;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    /**
     * Sets a new title for the product.
     *
     * @param title the new title. Cannot be blank
     */
    public void setTitle(String title) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("Titlen på en produktkategory kan ikke være blank");
        } else {
            this.title = title;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String returnValue = title;
        if (!description.isBlank()) {
            returnValue += " (" + description + ")";
        }
        return returnValue;
    }
}
