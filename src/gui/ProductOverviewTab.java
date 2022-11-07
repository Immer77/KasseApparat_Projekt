package gui;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.controller.ProductOverviewController;
import model.modelklasser.Price;
import model.modelklasser.Product;
import model.modelklasser.ProductCategory;
import model.modelklasser.Situation;
import storage.Storage;

public class ProductOverviewTab extends GridPane {

    private final ListView<Product> lvwProducts;
    private final ListView<ProductCategory> lvwCategories;
    private ProductOverviewControllerInterface productController;
    private Button btnCreateProduct;
    private ListView<Price> lvwPrices;
    private TextField txfDepositPrices;

    private Button btnCreatePrice;
    private Button btnCreateDepositPrice;
    private Button btnCreateProductCategory;
    private Button btnEditCategory;
    private Button btnEditProduct;
    private Button btnRemovePrice;
    private ListView<Situation> lvwSituaions;
    private Button btnRemoveSituation;

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

        //Listener for Price list
        ChangeListener<Price> priceListener = (ov, o, n) -> this.priceItemSelected();
        lvwPrices.getSelectionModel().selectedItemProperty().addListener(priceListener);


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
        this.add(hbxProductButtons, 1, 0);

        //CreatePrice button
        btnCreatePrice = new Button("Ny Pris");
        btnCreatePrice.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnCreatePrice, Priority.ALWAYS);
        btnCreatePrice.setOnAction(event -> this.createPriceAction());

        //Remove Price Button
        btnRemovePrice = new Button("Slet Pris");
        btnRemovePrice.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnRemovePrice, Priority.ALWAYS);
        btnRemovePrice.setOnAction(event -> this.removePriceAction());

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
        this.add(hbxCategoryButtons, 0, 0);

        //List view of situations
        lvwSituaions = new ListView<>();
        this.add(lvwSituaions, 0,6);

        //Button for creating new situations
        Button btnCreateSituation = new Button("Ny salgssituation");
        btnCreateSituation.setMaxWidth(Double.MAX_VALUE);
        btnCreateSituation.setOnAction(event -> this.createSituationAction());
        HBox.setHgrow(btnCreateSituation, Priority.ALWAYS);

        //Button for removing a situation
        btnRemoveSituation = new Button("Slet salgssituation");
        btnRemoveSituation.setMaxWidth(Double.MAX_VALUE);
        btnRemoveSituation.setOnAction(event -> this.removeSituationAction());
        HBox.setHgrow(btnRemoveSituation, Priority.ALWAYS);

        HBox hbxSituationButtons = new HBox(btnCreateSituation, btnRemoveSituation);
        hbxSituationButtons.setSpacing(10);
        this.add(hbxSituationButtons, 0, 5);

        //Listener for Situation list
        ChangeListener<Situation> situationListener = (ov, o, n) -> this.situationSelected();
        lvwSituaions.getSelectionModel().selectedItemProperty().addListener(situationListener);

        //initial methods
        this.initContent();
        updateControls();
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

        updateControls();

    }

    // -------------------------------------------------------------------------

    /**
     * Called when listener detects changes in selection from category list. Updates product list to show the products contained in the category.
     */
    private void productCategoryItemSelected() {
        if (lvwCategories.getSelectionModel().getSelectedItem() == null) {
            btnEditCategory.setDisable(true);
            btnCreateProduct.setDisable(true);
        } else {
            btnEditCategory.setDisable(false);
            btnCreateProduct.setDisable(false);
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

        updateCategoryList();
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

            updateProductList();
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

            if (lvwCategories.getSelectionModel().getSelectedItem() == null) {
                btnCreateProduct.setDisable(true);
                btnEditCategory.setDisable(true);
            }
            if (lvwProducts.getSelectionModel().getSelectedItem() == null) {
                btnEditProduct.setDisable(true);
                btnCreatePrice.setDisable(true);
                btnCreateDepositPrice.setDisable(true);
            }
            if (lvwPrices.getSelectionModel().getSelectedItem() == null) {
                btnRemovePrice.setDisable(true);
            }

            if (lvwSituaions.getSelectionModel().getSelectedItem() == null) {
                btnRemoveSituation.setDisable(true);
            }
            updateCategoryList();
            updateSituations();
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
        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            lvwProducts.getItems().setAll(selectedCategory.getProducts());
        } else {
            lvwProducts.getItems().clear();
        }
        if (selectedProduct != null) {
            lvwProducts.getSelectionModel().select(selectedProduct);
        }

        updatePriceList();
        updateDepositList();
    }


    /**
     * Helper method for updating the ProductCategory listview
     */
    private void updateCategoryList() {
        ProductCategory selCategory = lvwCategories.getSelectionModel().getSelectedItem();
        lvwCategories.getItems().setAll(productController.getProductCategories());
        if (selCategory != null) {
            lvwCategories.getSelectionModel().select(selCategory);
        }

        updateProductList();
    }

    /**
     * Called when selection changes in Product listview
     */
    public void productItemSelected() {

        if (lvwProducts.getSelectionModel().getSelectedItem() == null) {
            btnEditProduct.setDisable(true);
            btnCreatePrice.setDisable(true);
            btnCreateDepositPrice.setDisable(true);
        } else {
            btnEditProduct.setDisable(false);
            btnCreatePrice.setDisable(false);
            btnCreateDepositPrice.setDisable(false);
        }

        updatePriceList();
        updateDepositList();
    }

    /**
     * Called when selection changes in Price listview
     */
    public void priceItemSelected() {
        if (lvwPrices.getSelectionModel().getSelectedItem() == null) {
            btnRemovePrice.setDisable(true);
        } else {
            btnRemovePrice.setDisable(false);
        }
    }

    public void updatePriceList() {
        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();
        Price selectedPrice = lvwPrices.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            lvwPrices.getItems().setAll(selectedProduct.getPrices());
            lvwPrices.setCellFactory(new Callback<ListView<Price>, ListCell<Price>>() {
                @Override
                public ListCell<Price> call(ListView<Price> priceListView) {
                    return new PriceFormatCell();
                }
            });

            if (selectedPrice != null) {
                lvwPrices.getSelectionModel().select(selectedPrice);
            }

        } else {
            lvwPrices.getItems().clear();
        }
    }

    public void updateDepositList() {
        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {

            if (selectedProduct.getDepositPrice() != null) {
                txfDepositPrices.setText(selectedProduct.getDepositPrice().getValue() + " " + selectedProduct.getDepositPrice().getUnit());
            }


        } else {
            txfDepositPrices.clear();
        }
    }

    public void createPriceAction() {
        ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();
        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();

        CreatePriceWindow newPriceWindow = new CreatePriceWindow("Ny Pris", selectedProduct, new Stage());
        newPriceWindow.showAndWait();

        updatePriceList();

        lvwCategories.getSelectionModel().select(selectedCategory);
        lvwProducts.getSelectionModel().select(selectedProduct);
    }

    public void createDepositPriceAction() {
        ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();
        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();

        CreateDepositPriceWindow newDepositPriceWindow = new CreateDepositPriceWindow("Ny Pantpris", selectedProduct, new Stage());
        newDepositPriceWindow.showAndWait();

        updateDepositList();

        lvwCategories.getSelectionModel().select(selectedCategory);
        lvwProducts.getSelectionModel().select(selectedProduct);
    }

    public void editProductCategoryAction() {
        ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();
        EditProductCategoryWindow editWindow = new EditProductCategoryWindow("Rediger Produktkategori", new Stage(), selectedCategory);
        editWindow.showAndWait();

        updateCategoryList();

        lvwCategories.getSelectionModel().select(selectedCategory);
    }

    public void editProductAction() {
        ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();
        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();

        EditProductWindow editWindow = new EditProductWindow("Rediger Produkt", new Stage(), selectedProduct);
        editWindow.showAndWait();

        updateProductList();

        lvwProducts.getSelectionModel().select(selectedProduct);

    }

    /**
     * Removes the selected price from the selected products price list
     */
    public void removePriceAction() {
        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();
        Price selectedPrice = lvwPrices.getSelectionModel().getSelectedItem();

        productController.removePriceFromProduct(selectedPrice,selectedProduct);

        updatePriceList();
    }

    /**
     * Creates a new situation
     */
    public void createSituationAction () {
        CreateSituationWindow createSituationWindow = new CreateSituationWindow("Ny salgssituation", new Stage());
        createSituationWindow.showAndWait();

        updateSituations();
    }

    /**
     * Removes the currently selected situation, and all prices for all products connected to it
     */
    public void removeSituationAction () {
        Situation selectedSituation = lvwSituaions.getSelectionModel().getSelectedItem();
        if (selectedSituation != null) {
            productController.removeSituation(selectedSituation);
        }

        updateControls();
    }

    /**
     * Called when a new situation is selected
     */
    public void situationSelected () {
        if (lvwSituaions.getSelectionModel().getSelectedItem() == null) {
            btnRemoveSituation.setDisable(true);
        } else {
            btnRemoveSituation.setDisable(false);
        }
    }

    public void updateSituations () {
        lvwSituaions.getItems().setAll(productController.getSituations());
    }

    /**
     * Inner class to format the Price display in the listview of prices.
     */
    private class PriceFormatCell extends ListCell<Price> {
        private Label lblSituation;
        private Label lblValue;
        private Label lblUnit;
        private HBox hbxPrice;

        public PriceFormatCell() {
            //Create label for Situation
            lblSituation = new Label();
            lblSituation.setPrefWidth(70);

            Label lblseperator = new Label(" - ");
            lblseperator.setAlignment(Pos.BASELINE_CENTER);
            lblseperator.setPrefWidth(30);

            //Create label for value
            lblValue = new Label();
            lblValue.setAlignment(Pos.BASELINE_RIGHT);
            HBox.setHgrow(lblValue, Priority.ALWAYS);

            //Create label for Unit
            lblUnit = new Label();
            lblUnit.setAlignment(Pos.BASELINE_RIGHT);

            //Hbox to hold the labels
            hbxPrice = new HBox(lblSituation, lblseperator, lblValue, lblUnit);
            hbxPrice.setSpacing(5);
            hbxPrice.setMaxWidth(Double.MAX_VALUE);
        }

        @Override
        protected void updateItem(Price price, boolean empty) {
            super.updateItem(price, empty);

            if (empty || price ==null) {
             setGraphic(null);
            } else {
                lblSituation.setText(price.getSituation().getName());
                lblValue.setText("" + price.getValue());
                lblUnit.setText("" + price.getUnit());

                setGraphic(hbxPrice);
            }
        }
    }
}

