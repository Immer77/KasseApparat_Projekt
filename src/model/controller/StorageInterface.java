package model.controller;

import model.modelklasser.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public interface StorageInterface {
    List<ProductCategory> getProductcategories();
    void addProductCategory(ProductCategory productCategory);

}
