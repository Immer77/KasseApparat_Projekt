package storage;

import model.controller.StorageInterface;
import model.modelklasser.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class Storage implements StorageInterface {
    private static Storage unique_Storage;
    private List<ProductCategory> productCategories;

    private Storage(){
        productCategories = new ArrayList<>();
    }

    public static Storage getUnique_Storage() {
        if(unique_Storage == null){
            unique_Storage = new Storage();
        }
        return unique_Storage;
    }

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
