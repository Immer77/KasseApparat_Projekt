package model.modelklasser;

public class Product {

    private String name;
    private String description;


    /**
     * creates object of the Product class
     *
     * @param name name of the product
     * @param description a short description of the product
     */
    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
