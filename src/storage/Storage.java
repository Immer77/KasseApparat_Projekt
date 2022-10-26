package storage;

import model.controller.StorageInterface;
import model.modelklasser.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class Storage implements StorageInterface {

    private List<ProductCategory> productCategories = new ArrayList<>();

    @Override
    public List<ProductCategory> getProductCategories() {
        return new ArrayList<>(productCategories);
    }

    @Override
    public void addProductCategory(ProductCategory productCategory) {
        if(!productCategories.contains(productCategory)){
            productCategories.add(productCategory);
        }
    }
}
