package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.OrderController;
import model.modelklasser.OrderLine;
import model.modelklasser.PaymentMethod;
import model.modelklasser.Rental;
import storage.Storage;

public class EndRentalWindow extends Stage {

    private OrderControllerInterface orderController = OrderController.getOrderController(Storage.getStorage());
    private Rental rental;
    private ChoiceBox<PaymentMethod> chPaymentMethod;
    private VBox orderLineView;

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
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        //Adds a Vbox to hold OrderLines
        orderLineView = new VBox();
        orderLineView.setBackground(Background.fill(Color.WHITE));
        orderLineView.setBorder(Border.stroke(Color.BLACK));
        orderLineView.setPrefWidth(400);
        pane.add(orderLineView, 3, 2, 3, 3);

            //Confirm and cancel buttons
            Button btnOK = new Button("Bekræft");
            pane.add(btnOK, 0, 3);
            btnOK.setOnAction(event -> oKAction());
            btnOK.setDefaultButton(true);

            Button btnCancel = new Button("Fortryd");
            pane.add(btnCancel, 1, 3);
            btnCancel.setOnAction(event -> cancelAction());
            btnCancel.setCancelButton(true);

    }

        private void oKAction() {
            orderController.setPaymentMethodForOrder(rental, chPaymentMethod.getSelectionModel().getSelectedItem());
            orderController.saveOrder(rental);
            this.close();
        }

        private void cancelAction() {
            this.close();
        }
        public void amountChangedForOrderLine ( int newAmount, OrderLine orderLine){
            if (newAmount < 1) {
                rental.removeOrderLine(orderLine);
            } else {
                orderLine.setAmount(newAmount);
            }
            updateRental();
        }

    private void updateRental() {
        for (OrderLine ol : rental.getOrderLines()) {

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
    }
}