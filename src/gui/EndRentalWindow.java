package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.OrderController;
import model.modelklasser.OrderLine;
import model.modelklasser.PaymentMethod;
import model.modelklasser.Rental;
import model.modelklasser.Unit;
import storage.Storage;

public class EndRentalWindow extends Stage {

    private OrderControllerInterface orderController;
    private Rental rental;
    private ChoiceBox<PaymentMethod> chPaymentMethod;
    private ChoiceBox<Unit> chUnits;
    private VBox orderLineView, unusedOrderLineView, rentalInfoVBox;
    private SplitPane splitPane = new SplitPane();
    private ListView<HBox> lvwRentalOrderlines = new ListView<>();
    private ListView<HBox> lvwUnusedProducts = new ListView<>();
    private HBox buttons;
    private TextField txfName, txfStartDate;
    private TextArea txaDescription;
    private DatePicker endDatePicker;
    private TextField txfRabat, txfFixedPrice;
    private VBox vboxFinalPrice, vboxTotalPrice;
    private double depositPrice;

    public EndRentalWindow(String title, Stage owner, Rental rental) {
        this.rental = rental;
        this.initOwner(owner);
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setMinHeight(300);
        this.setMinWidth(500);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

        orderController = new OrderController(Storage.getStorage());
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        //adds vbox for rental info
        rentalInfoVBox = new VBox();
        rentalInfoVBox.setPrefWidth(200);

        //adds labels, textfields, textarea and datepicker for rentalinfo
        VBox nameVBox = new VBox();
        Label lblName = new Label("Navn");
        txfName = new TextField();
        txfName.setEditable(false);
        txfName.appendText(rental.getName());
        nameVBox.getChildren().addAll(lblName, txfName);

        VBox descriptionVBox = new VBox();
        Label lblDescription = new Label("Beskrivelse");
        txaDescription = new TextArea();
        txaDescription.setEditable(false);
        txaDescription.appendText(rental.getDescription());
        descriptionVBox.getChildren().setAll(lblDescription, txaDescription);

        VBox startdateVBox = new VBox();
        Label lblStartDate = new Label("Start dato");
        txfStartDate = new TextField(rental.getStartDate().toString());
        txfStartDate.setEditable(false);
        startdateVBox.getChildren().addAll(lblStartDate, txfStartDate);

        VBox endDateVBox = new VBox();
        endDatePicker = new DatePicker();
        endDatePicker.setValue(rental.getEndDate());
        Label lblEndDate = new Label("Slut dato");
        endDateVBox.getChildren().addAll(lblEndDate, endDatePicker);

        HBox dateHBox = new HBox();
        dateHBox.getChildren().addAll(startdateVBox, endDateVBox);

        rentalInfoVBox.getChildren().addAll(nameVBox, descriptionVBox, dateHBox);
        pane.add(rentalInfoVBox, 0, 0);


        //Adds a Vbox to hold all OrderLines
        orderLineView = new VBox(new Label("Alle udlejede produkter"));
        orderLineView.setPrefWidth(300);
        updateOrderLineView();


        //adds a VBox to hold unused products
        unusedOrderLineView = new VBox();
        Label lblUnused = new Label("Produkter som er ubrugte");
        unusedOrderLineView.setPrefWidth(500);
        unusedOrderLineView.getChildren().setAll(lblUnused, lvwUnusedProducts);


        //adds vboxs to splitpane
        pane.add(splitPane, 1, 0, 2, 2);
        splitPane.getItems().addAll(orderLineView, unusedOrderLineView);

        //Confirm and cancel buttons with vbox to hold them
        buttons = new HBox();
        buttons.setSpacing(10);
        buttons.setPrefWidth(200);

        Button btnOK = new Button("Bekræft");
        btnOK.setMaxWidth(Double.MAX_VALUE);
        btnOK.setOnAction(event -> oKAction());
        btnOK.setDefaultButton(true);

        Button btnCancel = new Button("Fortryd");
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setOnAction(event -> cancelAction());
        btnCancel.setCancelButton(true);

        buttons.getChildren().addAll(btnOK, btnCancel);
        pane.add(buttons,1,8);

        //vbox and label to show total before price changes
        Label lblTotalBefore = new Label("Total: ");
        pane.add(lblTotalBefore, 1, 2);
        vboxTotalPrice = new VBox();
        pane.add(vboxTotalPrice,2,2);

        // Percent discount on the rental
        Label lblRabat = new Label("Rabat: ");
        pane.add(lblRabat, 1, 3);

        txfRabat = new TextField(rental.getPercentDiscount()+ "");
        txfRabat.setPrefWidth(45);
        txfRabat.setMaxWidth(90);

        Label lblProcent = new Label(" %");

        HBox hboxRabat = new HBox();
        hboxRabat.getChildren().setAll(txfRabat,lblProcent);
        pane.add(hboxRabat,2,3);

        // Fixed price for rental
        Label lblFixedPrice = new Label("Aftalt pris: ");
        pane.add(lblFixedPrice, 1, 4);

        txfFixedPrice = new TextField();
        if (rental.getFixedPrice() > 0){
            txfFixedPrice.setText(rental.getFixedPrice() + "");
        }
        txfFixedPrice.setPrefWidth(45);
        txfFixedPrice.setMaxWidth(90);

        chUnits = new ChoiceBox<>();
        chUnits.setPrefWidth(50);
        chUnits.getItems().setAll(Unit.values());
        chUnits.getSelectionModel().select(0);

        HBox hboxFixPri = new HBox();
        hboxFixPri.getChildren().setAll(txfFixedPrice,chUnits);
        pane.add(hboxFixPri,2,4);


        // final total for the rental
        Label lblFinal = new Label("Endelig Total: ");
        lblFinal.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        pane.add(lblFinal, 1, 6);

        vboxFinalPrice = new VBox();
        pane.add(vboxFinalPrice,2,6);

        // deposit price
        Label lblDeposit = new Label("Pant: ");
        pane.add(lblDeposit,1,5);

        depositPrice = displayDepositPrice();
        Label lblDepositPrice = new Label("" + depositPrice + " " + Unit.DKK);
        pane.add(lblDepositPrice,2,5);

        // to select the payment method for the rental
        chPaymentMethod = new ChoiceBox<>();
        chPaymentMethod.setPrefWidth(150);
        chPaymentMethod.setMaxWidth(Double.MAX_VALUE);
        chPaymentMethod.getItems().setAll(PaymentMethod.values());
        chPaymentMethod.getSelectionModel().select(0);
        pane.add(chPaymentMethod,1,7);

        pane.setGridLinesVisible(false);
        ChangeListener<String> updateOnChange = (o,ov,nv) -> {
            updateControls();
        };
        txfRabat.textProperty().addListener(updateOnChange);
        txfFixedPrice.textProperty().addListener(updateOnChange);
        updateUnusedProducts();
    }

    public void updateControls(){
        updateRentalTotal();
        updateUnusedProducts();
        fixedPriceRental();
    }

    public boolean fixedPriceRental(){
        boolean fixedPrice;
        if (!txfFixedPrice.getText().isBlank()){
            rental.setFixedPrice(Double.parseDouble(txfFixedPrice.getText().trim()));
            rental.setFixedPriceUnit(chUnits.getSelectionModel().getSelectedItem());
            double fixedMinusDeposit = rental.getFixedPrice() - depositPrice;
            Label lblFixPri = new Label( fixedMinusDeposit + " " + chUnits.getValue());
            vboxFinalPrice.getChildren().setAll(lblFixPri);
            for (OrderLine ol : rental.getOrderLines()){
                Label lblResult = new Label(rental.calculateSumPriceForUnit(ol.getPrice().getUnit()) + " " + ol.getPrice().getUnit());
                vboxTotalPrice.getChildren().setAll(lblResult);
            }
            txfRabat.setDisable(true);
            fixedPrice = true;
        } else {
            txfRabat.setDisable(false);
            fixedPrice = false;
        }
        return fixedPrice;
    }

    public void updateRentalTotal() {
        vboxTotalPrice.getChildren().clear();
        vboxFinalPrice.getChildren().clear();
        fixedPriceRental();
        if (!fixedPriceRental()){
            for (Unit unit : Unit.values()) {


                //Checks if there is any orderlines in the order with this unit
                boolean currentUnitFound = false;
                for (OrderLine ol : rental.getOrderLines()) {
                    if (ol.getPrice().getUnit().equals(unit)) {
                        currentUnitFound = true;
                        break;
                    }
                }

                //If orderline with this unit exists, create labels for the total of this unit
                if (currentUnitFound) {
                    double result = rental.calculateSumPriceForUnit(unit);

                    Label priceTotal = new Label(result + " " + unit);
                    priceTotal.setAlignment(Pos.BASELINE_RIGHT);
                    vboxTotalPrice.getChildren().add(priceTotal);

                    //Calculate the total after subtracting the percentage discount

                    double calculatedFinalPrice = result;
                    try {
                        if (!txfRabat.getText().isBlank()) {
                            double percentageDiscount = Double.parseDouble(txfRabat.getText().trim());
                            double percentageMultiplier = 1.0;

                            if (percentageDiscount >= 0 && percentageDiscount <= 100) {
                                percentageMultiplier = (100 - percentageDiscount) / 100;
                            } else {
                                throw new NumberFormatException("Procentrabatten skal være et tal mellem 0 og 100");
                            }
                            calculatedFinalPrice = result * percentageMultiplier;
                        }

                    } catch (NumberFormatException nfe) {
                        txfRabat.setText("" + 0);

                        Alert alertNFE = new Alert(Alert.AlertType.ERROR);
                        alertNFE.setTitle("Indtastet værdi er ikke et tal");
                        alertNFE.setContentText(nfe.getMessage());
                        alertNFE.showAndWait();
                    }

                    String finalPriceText = calculatedFinalPrice - depositPrice + " " + unit;
                    Label lblFinalPrice = new Label(finalPriceText + " ");
                    lblFinalPrice.setAlignment(Pos.BASELINE_RIGHT);
                    vboxFinalPrice.getChildren().add(lblFinalPrice);
                }
            }
        }
    }

    public double displayDepositPrice(){
        double result = 0.0;
        try {
            if (rental.calculateDeposit() > 0) {
                result = rental.calculateDeposit();
            }
        } catch (NullPointerException npe){
            npe.getMessage();
        }
        return result;
    }

    private void updateOrderLineView() {
        for (OrderLine ol : rental.getOrderLines()) {
            if (ol.getAmount() > 0) {
                HBox orderline = new HBox();
                Label lblOL = new Label();
                if (ol.getPrice().getProduct().getDepositPrice() != null){
                    lblOL.setText(" " + ol.getAmount() + " " + ol.getPrice().getProduct().toString() + " " + ol.getPrice().getValue() + ol.getPrice().getUnit() );
                } else {
                    lblOL.setText(" " + ol.getAmount() + " " + ol.getPrice().getProduct().toString() + " " + ol.getPrice().getValue() + ol.getPrice().getUnit() + "    Pant pr. stk.: " + ol.getPrice().getProduct().getDepositPrice().getValue() + " " + ol.getPrice().getProduct().getDepositPrice().getUnit() + "  Total: " + ol.calculateOrderLinePrice());
                }
                orderline.setOnMouseClicked(event -> addToUnusedProducts(ol));
                orderline.getChildren().setAll(lblOL);
                lvwRentalOrderlines.getItems().add(orderline);
            }
        }
        orderLineView.getChildren().add(lvwRentalOrderlines);
    }

    private void oKAction() {
        orderController.setPaymentMethodForOrder(rental, chPaymentMethod.getSelectionModel().getSelectedItem());
        orderController.saveOrder(rental);
        this.close();
    }

    private void cancelAction() {
        this.close();
    }

    private void changeAmountOnOrderLine(int newAmount, OrderLine ol) {
        if (newAmount >= 0) {
            rental.removeOrderLine(ol);
        } else {
            ol.setAmount(newAmount);
        }
        updateUnusedProducts();
        updateRentalTotal();
    }


    private void updateUnusedProducts() {
        lvwUnusedProducts.getItems().clear();
        updateRentalTotal();
        fixedPriceRental();

        //For each orderline in the order, creates a spinner for amount, a textfield for the product name, a textfield for price and a textfield for total cost
        for (OrderLine ol : rental.getOrderLines()) {
            if (ol.getAmount() < 0) {
                int maxvalue = -999;
                for (OrderLine olcheck : rental.getOrderLines()) {
                    if (ol.getPrice().equals(olcheck.getPrice()) && olcheck.getAmount() > 0) {
                        maxvalue = olcheck.getAmount() * -1;
                    }
                }

                //Creates a spinner
                Spinner<Integer> spnAmount = new Spinner<>(maxvalue, 0, ol.getAmount());
                spnAmount.setEditable(true);
                spnAmount.setPrefWidth(60);
                ChangeListener<Integer> spinnerListener = (ov, n, o) -> changeAmountOnOrderLine(spnAmount.getValue(), ol);
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
                lvwUnusedProducts.getItems().add(orderline);
                updateRentalTotal();
            }
        }
    }

    private void addToUnusedProducts(OrderLine orderLine) {
        boolean found = false;
        //creates new negative orderline as a copy of the selected item
        for (OrderLine ol : rental.getOrderLines()) {
            if (ol.getPrice().equals(orderLine.getPrice()) && ol.getAmount() < 0) {
                found = true;
                if (orderLine.getAmount() <= ol.getAmount()) {
                    ol.setAmount(ol.getAmount() - 1);
                }
            }
        }
        if (found == false) {
            orderController.createOrderLineForOrder(rental, -1, orderLine.getPrice());
        }

        //then displays orderlines in OrderLineView
        updateUnusedProducts();
        updateRentalTotal();
        fixedPriceRental();
    }
}