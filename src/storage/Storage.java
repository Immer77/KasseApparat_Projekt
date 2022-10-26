package storage;

import model.controller.StorageInterface;
import model.modelklasser.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class Storage implements StorageInterface {
    private static Storage unique_Storage;
    private List<ProductCategory> productCategories;

    // Constructor
    private Storage(){
        productCategories = new ArrayList<>();
    }

    /**
     * We have here used the singletonPattern, so as we do not have different storages
     * @return Returns storage if storage != null else it creates a new storage and returns it.
     */
    public static Storage getUnique_Storage() {
        if(unique_Storage == null){
            unique_Storage = new Storage();
        }
        return unique_Storage;
    }

    /**
     * Returns a list of all the productcategories
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategories() {
        return new ArrayList<>(productCategories);
    }

    /**
     * Adds a productcategory to the list of productcategories.
     * @param productCategory
     */
    @Override
    public void addProductCategory(ProductCategory productCategory) {
        if(!productCategories.contains(productCategory)){
            productCategories.add(productCategory);
        }
    }
}
