package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.OrderController;
import model.modelklasser.Order;
import model.modelklasser.OrderLine;
import model.modelklasser.PaymentMethod;
import model.modelklasser.Unit;
import storage.Storage;

import java.time.LocalDate;

public class EndOrderWindow extends Stage {
    //Fields ------------------------------------------------------------
    private OrderControllerInterface orderController = OrderController.getOrderController(Storage.getStorage());
    private Order order;
    private ChoiceBox<PaymentMethod> chPaymentMethod;


    //Constructors ------------------------------------------------------
    public EndOrderWindow(String title, Stage owner, Order order) {
        this.order = order;
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

    //Methods - Get, Set & Add -------------------------------------------


    //Methods - Other ----------------------------------------------------
    private void initContent(GridPane pane) {
        //pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        //Overview of the final order

        VBox vbxProduct = new VBox();
        vbxProduct.setPrefWidth(200);
        Label lblProductNameTitle = new Label("Produkt:");
        lblProductNameTitle.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        vbxProduct.getChildren().add(lblProductNameTitle);

        VBox vbxAmount = new VBox();
        vbxAmount.setPrefWidth(100);
        Label lblAmountTitle = new Label("Mængde:");
        lblAmountTitle.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        vbxAmount.getChildren().add(lblAmountTitle);

        VBox vbxTotalPrice = new VBox();
        vbxTotalPrice.setPrefWidth(100);
        Label lblTotalPrice = new Label("Samlet Pris:");
        lblTotalPrice.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        vbxTotalPrice.setAlignment(Pos.BASELINE_RIGHT);
        vbxTotalPrice.getChildren().add(lblTotalPrice);

        for (OrderLine orderLine : order.getOrderLines()) {
            Label lblProductName = new Label(orderLine.getPrice().getProduct().getName());
            vbxProduct.getChildren().add(lblProductName);

            Label lblAmount = new Label("" + orderLine.getAmount());
            vbxAmount.getChildren().add(lblAmount);


            Label lblPriceTotal = new Label(orderLine.calculateOrderLinePrice() + " " + orderLine.getPrice().getUnit());
            lblPriceTotal.setAlignment(Pos.BASELINE_RIGHT);
            vbxTotalPrice.getChildren().add(lblPriceTotal);
        }

        HBox hbxOrderlineOverview = new HBox(vbxProduct, vbxAmount, vbxTotalPrice);
        hbxOrderlineOverview.setPadding(new Insets(0, 0, 30, 0));
        pane.add(hbxOrderlineOverview, 0, 0);

        VBox vbxPriceTotals = new VBox();
        if (order.getFixedPrice() == -1.0)  {
            for (Unit unit : Unit.values()) {
                if (order.calculateSumPriceForUnit(unit) > 0) {
                    Label lblUnitTotalPrice = new Label(order.calculateSumPriceForUnit(unit) + " " + unit);
                    vbxPriceTotals.getChildren().add(lblUnitTotalPrice);
                }
            }
        } else {
            Label lblAgreed = new Label("Aftalt:");
            Label lblUnitTotalPrice = new Label(""+order.getFixedPrice() + " " + order.getFixedPriceUnit());
            vbxPriceTotals.getChildren().add(lblAgreed);
            vbxPriceTotals.getChildren().add(lblUnitTotalPrice);

        }

        vbxPriceTotals.setAlignment(Pos.BASELINE_RIGHT);
        pane.add(vbxPriceTotals, 0, 1);


        //choicebox for payment method
        Label lblPaymentSelect = new Label("Betaling med:");
        lblPaymentSelect.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));

        chPaymentMethod = new ChoiceBox<>();
        HBox.setHgrow(chPaymentMethod, Priority.ALWAYS);
        chPaymentMethod.setPrefWidth(150);
        chPaymentMethod.setMaxWidth(Double.MAX_VALUE);
        chPaymentMethod.getItems().setAll(PaymentMethod.values());
        chPaymentMethod.getSelectionModel().select(0);

        Label lblPunchcardNotice = new Label("Bemærk: Klip betales altid med Klippekort");

        VBox vbxPaymentOptions = new VBox(lblPaymentSelect, chPaymentMethod, lblPunchcardNotice);
        vbxPaymentOptions.setPadding(new Insets(0, 0, 30, 0));
        pane.add(vbxPaymentOptions, 0, 2);


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
        orderController.setPaymentMethodForOrder(order, chPaymentMethod.getSelectionModel().getSelectedItem());
        orderController.setEndDateForOrder(order, LocalDate.now());
        orderController.saveOrder(order);
        this.close();
    }

    private void cancelAction() {
        this.close();

    }
}
