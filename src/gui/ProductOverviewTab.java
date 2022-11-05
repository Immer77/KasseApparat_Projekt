package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.ProductOverviewController;
import model.modelklasser.Price;
import model.modelklasser.Product;
import model.modelklasser.ProductCategory;
import storage.Storage;

public class ProductOverviewTab extends GridPane {

    private final ListView<Product> lvwProducts = new ListView<>();
    private final ListView<ProductCategory> lvwCategories = new ListView<>();
    private ProductOverviewControllerInterface productController;
    private Button btnCreateProduct;
    private ListView<HBox> lvwPrices = new ListView<>();

    private Button btnCreatePrice;

    /**
     * Creates a new ProductOverviewTab, for showing an overview of all ProductCategories and Products. Also allows creation of those objects.
     */
    public ProductOverviewTab() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        productController = new ProductOverviewController(Storage.getStorage());

        //List View of categories
        this.add(lvwCategories, 0, 1);
        lvwCategories.setPrefWidth(200);
        lvwCategories.setPrefHeight(300);
        lvwCategories.getItems().setAll();

        //List View of products
        this.add(lvwProducts, 1, 1);
        lvwProducts.setPrefWidth(200);
        lvwProducts.setPrefHeight(300);

        //List view of Prices
        this.add(lvwPrices, 2, 1);
        lvwPrices.setPrefWidth(200);
        lvwPrices.setPrefHeight(300);

        //Listener for category list
        ChangeListener<ProductCategory> categoryListener = (ov, o, n) -> this.productCategoryItemSelected();
        lvwCategories.getSelectionModel().selectedItemProperty().addListener(categoryListener);

        //Listener for product list
        ChangeListener<Product> productListener = (ov, o, n) -> this.productItemSelected();
        lvwProducts.getSelectionModel().selectedItemProperty().addListener(productListener);

        //create category button
        Button btnCreateProductCategory = new Button("Ny Produktkategori");
        btnCreateProductCategory.setMaxWidth(Double.MAX_VALUE);
        this.add(btnCreateProductCategory, 0, 0);
        btnCreateProductCategory.setOnAction(event -> this.createProductCategoryAction());

        //create product button
        btnCreateProduct = new Button("Nyt Produkt");
        btnCreateProduct.setMaxWidth(Double.MAX_VALUE);
        this.add(btnCreateProduct, 1, 0);
        btnCreateProduct.setOnAction(event -> this.createProductAction());

        //CreatePrice button
        btnCreatePrice = new Button("Ny Pris");
        btnCreatePrice.setMaxWidth(Double.MAX_VALUE);
        this.add(btnCreatePrice, 2,0);
        btnCreatePrice.setOnAction(event -> this.createPriceAction());

        //initial methods
        this.initContent();
    }

    // -------------------------------------------------------------------------

    /**
     * Creates initial products and categories
     */
    private void initContent() {
        //TODO - Remove this from final version. It creates initial objects to storage
        if (productController instanceof ProductOverviewController) {
            ProductOverviewController controller = (ProductOverviewController) productController;
            controller.initContent();
        }

        btnCreatePrice.setDisable(true);
        updateControls();

    }

    // -------------------------------------------------------------------------

    /**
     * Called when listener detects changes in selection from category list. Updates product list to show the products contained in the category.
     */
    private void productCategoryItemSelected() {
        updateProductList();
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
            btnCreateProduct.setDisable(false);
        } else {
            lvwProducts.getItems().clear();
            btnCreateProduct.setDisable(true);
        }
    }


    /**
     * Helper method for updating the ProductCategory listview
     */
    private void updateCategoryList() {
        lvwCategories.getItems().setAll(productController.getProductCategories());
    }

    public void productItemSelected() {
        updatePriceList();
    }

    public void updatePriceList() {

        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();

        lvwPrices.getItems().clear();

        if (selectedProduct != null) {
            for (Price price : selectedProduct.getPrices()) {
                //Create label for Situation
                Label lblSituation = new Label(price.getSituation().getName());
                lblSituation.setPrefWidth(70);


                Label lblseperator = new Label(" - ");
                lblseperator.setAlignment(Pos.BASELINE_CENTER);
                lblseperator.setPrefWidth(30);

                //Create label for value
                Label lblValue = new Label("" + price.getValue());
                lblValue.setAlignment(Pos.BASELINE_RIGHT);
                HBox.setHgrow(lblValue, Priority.ALWAYS);

                //Create label for Unit
                Label lblUnit = new Label("" + price.getUnit());
                lblUnit.setAlignment(Pos.BASELINE_RIGHT);

                //Hbox to hold the labels
                HBox hbxPrice = new HBox(lblSituation, lblseperator, lblValue, lblUnit);
                hbxPrice.setSpacing(5);
                hbxPrice.setMaxWidth(Double.MAX_VALUE);
                lvwPrices.getItems().add(hbxPrice);

                btnCreatePrice.setDisable(false);
            }
        } else {
            btnCreatePrice.setDisable(true);
        }
    }

    public void createPriceAction () {
        //TODO - Create a new price when button is pressed
    }


}
