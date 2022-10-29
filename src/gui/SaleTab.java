package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private VBox orderLineView;
    private Order tempOrder;
    private VBox vbxOrderTotal;
    private TextField txfPercentDiscount;
    private VBox vbxFinalPrice;

    //Constructors ------------------------------------------------------
    public SaleTab() {
/*        this.setGridLinesVisible(true);*/
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);


        //Adds a choicebox to select the Situation and a listener for the box
        chSituation = new ChoiceBox<>();
        HBox.setHgrow(chSituation, Priority.ALWAYS);
        chSituation.setPrefWidth(150);
        chSituation.setMaxWidth(Double.MAX_VALUE);

        ChangeListener<Situation> situationListener = (ov, o, n) -> this.situationSelectionChanged();
        chSituation.getSelectionModel().selectedItemProperty().addListener(situationListener);

        //Adds a choicebox to select which unit to show prices in and a listener for the box
        chUnits = new ChoiceBox<>();
        chUnits.setPrefWidth(150);
        chUnits.setMaxWidth(Double.MAX_VALUE);

        ChangeListener<Unit> unitListener = (ov, o, n) -> this.unitSelectionChanged();
        chUnits.getSelectionModel().selectedItemProperty().addListener(unitListener);

        //Creates a HBox to display the choiceboxes
        HBox hbxChoices = new HBox(chSituation, chUnits);
        hbxChoices.setSpacing(10);
        this.add(hbxChoices, 0, 0, 3, 1);


        //Adds Accordion control for showing of categories and products
        accProductOverview = new Accordion();
        accProductOverview.setMaxWidth(Double.MAX_VALUE);
        accProductOverview.setPrefWidth(250);
        accProductOverview.setPadding(Insets.EMPTY);
        this.add(accProductOverview, 0, 2, 3, 2);

        //Initialisez an order
        tempOrder = orderController.createOrder(chSituation.getValue());

        //Adds a Vbox to hold OrderLines
        orderLineView = new VBox();
        orderLineView.setBackground(Background.fill(Color.WHITE));
        orderLineView.setBorder(Border.stroke(Color.BLACK));
        orderLineView.setPrefWidth(400);
        orderLineView.setPrefHeight(400);
        this.add(orderLineView, 3, 2, 3, 3);

        //Adds field and label for calculated total of the Order
        Label lblOrderTotal = new Label("Total:");
        lblOrderTotal.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));

        vbxOrderTotal = new VBox();
        vbxOrderTotal.setPrefWidth(75);
        vbxOrderTotal.setPrefHeight(75);
        vbxOrderTotal.setBackground(Background.EMPTY);
        vbxOrderTotal.setAlignment(Pos.BASELINE_RIGHT);
        vbxOrderTotal.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, new BorderWidths(0.0, 0.0, 0.5, 0.0))));

        HBox hbxOrderTotal = new HBox(lblOrderTotal, vbxOrderTotal);
        hbxOrderTotal.setSpacing(10);
        hbxOrderTotal.setAlignment(Pos.TOP_RIGHT);
        this.add(hbxOrderTotal, 5, 5);

        //Add field for percent Discount
        Label lblrabat = new Label("Rabat: ");
        lblrabat.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));

        txfPercentDiscount = new TextField("" + 0);
        txfPercentDiscount.setPrefWidth(75);
        txfPercentDiscount.setAlignment(Pos.BASELINE_RIGHT);
        ChangeListener<String> percentListener = (observable, oldV, newV) -> this.updateOrder();
        txfPercentDiscount.textProperty().addListener(percentListener);

        Label lblPercent = new Label("%");

        HBox hbxPercentDiscount = new HBox(lblrabat, txfPercentDiscount, lblPercent);
        hbxPercentDiscount.setAlignment(Pos.BASELINE_RIGHT);
        hbxPercentDiscount.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, new BorderWidths(0.0, 0.0, 0.5, 0.0))));
        this.add(hbxPercentDiscount, 5, 6);

        //Add field for the final price
        Label lblFinal = new Label("Endelig Total: ");
        lblFinal.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));

        vbxFinalPrice = new VBox();
        vbxFinalPrice.setPrefWidth(75);
        vbxFinalPrice.setPrefHeight(75);
        vbxFinalPrice.setBackground(Background.EMPTY);
        vbxFinalPrice.setAlignment(Pos.BASELINE_RIGHT);
        vbxFinalPrice.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, new BorderWidths(0.0, 0.0, 0.5, 0.0))));

        HBox hbxFinalPrice = new HBox(lblFinal, vbxFinalPrice);
        hbxFinalPrice.setAlignment(Pos.TOP_RIGHT);
        this.add(hbxFinalPrice, 5, 7);


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

        //Resets the order
        resetOrder();


    }


    /**
     * Adds a product from the overview to the current order
     *
     * @param price the price to add to the order
     */
    private void addProductToOrder(Price price) {


        if (tempOrder == null) {
            orderController.createOrder(chSituation.getValue());

        }

        //If product already is has an OrderLine in order, increments the amount
        boolean foundInOrderLine = false;
        for (OrderLine ol : tempOrder.getOrderLines()) {
            if (ol.getPrice().equals(price)) {
                foundInOrderLine = true;
                ol.setAmount(ol.getAmount() + 1);

            }
        }

        //Otherwise adds product to order with amount of 1
        if (!foundInOrderLine) {
            orderController.createOrderLineForOrder(tempOrder, 1, price);
        }

        //then displays orderlines in OrderLineView
        updateOrder();
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
                        HBox.setHgrow(productDescr, Priority.ALWAYS);

                        //Create textfield for price
                        TextField txfPrice = new TextField(price.getValue() + " " + price.getUnit());
                        txfPrice.setEditable(false);
                        txfPrice.setPrefWidth(75);
                        txfPrice.setBackground(Background.EMPTY);
                        txfPrice.setAlignment(Pos.BASELINE_RIGHT);

                        //Create HBox for holding the entire product line
                        HBox productLine = new HBox(productDescr, txfPrice);
                        productLine.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.DASHED, null, new BorderWidths(0.0, 0.0, 1, 0.0))));
                        for (Node ch : productLine.getChildren()) {
                            ch.setOnMouseClicked(event -> addProductToOrder(price));
                        }
                        productLine.setOnMouseClicked(event -> addProductToOrder(price));

                        vbxCategory.getChildren().add(productLine);

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

        //Resets and updates the order
        resetOrder();
        updateOrder();


    }

    /**
     * Called when the current unit is changed
     */
    private void unitSelectionChanged() {
        //Updates overview
        updateProductOverview();
    }

    private void resetOrder() {
        orderController.removeOrder(tempOrder);
        tempOrder = orderController.createOrder(chSituation.getValue());

    }

    /**
     * Updates the display of orderlines
     */
    public void updateOrder() {

        //Clears list
        orderLineView.getChildren().clear();

        //For each orderline in the order, creates a spinner for amount, a textfield for the product name, a textfield for price and a textfield for total cost
        for (OrderLine ol : tempOrder.getOrderLines()) {

            //Creates a spinner
            Spinner<Integer> spnAmount = new Spinner<>(0, 999, ol.getAmount());
            spnAmount.setEditable(true);
            spnAmount.setPrefWidth(60);
            ChangeListener<Integer> spinnerListener = (ov, n, o) -> amountChangedForOrderLine(spnAmount.getValue(), ol);
            spnAmount.valueProperty().addListener(spinnerListener);

            //Create a textfield with the product name and description
            TextField txfproductDescr = new TextField(ol.getPrice().getProduct().toString());
            txfproductDescr.setMaxWidth(Double.MAX_VALUE);
            txfproductDescr.setEditable(false);
            txfproductDescr.setBackground(Background.EMPTY);
            HBox.setHgrow(txfproductDescr, Priority.ALWAYS);

            //Create textfield for price
            TextField txfPrice = new TextField(ol.getPrice().getValue() + " " + ol.getPrice().getUnit());
            txfPrice.setEditable(false);
            txfPrice.setPrefWidth(75);
            txfPrice.setBackground(Background.EMPTY);
            txfPrice.setAlignment(Pos.BASELINE_RIGHT);

            //Create textfield for subtotal
            TextField txfSubTotal = new TextField("" + ol.calculateOrderLinePrice() + " " + ol.getPrice().getUnit());
            txfSubTotal.setEditable(false);
            txfSubTotal.setPrefWidth(75);
            txfSubTotal.setBackground(Background.EMPTY);
            txfSubTotal.setAlignment(Pos.BASELINE_LEFT);

            //Creates HBox to display the orderline
            HBox orderline = new HBox(spnAmount, txfproductDescr, txfPrice, txfSubTotal);
            orderline.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.DASHED, null, new BorderWidths(0.0, 0.0, 1, 0.0))));
            orderLineView.getChildren().add(orderline);
        }

        //Updates the calculated sum of the orderlines, and the final price
        vbxOrderTotal.getChildren().clear();
        vbxFinalPrice.getChildren().clear();

        for (Unit unit : Unit.values()) {
            double result = tempOrder.calculateSumPriceForUnit(unit);
            boolean currentUnitFound = false;
            for (OrderLine ol : tempOrder.getOrderLines()) {
                if (ol.getPrice().getUnit().equals(unit)) {
                    currentUnitFound = true;
                    break;
                }
            }

            if (currentUnitFound) {
                Label priceTotal = new Label(result + " " + unit);
                priceTotal.setAlignment(Pos.BASELINE_RIGHT);
                vbxOrderTotal.getChildren().add(priceTotal);

                double percentageMultiplier = (100 - Double.parseDouble(txfPercentDiscount.getText().trim())) / 100;
                double calculatedFinalPrice = result * percentageMultiplier;

                Label finalPrice = new Label((calculatedFinalPrice + " " + unit));
                finalPrice.setAlignment(Pos.BASELINE_RIGHT);
                vbxFinalPrice.getChildren().add(finalPrice);

            }
        }


    }

    /**
     * Updates orderlines when spinner is used to change amount
     *
     * @param orderLine the orderline to update
     */
    public void amountChangedForOrderLine(int newAmount, OrderLine orderLine) {
        if (newAmount < 1) {
            tempOrder.removeOrderLine(orderLine);
        } else {
            orderLine.setAmount(newAmount);
        }
        updateOrder();
    }


}


