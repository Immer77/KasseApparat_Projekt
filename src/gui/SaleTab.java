package gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.controller.ProductOverviewController;
import model.modelklasser.Product;
import model.modelklasser.ProductCategory;
import storage.Storage;

public class SaleTab extends GridPane {
    //Fields ------------------------------------------------------------
    private ProductOverviewControllerInterface productController = ProductOverviewController.getProductOverviewController(Storage.getStorage());
    Accordion accProductOverview;

    //Constructors ------------------------------------------------------
    public SaleTab () {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        updateProductOverview();

    }

    //Methods - Get, Set & Add -------------------------------------------


    //Methods - Other ----------------------------------------------------
    public void updateControls () {

    }

    public void productCategorySelectionChanged () {

    }



    private void addProductToOrder(Product product) {

    }

    private void updateProductOverview() {
        //Accordion control for showing of categories and products
        //Create accordion view, and add the titled pane
        accProductOverview = new Accordion();
        accProductOverview.setMaxWidth(Double.MAX_VALUE);
        accProductOverview.setPrefWidth(250);
        accProductOverview.setPadding(Insets.EMPTY);

        //For each category...
        for (ProductCategory proCat : productController.getProductCategories()) {
            //Create a vbox
            VBox buttonVBox = new VBox();
            buttonVBox.setPadding(new Insets(5));
            buttonVBox.setFillWidth(true);
            buttonVBox.maxWidth(Double.MAX_VALUE);
            buttonVBox.setPrefWidth(accProductOverview.getPrefWidth());

            //for each product in the category, create a button and add to the vbox
            for (Product prod : proCat.getProducts()) {
                Button productButton = new Button(prod.toString());
                productButton.setOnAction(event -> addProductToOrder(prod));
                productButton.setWrapText(true);
                productButton.setMaxWidth(Double.MAX_VALUE);

                buttonVBox.getChildren().add(productButton);

            }

            //Create a titledPane, add the vbox
            TitledPane titledPane = new TitledPane(proCat.getTitle(), buttonVBox);

            accProductOverview.getPanes().add(titledPane);
        }

        //add accordionView to tab
        this.add(accProductOverview, 0,0);
    }
}
