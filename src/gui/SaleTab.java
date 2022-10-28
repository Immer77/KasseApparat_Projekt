package gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.controller.ProductOverviewController;
import model.modelklasser.Product;
import model.modelklasser.ProductCategory;
import storage.Storage;

public class SaleTab extends GridPane {
    //Fields ------------------------------------------------------------
    private ProductOverviewControllerInterface saleController = ProductOverviewController.getProductOverviewController(Storage.getStorage());
    private Accordion accProductOverview;
    private ListView<HBox> orderOverview;

    //Constructors ------------------------------------------------------
    public SaleTab () {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        updateProductOverview();

        orderOverview = new ListView<>();
        orderOverview.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
        this.add(orderOverview, 1, 0, 2, 2);



        Label lblProductName = new Label("Produkt");

        Label lblAmount = new Label("Antal");

        HBox orderTitles = new HBox(lblProductName, lblAmount);
        orderOverview.getItems().setAll(orderTitles);




    }

    //Methods - Get, Set & Add -------------------------------------------


    //Methods - Other ----------------------------------------------------
    public void updateControls () {

    }

    public void productCategorySelectionChanged () {

    }


    /**
     * Adds a product from the overview to the current order
     * @param product the Product to add
     */
    private void addProductToOrder(Product product) {
        //Create label for the product and add to the overview
        Label lblProduct = new Label(product.toString());



        //Create amount textfield, with a + & - button

        //Create an orderline for the product and add to the order

    }

    private void updateProductOverview() {
        //Accordion control for showing of categories and products
        //Create accordion view, and add the titled pane
        accProductOverview = new Accordion();
        accProductOverview.setMaxWidth(Double.MAX_VALUE);
        accProductOverview.setPrefWidth(250);
        accProductOverview.setPadding(Insets.EMPTY);

        //For each category...
        for (ProductCategory proCat : saleController.getProductCategories()) {
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
            //Add the titledPane to the accordionView
            accProductOverview.getPanes().add(titledPane);
        }

        //add accordionView to tab
        this.add(accProductOverview, 0,0);
    }
}
