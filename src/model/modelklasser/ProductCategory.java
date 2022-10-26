package model.modelklasser;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory {
    private String title;
    private String description;
    private List<Product> products = new ArrayList<>();

    public ProductCategory(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Product createProduct(String name, String description){
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

    @Override
    public String toString() {
        return "ProductCategory{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
