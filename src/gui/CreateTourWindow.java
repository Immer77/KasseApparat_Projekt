package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.OrderController;
import model.controller.ProductOverviewController;
import model.modelklasser.*;
import storage.Storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CreateTourWindow extends Stage {

    // Field variables
    private final TextField txfName = new TextField();
    private final TextArea txaDescription = new TextArea();
    private final OrderControllerInterface controller;
    private final DatePicker datePicker = new DatePicker();
    private VBox orderLineView;
    private Order order;
    private VBox vbxOrderTotal;
    private TextField txfPercentDiscount;
    private VBox vbxFinalPrice;
    private TextField txfFixedTotal;
    private TextField txfTidspunkt = new TextField();
    private LocalDateTime tidspunkt;
    // Lav 2 choice boxes istedet for textfield. Måske kombo box.

    // Constructor to createRentalWindows
    public CreateTourWindow(String title, Stage owner){
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

        controller = new OrderController(Storage.getStorage());
        //Initialises an order
        order = controller.createOrder();
    }

    /**
     * Initilizes the pane to display lbls, textfields etc.
     * @param pane
     */
    public void initContent(GridPane pane) {
        //pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblName = new Label("Navn:");
        pane.add(lblName, 0, 0);
        Label lblDato = new Label("Planlagt dato");
        pane.add(lblDato, 0, 1);
        Label lblTidspunkt = new Label("Planlagt tidspunkt");
        pane.add(lblTidspunkt, 0, 2);
        Label lblDescription = new Label("Beskrivelse:");
        pane.add(lblDescription, 0, 3);

        pane.add(txfName, 1, 0, 2, 1);

        pane.add(datePicker, 1, 1, 2, 1);

        pane.add(txfTidspunkt, 1, 2,2,1);

        pane.add(txaDescription, 1, 3, 2, 1);
        txaDescription.setPrefWidth(100);


        //-------------------------------------------


        //Adds a Vbox to hold OrderLines
        orderLineView = new VBox();
        orderLineView.setBackground(Background.fill(Color.WHITE));
        orderLineView.setBorder(Border.stroke(Color.BLACK));
        orderLineView.setPrefWidth(400);
        pane.add(orderLineView, 7, 0, 3, 3);

        //Adds field and label for calculated total of the Order
        Label lblOrderTotal = new Label("Total:");
        lblOrderTotal.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        pane.add(lblOrderTotal, 8, 4);

        vbxOrderTotal = new VBox();
        vbxOrderTotal.setPrefWidth(75);
        vbxOrderTotal.setBackground(Background.EMPTY);
        vbxOrderTotal.setAlignment(Pos.BASELINE_RIGHT);
        pane.add(vbxOrderTotal, 9, 4);

        //Add field for percent Discount
        Label lblrabat = new Label("Rabat: ");
        lblrabat.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        pane.add(lblrabat, 8, 5);

        txfPercentDiscount = new TextField("" + 0);
        txfPercentDiscount.setPrefWidth(75);
        txfPercentDiscount.setAlignment(Pos.BASELINE_RIGHT);
        ChangeListener<String> percentListener = (observable, oldV, newV) -> this.updateDiscount();
        txfPercentDiscount.textProperty().addListener(percentListener);

        Label lblPercent = new Label("%");

        HBox hbxPercentDiscount = new HBox(txfPercentDiscount, lblPercent);
        hbxPercentDiscount.setAlignment(Pos.BASELINE_RIGHT);
        pane.add(hbxPercentDiscount, 9, 5);

        //Add field for a fixed total price
        Label lblFixedPrice = new Label("Aftalt Total: ");
        lblFixedPrice.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        pane.add(lblFixedPrice, 8, 6);

        txfFixedTotal = new TextField();
        txfFixedTotal.setPrefWidth(75);
        txfFixedTotal.setAlignment(Pos.BASELINE_RIGHT);
        ChangeListener<String> fixedTotalListener = (observable, oldV, newV) -> this.updateFixedPrice();
        txfFixedTotal.textProperty().addListener(fixedTotalListener);

        HBox hbxFixedTotal = new HBox(txfFixedTotal);
        hbxFixedTotal.setAlignment(Pos.BASELINE_RIGHT);
        hbxFixedTotal.setPadding(new Insets(4, 8, 5, 0));
        hbxFixedTotal.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, new BorderWidths(0.0, 0.0, 0.5, 0.0))));
        pane.add(hbxFixedTotal, 9, 6);

        //Add field for the final price
        Label lblFinal = new Label("Endelig Total: ");
        lblFinal.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        pane.add(lblFinal, 8, 7);

        vbxFinalPrice = new VBox();
        vbxFinalPrice.setPrefWidth(75);
        vbxFinalPrice.setBackground(Background.EMPTY);
        vbxFinalPrice.setAlignment(Pos.BASELINE_RIGHT);

        HBox hbxFinalPrice = new HBox(vbxFinalPrice);
        hbxFinalPrice.setAlignment(Pos.TOP_RIGHT);
        hbxFinalPrice.setPadding(new Insets(32, 8, 0, 0));
        pane.add(hbxFinalPrice, 9, 7);


        //Add confirmation button for order
        Button btnConfirmOrder = new Button("Opret Udlejning");
        btnConfirmOrder.setMaxWidth(Double.MAX_VALUE);
        btnConfirmOrder.setOnAction(event -> oKAction());
        pane.add(btnConfirmOrder, 7, 8, 3, 1);

        // Adds a cancel button
        Button btnCancel = new Button("Afbryd");
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setOnAction(event -> cancelAction());
        pane.add(btnCancel, 6, 8, 3, 1);


        //Updates all controls
        updateControls();
    }



    //Updates controls
    public void updateControls() {
        //TODO
    }

    public void updateOrder() {
        //TODO
    }

        /**
         * Method to update discount on the order
         */
    private void updateDiscount() {
        try {
            if (txfPercentDiscount.getText().isBlank()) {
                txfPercentDiscount.setText("" + 0);
            }

            double discount = Double.parseDouble(txfPercentDiscount.getText().trim());

            if (discount >= 0.0 && discount <= 100.0) {
                controller.setDiscountForOrder(order, discount);
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

    /**
     * Method to update the fixedprice if the fixed price is set
     */
    private void updateFixedPrice() {
        try {
            if (!txfFixedTotal.getText().isBlank()) {
                double value = Double.parseDouble(txfFixedTotal.getText());
                if (Double.parseDouble(txfFixedTotal.getText()) < 0) {
                    throw new NumberFormatException("Den indtastede værdi kan ikke være mindre end 0");
                }
                controller.setFixedPriceForOrder(order, value, Unit.DKK);
            } else {
                controller.setFixedPriceForOrder(order, -1.0, null);
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

    public void oKAction() {
        String name = "";
        String description = "";
        if (!txfName.getText().isBlank()) {
            name = txfName.getText().trim();

            if (!txaDescription.getText().isBlank()) {
                description = txaDescription.getText().trim();
            }

            Tour tour = controller.createTour(LocalDate.from(datePicker.getValue()), LocalTime.parse(txfTidspunkt.getText().trim()));
            for(OrderLine order : order.getOrderLines()){
                tour.addOrderLine(order);
            }
            this.close();

        } else {
            Alert nameAlert = new Alert(Alert.AlertType.ERROR);
            nameAlert.setTitle("Manglende navn!");
            nameAlert.setHeaderText("En rundvisning skal have et navn før det kan oprettes!");
            nameAlert.showAndWait();
        }
    }

    /**
     * Closes down the window
     */
    private void cancelAction() {
        this.close();
    }

}
