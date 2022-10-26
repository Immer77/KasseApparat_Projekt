package gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.ProductOverviewController;
import model.modelklasser.Product;
import model.modelklasser.ProductCategory;
import storage.Storage;

public class ProductOverviewTab extends GridPane {

    private final ListView<Product> lvwProducts = new ListView<>();
    private final ListView<ProductCategory> lvwCategories = new ListView<>();
    private ProductOverviewController productController = ProductOverviewController.getProductOverviewController(Storage.getUnique_Storage());
    private Button btnCreateProduct;

    public ProductOverviewTab() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

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
        btnCreateProduct = new Button("Create product");
        this.add(btnCreateProduct, 1, 0);
        btnCreateProduct.setOnAction(event -> this.createProductAction());

        //initial methods
        this.initProduct();
        updateControls();
    }

    // -------------------------------------------------------------------------

    /**
     * Creates initial products and categories
     */
    private void initProduct() {
        productController.initContent();
    }

    // -------------------------------------------------------------------------

    /**
     * Called when listener detects changes in selection from category list. Updates product list to show the products contained in the category.
     */
    private void productCategoryItemSelected() {
        updateProductList();
        productButtonGreyout();
    }


    // Button actions

    /**
     * Opens a new window to create a new category of products
     */
    private void createProductCategoryAction() {
        //TODO
    }

    /**
     * Opens a new window to create a new product, for the currently selected category
     */
    private void createProductAction() {

        if (lvwCategories.getSelectionModel().getSelectedItem() != null) {
            ProductCategory category = lvwCategories.getSelectionModel().getSelectedItem();
            Stage stage = new Stage(StageStyle.UTILITY);
            CreateProductWindow productWindow = new CreateProductWindow("Nyt Produkt", category, stage);
            productWindow.showAndWait();

            updateControls();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fejl!");
            alert.setHeaderText("Ingen produktkategori er valgt. Alle produkter skal tilhøre en kategori!");
            alert.showAndWait();
        }
    }

    public void updateControls() {
        try {

            updateCategoryList();
            updateProductList();

            productButtonGreyout();

        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Null Pointer Exception");
            alert.setHeaderText(npe.getMessage());
            alert.showAndWait();
        }
    }

    private void updateProductList() {
        ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            lvwProducts.getItems().setAll(selectedCategory.getProducts());
        } else {
            lvwProducts.getItems().clear();
        }
    }


    private void updateCategoryList() {
        lvwCategories.getItems().setAll(productController.getProductCategories());
    }

    private void productButtonGreyout() {
        ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();

        if (selectedCategory == null) {
            btnCreateProduct.setDisable(true);

        } else {
            btnCreateProduct.setDisable(false);

        }
    }

}
