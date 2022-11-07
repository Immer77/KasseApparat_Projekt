package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

    private final ListView<Product> lvwProducts;
    private final ListView<ProductCategory> lvwCategories;
    private ProductOverviewControllerInterface productController;
    private Button btnCreateProduct;
    private ListView<HBox> lvwPrices;
    private TextField txfDepositPrices;

    private Button btnCreatePrice;
    private Button btnCreateDepositPrice;
    private Button btnCreateProductCategory;
    private Button btnEditCategory;
    private Button btnEditProduct;
    private Button btnRemovePrice;

    /**
     * Creates a new ProductOverviewTab, for showing an overview of all ProductCategories and Products. Also allows creation of those objects.
     */
    public ProductOverviewTab() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        productController = new ProductOverviewController(Storage.getStorage());

        //List View of categories
        lvwCategories = new ListView<>();
        this.add(lvwCategories, 0, 1, 1, 3);
        lvwCategories.getItems().setAll();

        //List View of products
        lvwProducts = new ListView<>();
        this.add(lvwProducts, 1, 1, 1, 3);

        //List view of Prices
        lvwPrices = new ListView<>();
        this.add(lvwPrices, 2, 1);

        //List view of Deposit prices
        txfDepositPrices = new TextField();
        txfDepositPrices.setEditable(false);
        this.add(txfDepositPrices, 2, 3);

        //Listener for category list
        ChangeListener<ProductCategory> categoryListener = (ov, o, n) -> this.productCategoryItemSelected();
        lvwCategories.getSelectionModel().selectedItemProperty().addListener(categoryListener);

        //Listener for product list
        ChangeListener<Product> productListener = (ov, o, n) -> this.productItemSelected();
        lvwProducts.getSelectionModel().selectedItemProperty().addListener(productListener);


        //create product button
        btnCreateProduct = new Button("Nyt Produkt");
        btnCreateProduct.setMaxWidth(Double.MAX_VALUE);
        btnCreateProduct.setOnAction(event -> this.createProductAction());
        HBox.setHgrow(btnCreateProduct, Priority.ALWAYS);

        //Edit Product Button
        btnEditProduct = new Button("Rediger Produkt");
        btnEditProduct.setMaxWidth(Double.MAX_VALUE);
        btnEditProduct.setOnAction(event -> this.editProductAction());
        HBox.setHgrow(btnEditProduct, Priority.ALWAYS);

        HBox hbxProductButtons = new HBox(btnCreateProduct, btnEditProduct);
        hbxProductButtons.setSpacing(10);
        this.add(hbxProductButtons, 1,0);

        //CreatePrice button
        btnCreatePrice = new Button("Ny Pris");
        btnCreatePrice.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnCreatePrice, Priority.ALWAYS);
        btnCreatePrice.setOnAction(event -> this.createPriceAction());

        //Remove Price Button
        btnRemovePrice = new Button("Slet Pris");
        btnRemovePrice.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnRemovePrice, Priority.ALWAYS);
        btnRemovePrice.setOnAction(event->this.removePriceAction());

        HBox hbxPiceButtons = new HBox(btnCreatePrice, btnRemovePrice);
        hbxPiceButtons.setSpacing(10);
        this.add(hbxPiceButtons, 2, 0);

        //Create Deposit Price button
        btnCreateDepositPrice = new Button("Ny Pant");
        btnCreateDepositPrice.setMaxWidth(Double.MAX_VALUE);
        btnCreateDepositPrice.setOnAction(event -> this.createDepositPriceAction());
        this.add(btnCreateDepositPrice, 2, 2);

        //create category button
        btnCreateProductCategory = new Button("Ny Kategori");
        btnCreateProductCategory.setMaxWidth(Double.MAX_VALUE);
        btnCreateProductCategory.setOnAction(event -> this.createProductCategoryAction());
        HBox.setHgrow(btnCreateProductCategory, Priority.ALWAYS);

        //Edit Category button
        btnEditCategory = new Button("Rediger Kategori");
        btnEditCategory.setMaxWidth(Double.MAX_VALUE);
        btnEditCategory.setOnAction(event -> this.editProductCategoryAction());
        HBox.setHgrow(btnEditCategory, Priority.ALWAYS);

        HBox hbxCategoryButtons = new HBox(btnCreateProductCategory, btnEditCategory);
        hbxCategoryButtons.setSpacing(10);
        this.add(hbxCategoryButtons, 0,0);


        //initial methods
        this.initContent();
        updateControls();
        productCategoryItemSelected();
        productItemSelected();
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
        if (lvwCategories.getSelectionModel().getSelectedItem() == null) {
            btnEditCategory.setDisable(true);
        } else {
            btnEditCategory.setDisable(false);
        }

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
            ProductCategory selCategory = lvwCategories.getSelectionModel().getSelectedItem();
            updateCategoryList();
            if (selCategory != null) {
                lvwCategories.getSelectionModel().select(selCategory);
            }

            Product selProduct = lvwProducts.getSelectionModel().getSelectedItem();
            updateProductList();
            if (selProduct != null) {
                lvwProducts.getSelectionModel().select(selProduct);
            }

            updatePriceList();
            updateDepositList();

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
        //Fills the category list
        lvwCategories.getItems().setAll(productController.getProductCategories());
    }

    public void productItemSelected() {

        if (lvwProducts.getSelectionModel().getSelectedItem() == null) {
            btnEditProduct.setDisable(true);
        } else {
            btnEditProduct.setDisable(false);
        }

        updateControls();
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

    public void updateDepositList() {
        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();

        txfDepositPrices.clear();

        if (selectedProduct != null) {

            if (selectedProduct.getDepositPrice() != null) {
                txfDepositPrices.setText(selectedProduct.getDepositPrice().getValue()+" "+selectedProduct.getDepositPrice().getUnit());
            }

            btnCreateDepositPrice.setDisable(false);
        } else {

            btnCreateDepositPrice.setDisable(true);
        }
    }

    public void createPriceAction() {
        ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();
        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();

        CreatePriceWindow newPriceWindow = new CreatePriceWindow("Ny Pris", selectedProduct, new Stage());
        newPriceWindow.showAndWait();

        updateControls();

        lvwCategories.getSelectionModel().select(selectedCategory);
        lvwProducts.getSelectionModel().select(selectedProduct);
    }

    public void createDepositPriceAction() {
        ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();
        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();

        CreateDepositPriceWindow newDepositPriceWindow = new CreateDepositPriceWindow("Ny Pantpris", selectedProduct, new Stage());
        newDepositPriceWindow.showAndWait();

        updateControls();

        lvwCategories.getSelectionModel().select(selectedCategory);
        lvwProducts.getSelectionModel().select(selectedProduct);
    }

    public void editProductCategoryAction(){

    }

    public void editProductAction() {

    }

    public void removePriceAction() {

    }


}

