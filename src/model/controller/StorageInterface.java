package model.controller;

import model.modelklasser.ProductCategory;
import java.util.List;

public interface StorageInterface {
    List<ProductCategory> getProductCategories();
    void addProductCategory(ProductCategory productCategory);

}
