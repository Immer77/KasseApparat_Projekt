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
import javafx.stage.Stage;
import model.controller.OrderController;
import model.controller.ProductOverviewController;
import model.modelklasser.*;
import storage.Storage;

public class SaleTab extends GridPane {
    //Fields ------------------------------------------------------------
    private OrderControllerInterface orderController;

    private Stage mainStage;
    private Accordion accProductOverview;
    private ChoiceBox<Situation> chSituation;
    private ChoiceBox<Unit> chUnits;
    private VBox orderLineView;
    private Order tempOrder;
    private VBox vbxOrderTotal;
    private TextField txfPercentDiscount;
    private VBox vbxFinalPrice;
    private TextField txfFixedTotal;
    private ChoiceBox<Unit> chFixedUnit;

    //Constructors ------------------------------------------------------
    public SaleTab() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        orderController = new OrderController(Storage.getStorage());

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

        //Initialises an order
        tempOrder = orderController.createOrder();

        //Adds Accordion control for showing of categories and products
        accProductOverview = new Accordion();
        accProductOverview.setMaxWidth(Double.MAX_VALUE);
        accProductOverview.setPrefWidth(250);
        accProductOverview.setPadding(Insets.EMPTY);
        this.add(accProductOverview, 0, 2, 3, 2);


        //Adds a Vbox to hold OrderLines
        orderLineView = new VBox();
        orderLineView.setBackground(Background.fill(Color.WHITE));
        orderLineView.setBorder(Border.stroke(Color.BLACK));
        orderLineView.setPrefWidth(400);
        this.add(orderLineView, 3, 2, 3, 3);

        //Adds field and label for calculated total of the Order
        Label lblOrderTotal = new Label("Total:");
        lblOrderTotal.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        this.add(lblOrderTotal, 4, 6);

        vbxOrderTotal = new VBox();
        vbxOrderTotal.setPrefWidth(75);
        vbxOrderTotal.setBackground(Background.EMPTY);
        vbxOrderTotal.setAlignment(Pos.BASELINE_RIGHT);
        this.add(vbxOrderTotal, 5, 6);

        //Add field for percent Discount
        Label lblrabat = new Label("Rabat: ");
        lblrabat.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        this.add(lblrabat, 4, 7);

        txfPercentDiscount = new TextField("" + 0);
        txfPercentDiscount.setPrefWidth(75);
        txfPercentDiscount.setAlignment(Pos.BASELINE_RIGHT);
        ChangeListener<String> percentListener = (observable, oldV, newV) -> this.updateDiscount();
        txfPercentDiscount.textProperty().addListener(percentListener);

        Label lblPercent = new Label("%");

        HBox hbxPercentDiscount = new HBox(txfPercentDiscount, lblPercent);
        hbxPercentDiscount.setAlignment(Pos.BASELINE_RIGHT);
        this.add(hbxPercentDiscount, 5, 7);

        //Add field for a fixed total price
        Label lblFixedPrice = new Label("Aftalt Total: ");
        lblFixedPrice.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        this.add(lblFixedPrice, 4, 8);

        txfFixedTotal = new TextField();
        txfFixedTotal.setPrefWidth(75);
        txfFixedTotal.setAlignment(Pos.BASELINE_RIGHT);
        ChangeListener<String> fixedTotalListener = (observable, oldV, newV) -> this.updateFixedPrice();
        txfFixedTotal.textProperty().addListener(fixedTotalListener);

        chFixedUnit = new ChoiceBox<>();
        chFixedUnit.getItems().setAll(Unit.values());
        chFixedUnit.getSelectionModel().select(0);
        ChangeListener<Unit> fixedUnitListener = (observable, oldV, newV) -> this.updateFixedPrice();
        chFixedUnit.getSelectionModel().selectedItemProperty().addListener(fixedUnitListener);

        HBox hbxFixedTotal = new HBox(txfFixedTotal, chFixedUnit);
        hbxFixedTotal.setAlignment(Pos.BASELINE_RIGHT);
        hbxFixedTotal.setPadding(new Insets(0, 10, 5, 0));
        hbxFixedTotal.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, new BorderWidths(0.0, 0.0, 0.5, 0.0))));
        this.add(hbxFixedTotal, 5, 8);


        //Add field for the final price
        Label lblFinal = new Label("Endelig Total: ");
        lblFinal.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        this.add(lblFinal, 4, 9);

        vbxFinalPrice = new VBox();
        vbxFinalPrice.setPrefWidth(75);
        vbxFinalPrice.setBackground(Background.EMPTY);
        vbxFinalPrice.setAlignment(Pos.BASELINE_RIGHT);

        HBox hbxFinalPrice = new HBox(vbxFinalPrice);
        hbxFinalPrice.setAlignment(Pos.TOP_RIGHT);
        hbxFinalPrice.setPadding(new Insets(30, 10, 0, 0));
        this.add(hbxFinalPrice, 5, 9);


        //Add confirmation button for order
        Button btnConfirmOrder = new Button("Afslut Ordre");
        btnConfirmOrder.setMaxWidth(Double.MAX_VALUE);
        btnConfirmOrder.setOnAction(event -> confirmOrderAction());
        this.add(btnConfirmOrder, 3, 10, 3, 1);

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
            orderController.createOrder();
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
        for (ProductCategory proCat : orderController.getProductCategories()) {
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
        tempOrder = orderController.createOrder();
        txfPercentDiscount.setText("" + 0);
        txfFixedTotal.clear();
        updateOrder();
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
            Spinner<Integer> spnAmount = new Spinner<>(0, Double.MAX_VALUE, ol.getAmount());
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

        //Clears both the calculated total of all the orderlines, and the final total price for the order
        vbxOrderTotal.getChildren().clear();
        vbxFinalPrice.getChildren().clear();

        //For each unit, calculates the sum of the orderlines with that unit
        for (Unit unit : Unit.values()) {


            //Checks if there is any orderlines in the order with this unit
            boolean currentUnitFound = false;
            for (OrderLine ol : tempOrder.getOrderLines()) {
                if (ol.getPrice().getUnit().equals(unit)) {
                    currentUnitFound = true;
                    break;
                }
            }

            //If orderline with this unit exists, create labels for the total of this unit
            if (currentUnitFound) {
                double result = tempOrder.calculateSumPriceForUnit(unit);

                Label priceTotal = new Label(result + " " + unit);
                priceTotal.setAlignment(Pos.BASELINE_RIGHT);
                vbxOrderTotal.getChildren().add(priceTotal);

                //Calculate the total after subtracting the percentage discount

                double calculatedFinalPrice = result;
                try {
                    if (!txfPercentDiscount.getText().isBlank()) {
                        double percentageDiscount = Double.parseDouble(txfPercentDiscount.getText().trim());
                        double percentageMultiplier = 1.0;

                        if (percentageDiscount >= 0 && percentageDiscount <= 100) {
                            percentageMultiplier = (100 - percentageDiscount) / 100;
                        } else {
                            throw new NumberFormatException("Procentrabatten skal være et tal mellem 0 og 100");
                        }
                        calculatedFinalPrice = result * percentageMultiplier;
                    }

                } catch (NumberFormatException nfe) {
                    txfPercentDiscount.setText("" + 0);

                    Alert alertNFE = new Alert(Alert.AlertType.ERROR);
                    alertNFE.setTitle("Indtastet værdi er ikke et tal");
                    alertNFE.setContentText(nfe.getMessage());
                    alertNFE.showAndWait();
                }

                String finalPriceText = calculatedFinalPrice + " " + unit;
                Label lblFinalPrice = new Label(finalPriceText);
                lblFinalPrice.setAlignment(Pos.BASELINE_RIGHT);
                vbxFinalPrice.getChildren().add(lblFinalPrice);

            }
        }

        if (!txfFixedTotal.getText().isBlank()) {
            vbxFinalPrice.getChildren().clear();
            Label lblAgreedTotal = new Label("--AFTALT--");
            lblAgreedTotal.setAlignment(Pos.BASELINE_RIGHT);
            Label lblManualFinalPrice = new Label(txfFixedTotal.getText() + " " + chFixedUnit.getSelectionModel().getSelectedItem());
            lblManualFinalPrice.setAlignment(Pos.BASELINE_RIGHT);
            vbxFinalPrice.getChildren().add(lblAgreedTotal);
            vbxFinalPrice.getChildren().add(lblManualFinalPrice);
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

    /**
     * Called when the confirm order button is used. Opens a new window, in order to finish the order
     */
    private void confirmOrderAction() {
        updateFixedPrice();
        updateDiscount();

        EndOrderWindow endOrderWindow = new EndOrderWindow("Ordrebekræftigelse", new Stage(), tempOrder);
        endOrderWindow.showAndWait();

        resetOrder();
    }

    private void updateDiscount() {
        try {
            if (txfPercentDiscount.getText().isBlank()) {
                txfPercentDiscount.setText("" + 0);
            }

            double discount = Double.parseDouble(txfPercentDiscount.getText().trim());

            if (discount >= 0.0 && discount <= 100.0) {
                orderController.setDiscountForOrder(tempOrder, discount);
            } else {
                throw new NumberFormatException("procentrabat skal være et tal mellem 0.0 og 100");
            }

        } catch (NumberFormatException nfe) {
            txfPercentDiscount.setText("" + 0);

            Alert alertNFE = new Alert(Alert.AlertType.ERROR);
            alertNFE.setTitle("Indtastet værdi er ikke et tal");
            alertNFE.setContentText(nfe.getMessage());
            alertNFE.showAndWait();

        } catch (RuntimeException rte) {
            txfPercentDiscount.setText("" + 0);

            Alert alertRTE = new Alert(Alert.AlertType.ERROR);
            alertRTE.setTitle("Forkert værdi");
            alertRTE.setContentText("Tal skal være mellem 0 og 100");
            alertRTE.showAndWait();

        }


        updateOrder();
    }

    private void updateFixedPrice() {
        try {
            if (!txfFixedTotal.getText().isBlank()) {
                double value = Double.parseDouble(txfFixedTotal.getText());
                if (Double.parseDouble(txfFixedTotal.getText()) < 0) {
                    throw new NumberFormatException("Den indtastede værdi kan ikke være mindre end 0");
                }

                Unit unit = chFixedUnit.getSelectionModel().getSelectedItem();
                orderController.setFixedPriceForOrder(tempOrder, value, unit);
            } else {
                orderController.setFixedPriceForOrder(tempOrder, -1.0, null);
            }

            updateOrder();
        } catch (NumberFormatException nfe) {
            txfFixedTotal.clear();

            Alert alertNFE = new Alert(Alert.AlertType.ERROR);
            alertNFE.setTitle("Forkert indtastet værdi");
            alertNFE.setContentText(nfe.getMessage());
            alertNFE.showAndWait();
        }

    }


}


