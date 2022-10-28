package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.controller.OrderController;
import model.controller.ProductOverviewController;
import model.modelklasser.*;
import storage.Storage;

public class SaleTab extends GridPane {
    //Fields ------------------------------------------------------------
    private OrderControllerInterface orderController = OrderController.getOrderController(Storage.getStorage());
    private ProductOverviewControllerInterface productController = ProductOverviewController.getProductOverviewController(Storage.getStorage());
    private Accordion accProductOverview;
    private ChoiceBox<Situation> chSituation;
    private ChoiceBox<Unit> chUnits;
    private Order currentOrder;

    //Constructors ------------------------------------------------------
    public SaleTab() {

        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        //Adds a choicebox to select the Situation and a listener for the box
        chSituation = new ChoiceBox<>();
        this.add(chSituation, 0, 0);

        ChangeListener<Situation> situationListener = (ov, o, n) -> this.situationSelectionChanged();
        chSituation.getSelectionModel().selectedItemProperty().addListener(situationListener);

        //Adds a choicebox to select which unit to show prices in and a listener for the box
        chUnits = new ChoiceBox<>();
        this.add(chUnits, 1, 0);

        ChangeListener<Unit> unitListener = (ov, o, n) -> this.unitSelectionChanged();
        chUnits.getSelectionModel().selectedItemProperty().addListener(unitListener);


        //Adds Accordion control for showing of categories and products
        accProductOverview = new Accordion();
        accProductOverview.setMaxWidth(Double.MAX_VALUE);
        accProductOverview.setPrefWidth(250);
        accProductOverview.setPadding(Insets.EMPTY);
        this.add(accProductOverview, 0, 1, 2, 2);

        //Adds a Vbox to hold OrderLines
        VBox orderLineView = new VBox();
        orderLineView.setBackground(Background.fill(Color.WHITE));
        orderLineView.setBorder(Border.stroke(Color.BLACK));
        this.add(orderLineView,2,0);


        //Initiates examples of situations and prices for products
        orderController.initContent();

        //Updates all controls
        updateControls();



    }

    //Methods - Get, Set & Add -------------------------------------------


    //Methods - Other ----------------------------------------------------

    /**
     * Updates all controls on this tab
     */
    public void updateControls() {
        //Update choiceboxes
        chSituation.getItems().setAll(orderController.getSituations());
        if (chSituation.getSelectionModel().isEmpty()) {
            chSituation.getSelectionModel().select(0);
        }

        chUnits.getItems().setAll(Unit.values());
        if (chUnits.getSelectionModel().isEmpty()) {
            chUnits.getSelectionModel().select(0);
        }

        //Update productOverview
        updateProductOverview();

        //Clears old order and creates a new one
        /*currentOrder = orderController.*/

    }



    /**
     * Adds a product from the overview to the current order
     *
     * @param product the Product to add
     */
    private void addProductToOrder(Product product) {


    }

    /**
     * Updates the overview of products, depending on the Situation and the Unit chosen.
     */
    private void updateProductOverview() {
        TitledPane selected = accProductOverview.getExpandedPane();

        accProductOverview.getPanes().clear();


        //For each price in each product in each category...
        for (ProductCategory proCat : productController.getProductCategories()) {
            VBox vbxCategory = new VBox();
            vbxCategory.setPadding(new Insets(5));
            vbxCategory.setFillWidth(true);
            vbxCategory.maxWidth(Double.MAX_VALUE);
            vbxCategory.setPrefWidth(accProductOverview.getPrefWidth());


            for (Product prod : proCat.getProducts()) {
                for (Price price : prod.getPrices()) {
                    //Determine if there is any prices matching unit and situation
                    if (price.getSituation().equals(chSituation.getSelectionModel().getSelectedItem()) && price.getUnit().equals(chUnits.getSelectionModel().getSelectedItem())) {

                        //Create a textfield with the product name and description
                        TextField productDescr = new TextField(prod.toString());
                        productDescr.setMaxWidth(Double.MAX_VALUE);
                        productDescr.setEditable(false);
                        productDescr.setBackground(Background.EMPTY);

                        //Create textfield for price
                        TextField txfPrice = new TextField(price.getValue()+" "+price.getUnit());
                        txfPrice.setEditable(false);
                        txfPrice.setPrefWidth(75);
                        txfPrice.setBackground(Background.EMPTY);
                        txfPrice.setAlignment(Pos.BASELINE_RIGHT);


                        //Create Borderpane to hold the product and price
                        BorderPane productline = new BorderPane(null, null, txfPrice, null, productDescr);
                        productline.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.DASHED, null,new BorderWidths(0.0,0.0,1,0.0))));
                        productline.setOnMouseClicked(event -> addProductToOrder(prod));

                        vbxCategory.getChildren().add(productline);

                    }
                }
            }

            //If category is not empty...
            if (!vbxCategory.getChildren().isEmpty()) {
                //Create a titled pane and add the vbox to it
                TitledPane titledPane = new TitledPane(proCat.getTitle(), vbxCategory);
                accProductOverview.getPanes().add(titledPane);
            }

        }

        if (accProductOverview.getPanes().contains(selected)) {
            accProductOverview.setExpandedPane(selected);
        } else if (!accProductOverview.getPanes().isEmpty()) {
            accProductOverview.setExpandedPane(accProductOverview.getPanes().get(0));
        }
    }

    /**
     * Called when the current Situation is changed.
     */
    private void situationSelectionChanged() {
        //Sets units to the default unit
        chUnits.getItems().setAll(Unit.values());
        chUnits.getSelectionModel().select(0);



        //Updates overview
        updateProductOverview();


    }

    /**
     * Called when the current unit is changed
     */
    private void unitSelectionChanged() {
        //Updates overview
        updateProductOverview();
    }


}
