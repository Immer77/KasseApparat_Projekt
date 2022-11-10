package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.OrderController;
import model.modelklasser.OrderLine;
import model.modelklasser.PaymentMethod;
import model.modelklasser.Tour;
import model.modelklasser.Unit;
import storage.Storage;

public class EndTourWindow extends Stage {

    private OrderControllerInterface orderController;
    private Tour tour;
    private ChoiceBox<PaymentMethod> chPaymentMethod;
    private VBox orderLineView, vboxFinalPrice, vboxTotalPrice, tourInfoVBox;
    private ListView<HBox> lvwTourOrderlines = new ListView<>();
    private HBox buttons;
    private TextField txfName;
    private TextArea txaDescription;
    private TextField endDatePicker, txfRabat, txfFixedPrice;
    private ListView lvwOrderlines = new ListView<>();
    private ChoiceBox<Unit> chUnits;

    private SplitPane splitPane = new SplitPane();

    public EndTourWindow(String title, Stage owner, Tour tour){
        this.tour = tour;
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
        //adds vbox for tour info
        tourInfoVBox = new VBox();
        tourInfoVBox.setPrefWidth(200);

        //adds labels, textfields, textarea and timepicker for tourinfo
        VBox nameVBox = new VBox();
        Label lblName = new Label("Navn");
        txfName =  new TextField();
        txfName.setEditable(false);
        txfName.appendText(tour.getName());
        nameVBox.getChildren().addAll(lblName, txfName);

        VBox descriptionVBox = new VBox();
        Label lblDescription = new Label("Beskrivelse");
        txaDescription = new TextArea();
        txaDescription.setEditable(false);
        txaDescription.appendText(tour.getDescription());
        descriptionVBox.getChildren().setAll(lblDescription,txaDescription);

        VBox endDateVBox = new VBox();
        endDatePicker = new TextField();
        endDatePicker.setText(String.valueOf(tour.getEndDate()));
        endDatePicker.setEditable(false);
        Label lblEndDate = new Label("Slut dato");
        endDateVBox.getChildren().addAll(lblEndDate,endDatePicker);

        tourInfoVBox.getChildren().addAll(nameVBox,descriptionVBox,endDateVBox);
        pane.add(tourInfoVBox,0,0);

        //Adds a Vbox to hold all OrderLines
        orderLineView = new VBox(new Label("Rundvisninger"));
        orderLineView.setPrefWidth(300);
//        pane.add(lvwOrderlines,1,0);
//        lvwOrderlines.getItems().addAll(orderLineView);
        updateOrderLineView();

        //adds vboxs to splitpane
        pane.add(splitPane, 1, 0, 2, 2);
        splitPane.getItems().addAll(orderLineView);

        //Confirm and cancel buttons with hbox to hold them
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

        buttons.getChildren().addAll(btnOK,btnCancel);
        pane.add(buttons,1,8);


        //vbox and label to show total before price changes
        Label lblTotalBefore = new Label("Total: ");
        pane.add(lblTotalBefore, 1, 2);
        vboxTotalPrice = new VBox();
        pane.add(vboxTotalPrice,2,2);

        // Percent discount on the rental
        Label lblRabat = new Label("Rabat: ");
        pane.add(lblRabat, 1, 3);

        txfRabat = new TextField(tour.getPercentDiscount()+ "");
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
        if (tour.getFixedPrice() > 0){
            txfFixedPrice.setText(tour.getFixedPrice() + "");
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

        //Add field for the final price
        Label lblFinal = new Label("Endelig Total: ");
        lblFinal.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        pane.add(lblFinal, 1, 6);

        vboxFinalPrice = new VBox();
        pane.add(vboxFinalPrice,2,6);

        // to select the payment method for the rental
        chPaymentMethod = new ChoiceBox<>();
        chPaymentMethod.setPrefWidth(150);
        chPaymentMethod.setMaxWidth(Double.MAX_VALUE);
        chPaymentMethod.getItems().setAll(PaymentMethod.values());
        chPaymentMethod.getSelectionModel().select(0);
        pane.add(chPaymentMethod,1,7);

        pane.setGridLinesVisible(false);
        ChangeListener<String> updateOnChange = (o, ov, nv) -> {
            updateControls();
        };

        txfRabat.textProperty().addListener(updateOnChange);
        txfFixedPrice.textProperty().addListener(updateOnChange);
        updateControls();

    }

    //----------------------------------------------------------------------------------------------------
    private void oKAction() {
        orderController.setPaymentMethodForOrder(tour, chPaymentMethod.getSelectionModel().getSelectedItem());
        orderController.saveOrder(tour);
        this.close();
    }

    private void cancelAction() {
        this.close();
    }

    private void updateOrderLineView() {
        orderLineView = new VBox(new Label("Rundvisninger"));
        orderLineView.setPrefWidth(300);
        for (OrderLine ol : tour.getOrderLines()){
            HBox orderline = new HBox();
            Label lblOL = new Label(" "+ol.getAmount() + " " + ol.getPrice().getProduct().toString() + " " + ol.getPrice().getValue() + ol.getPrice().getUnit());
            orderline.getChildren().setAll(lblOL);
            lvwTourOrderlines.getItems().setAll(orderline);
        }
        orderLineView.getChildren().add(lvwTourOrderlines);
    }

    public void updateControls(){
        updateTourTotal();
        fixedPriceTour();

    }

    private boolean fixedPriceTour(){
        boolean fixedPrice;
        if (!txfFixedPrice.getText().isBlank()){
            tour.setFixedPrice(Double.parseDouble(txfFixedPrice.getText().trim()));
            tour.setFixedPriceUnit(chUnits.getSelectionModel().getSelectedItem());
            Label lblFixPri = new Label(tour.getFixedPrice() + " " + chUnits.getValue());
            vboxFinalPrice.getChildren().setAll(lblFixPri);
            for (OrderLine ol : tour.getOrderLines()){
                Label lblResult = new Label(tour.calculateSumPriceForUnit(ol.getPrice().getUnit()) + " " + ol.getPrice().getUnit());
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

    private void updateTourTotal() {

        vboxTotalPrice.getChildren().clear();
        vboxFinalPrice.getChildren().clear();
        fixedPriceTour();
        if (!fixedPriceTour()){
            for (Unit unit : Unit.values()) {


                //Checks if there is any orderlines in the order with this unit
                boolean currentUnitFound = false;
                for (OrderLine ol : tour.getOrderLines()) {
                    if (ol.getPrice().getUnit().equals(unit)) {
                        currentUnitFound = true;
                        break;
                    }
                }

                //If orderline with this unit exists, create labels for the total of this unit
                if (currentUnitFound) {
                    double result = tour.calculateSumPriceForUnit(unit);

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

                    String finalPriceText = calculatedFinalPrice + " " + unit;
                    Label lblFinalPrice = new Label(finalPriceText + " ");
                    lblFinalPrice.setAlignment(Pos.BASELINE_RIGHT);
                    vboxFinalPrice.getChildren().add(lblFinalPrice);
                }
            }
        }
    }

}
