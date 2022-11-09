package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

    private ListView<Product> lvwProducts;
    private ListView<ProductCategory> lvwCategories;
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
    private ListView<Situation> lvwSituations;
    private Button btnRemoveSituation;

    /**
     * Creates a new ProductOverviewTab, for showing an overview of all ProductCategories and Products. Also allows creation of those objects.
     */
    public ProductOverviewTab() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.TOP_CENTER);

        productController = new ProductOverviewController(Storage.getStorage());

        //-----Initial methods-----
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

        //-----Category Controls-----
        //Label
        Label lblCategory = new Label("Produktkategorier");
        lblCategory.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize() + 5));
        lblCategory.setMaxWidth(Double.MAX_VALUE);
        lblCategory.setAlignment(Pos.BASELINE_CENTER);

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

        //Hbox for buttons
        HBox hbxCategoryButtons = new HBox(btnCreateProductCategory, btnEditCategory);
        hbxCategoryButtons.setSpacing(10);

        //List View of categories
        lvwCategories = new ListView<>();
        lvwCategories.getItems().setAll();
        lvwCategories.setMinHeight(200);
        lvwCategories.setPrefHeight(250);

        //Listener for category list
        ChangeListener<ProductCategory> categoryListener = (ov, o, n) -> this.productCategoryItemSelected();
        lvwCategories.getSelectionModel().selectedItemProperty().addListener(categoryListener);

        //Vbox for all Category controls
        VBox vbxCategory = new VBox(lblCategory, hbxCategoryButtons, lvwCategories);
        vbxCategory.setSpacing(5);
        this.add(vbxCategory, 0, 0, 1, 2);


        //-----Products Controls-----
        //Label
        Label lblProducts = new Label("Produkter");
        lblProducts.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize() + 5));
        lblProducts.setMaxWidth(Double.MAX_VALUE);
        lblProducts.setAlignment(Pos.BASELINE_CENTER);

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

        //List View of products
        lvwProducts = new ListView<>();
        lvwProducts.setMinHeight(200);
        lvwProducts.setPrefHeight(250);

        //Listener for product list
        ChangeListener<Product> productListener = (ov, o, n) -> this.productItemSelected();
        lvwProducts.getSelectionModel().selectedItemProperty().addListener(productListener);

        //Vbox to hold Product Controls
        VBox vbxProduct = new VBox(lblProducts, hbxProductButtons, lvwProducts);
        vbxProduct.setSpacing(5);
        this.add(vbxProduct, 1, 0, 1, 2);

        //-----Prices Controls
        //Label
        Label lblPrices = new Label("Priser");
        lblPrices.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize() + 5));
        lblPrices.setMaxWidth(Double.MAX_VALUE);
        lblPrices.setAlignment(Pos.BASELINE_CENTER);

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

        //List view of Prices
        lvwPrices = new ListView<>();
        lvwPrices.setPrefHeight(150);

        //Listener for Price list
        ChangeListener<Price> priceListener = (ov, o, n) -> this.priceItemSelected();
        lvwPrices.getSelectionModel().selectedItemProperty().addListener(priceListener);

        //Vbox to hold Price controls
        VBox vbxPrices = new VBox(lblPrices, hbxPiceButtons, lvwPrices);
        vbxPrices.setSpacing(5);


        //-----Deposit price controls-----
        //Label
        Label lblDeposit = new Label("Pant");
        lblDeposit.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize() + 5));
        lblDeposit.setMaxWidth(Double.MAX_VALUE);
        lblDeposit.setAlignment(Pos.BASELINE_CENTER);

        //Create Deposit Price button
        btnCreateDepositPrice = new Button("Ny Pant");
        btnCreateDepositPrice.setMaxWidth(Double.MAX_VALUE);
        btnCreateDepositPrice.setOnAction(event -> this.createDepositPriceAction());

        //List view of Deposit prices
        txfDepositPrices = new TextField();
        txfDepositPrices.setEditable(false);

        //Vbox to hold deposit controls
        VBox vbxDeposit = new VBox(lblDeposit, btnCreateDepositPrice, txfDepositPrices);
        vbxDeposit.setSpacing(5);


        //VBox to group prices and deposit control
        VBox vbxPriceAndDeposit = new VBox(vbxPrices, vbxDeposit);
        vbxPriceAndDeposit.setSpacing(15);
        this.add(vbxPriceAndDeposit, 2, 0, 1, 2);

        //-----Situation controls-----
        //Label
        Label lblSituation = new Label("Salgssituationer");
        lblSituation.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize() + 5));
        lblSituation.setMaxWidth(Double.MAX_VALUE);
        lblSituation.setAlignment(Pos.BASELINE_CENTER);

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

        //List view of situations
        lvwSituations = new ListView<>();
        lvwSituations.setPrefHeight(75);

        //Listener for Situation list
        ChangeListener<Situation> situationListener = (ov, o, n) -> this.situationSelected();
        lvwSituations.getSelectionModel().selectedItemProperty().addListener(situationListener);

        //Vbox to hold situation controls
        VBox vbxSituationList = new VBox(lblSituation, hbxSituationButtons, lvwSituations);
        vbxSituationList.setSpacing(5);
        this.add(vbxSituationList, 1, 2);

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

            if (lvwSituations.getSelectionModel().getSelectedItem() == null) {
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
        updateDepositField();
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
        updateDepositField();
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

    /**
     * Updates the list of prices for a product
     */
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

    /**
     * Updates the field with the deposit price
     */
    public void updateDepositField() {
        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();

        if (selectedProduct != null && selectedProduct.getDepositPrice() != null) {
            txfDepositPrices.setText(selectedProduct.getDepositPrice().getValue() + " " + selectedProduct.getDepositPrice().getUnit());
        } else {
            txfDepositPrices.setText("0.0 DKK");
        }
    }

    /**
     * Called when the create price button is pressed
     */
    public void createPriceAction() {
        ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();
        Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();

        CreatePriceWindow newPriceWindow = new CreatePriceWindow("Ny Pris", selectedProduct, new Stage());
        newPriceWindow.showAndWait();

        updatePriceList();

        lvwCategories.getSelectionModel().select(selectedCategory);
        lvwProducts.getSelectionModel().select(selectedProduct);
    }

    /**
     * Called when the create deposit button is pressed.
     */
    public void createDepositPriceAction() {
        if (lvwProducts.getSelectionModel().getSelectedItem() == null) {
            Alert noProductAlert = new Alert(Alert.AlertType.ERROR);
            noProductAlert.setTitle("Der er ikke valgt et produkt");
            noProductAlert.setContentText("Du skal vælge et produkt før du kan oprette pant");
            noProductAlert.showAndWait();

        } else {
            ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();
            Product selectedProduct = lvwProducts.getSelectionModel().getSelectedItem();

            CreateDepositPriceWindow newDepositPriceWindow = new CreateDepositPriceWindow("Ny Pantpris", selectedProduct, new Stage(), selectedCategory);
            newDepositPriceWindow.showAndWait();

            lvwCategories.getSelectionModel().select(selectedCategory);
            lvwProducts.getSelectionModel().select(selectedProduct);
        }

        updateDepositField();
    }

    /**
     * Called when the edit categories button is pressed
     */
    public void editProductCategoryAction() {
        ProductCategory selectedCategory = lvwCategories.getSelectionModel().getSelectedItem();
        EditProductCategoryWindow editWindow = new EditProductCategoryWindow("Rediger Produktkategori", new Stage(), selectedCategory);
        editWindow.showAndWait();

        updateCategoryList();

        lvwCategories.getSelectionModel().select(selectedCategory);
    }

    /**
     * Called when the edit product button is pressed.
     */
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

        productController.removePriceFromProduct(selectedPrice, selectedProduct);

        updatePriceList();
    }

    /**
     * Creates a new situation
     */
    public void createSituationAction() {
        CreateSituationWindow createSituationWindow = new CreateSituationWindow("Ny salgssituation", new Stage());
        createSituationWindow.showAndWait();

        updateSituations();
    }

    /**
     * Removes the currently selected situation, and all prices for all products connected to it
     */
    public void removeSituationAction() {
        Situation selectedSituation = lvwSituations.getSelectionModel().getSelectedItem();
        if (selectedSituation != null) {
            productController.removeSituation(selectedSituation);
        }

        updateControls();
    }

    /**
     * Called when a new situation is selected
     */
    public void situationSelected() {
        if (lvwSituations.getSelectionModel().getSelectedItem() == null) {
            btnRemoveSituation.setDisable(true);
        } else {
            btnRemoveSituation.setDisable(false);
        }
    }

    /**
     * Updates the Situationlist
     */
    public void updateSituations() {
        lvwSituations.getItems().setAll(productController.getSituations());
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

            if (empty || price == null) {
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

