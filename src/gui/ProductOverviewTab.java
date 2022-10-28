package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private ProductOverviewControllerInterface productController = ProductOverviewController.getProductOverviewController(Storage.getStorage());
    private Button btnCreateProduct;

    /**
     * Creates a new ProductOverviewTab, for showing an overview of all ProductCategories and Products. Also allows creation of those objects.
     */
    public ProductOverviewTab() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        //List View of categories
        this.add(lvwCategories, 0, 1);
        lvwCategories.setPrefWidth(200);
        lvwCategories.setPrefHeight(300);
        lvwCategories.getItems().setAll();

        //List View of products
        this.add(lvwProducts, 1, 1);
        lvwProducts.setPrefWidth(200);
        lvwProducts.setPrefHeight(300);
        lvwProducts.getItems().setAll();

        //Listener for category list
        ChangeListener<ProductCategory> listener = (ov, o, n) -> this.productCategoryItemSelected();
        lvwCategories.getSelectionModel().selectedItemProperty().addListener(listener);

        //create category button
        Button btnCreateProductCategory = new Button("Ny Produktkategori");
        this.add(btnCreateProductCategory, 0, 0);
        btnCreateProductCategory.setOnAction(event -> this.createProductCategoryAction());

        //create product button
        btnCreateProduct = new Button("Nyt Produkt");
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
        if (productController instanceof ProductOverviewController) {
            ProductOverviewController controller = (ProductOverviewController) productController;
            controller.initContent();
        }

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

        Stage stage = new Stage(StageStyle.UTILITY);
        CreateProductCategoryWindow categoryWindow = new CreateProductCategoryWindow("Ny produktkategori", stage);
        categoryWindow.showAndWait();

        updateControls();
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
            lvwCategories.getSelectionModel().select(category);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fejl!");
            alert.setHeaderText("Ingen produktkategori er valgt. Alle produkter skal tilhøre en kategori!");
            alert.showAndWait();
        }
    }

    /**
     * Updates lists and buttons on this tab
     */
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

    /**
     * Helper method for updating the Product listview
     */
    private void updateProductList() {
        ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            lvwProducts.getItems().setAll(selectedCategory.getProducts());
        } else {
            lvwProducts.getItems().clear();
        }
    }


    /**
     * Helper method for updating the ProductCategory listview
     */
    private void updateCategoryList() {
        lvwCategories.getItems().setAll(productController.getProductCategories());
    }

    /**
     * Helper method. Greys out btnCreateProduct button, when no ProductCategory is selected.
     */
    private void productButtonGreyout() {
        ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();

        if (selectedCategory == null) {
            btnCreateProduct.setDisable(true);

        } else {
            btnCreateProduct.setDisable(false);

        }
    }

}
