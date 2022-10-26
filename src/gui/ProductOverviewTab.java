package gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.controller.ProductOverviewController;
import model.modelklasser.Product;
import model.modelklasser.ProductCategory;
import storage.Storage;

public class ProductOverviewTab extends GridPane {

    private final ListView<Product> lvwProducts = new ListView<>();
    private final ListView<ProductCategory> lvwCategories = new ListView<>();
    private ProductOverviewController productController = ProductOverviewController.getProductOverviewController(Storage.getUnique_Storage());

    public ProductOverviewTab() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        //Categoy Label
        Label lblNameKategori = new Label("Produktkategorier:");
        this.add(lblNameKategori, 0, 0);

        //Product Label
        Label lblNameProdukt = new Label("Produkt:");
        this.add(lblNameProdukt, 1, 0);

        //List View of categories
        this.add(lvwCategories, 0, 1);
        lvwCategories.setPrefWidth(200);
        lvwCategories.setPrefHeight(200);
        lvwCategories.getItems().setAll();

        //List View of products
        this.add(lvwProducts, 1, 1);
        lvwProducts.setPrefWidth(200);
        lvwProducts.setPrefHeight(200);
        lvwProducts.getItems().setAll();

        //Listener for category list
        ChangeListener<ProductCategory> listener = (ov, o, n) -> this.productCategoryItemSelected();
        lvwCategories.getSelectionModel().selectedItemProperty().addListener(listener);

        //create category button
        Button btnCreateProductCategory = new Button("Create category");
        this.add(btnCreateProductCategory, 0, 0);
        btnCreateProductCategory.setOnAction(event -> this.createProductCategoryAction());

        //create product button
        Button btnCreateProduct = new Button("Create product");
        this.add(btnCreateProduct, 1, 0);
        btnCreateProduct.setOnAction(event -> this.createProductAction());

        //initial methods
        this.initProduct();
        lvwCategories.getItems().setAll(productController.getProductCategories());
    }

    // -------------------------------------------------------------------------

    /**
     * Creates initial products and categories
     */
    private void initProduct(){
        productController.initContent();
    }

    // -------------------------------------------------------------------------

    /**
     * Called when listener detects changes in selection from category list. Updates product list to show the products contained in the category.
     */
    private void productCategoryItemSelected() {
        ProductCategory selected = lvwCategories.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lvwProducts.getItems().setAll(selected.getProducts());
        } else {
            lvwCategories.getItems().setAll(productController.getProductCategories());
        }
    }



    // Button actions

    /**
     * Opens a new window to create a new category of products
     */
    private void createProductCategoryAction(){
        //TODO
    }

    /**
     * Opens a new window to create a new product, for the currently selected category
     */
    private void createProductAction(){
        //TODO
    }

    public void updateControls(){
        try {
            lvwCategories.getItems().setAll(productController.getProductCategories());
        }
        catch (NullPointerException npe){
            npe.getMessage();
        }
    }

}
