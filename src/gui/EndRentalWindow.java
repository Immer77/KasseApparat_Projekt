package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.OrderController;
import model.modelklasser.OrderLine;
import model.modelklasser.PaymentMethod;
import model.modelklasser.Price;
import model.modelklasser.Rental;
import storage.Storage;

public class EndRentalWindow extends Stage {

    private OrderControllerInterface orderController = OrderController.getOrderController(Storage.getStorage());
    private Rental rental;
    private ChoiceBox<PaymentMethod> chPaymentMethod;
    private VBox orderLineView, unusedOrderLineView, rentalInfoVBox;
    private SplitPane splitPane = new SplitPane();
    private ListView<OrderLine> lvwRentalOrderlines = new ListView<>();
    private ListView<OrderLine> lvwUnusedProducts = new ListView<>();
    private HBox buttons;
    private Button btnAddToUnused;
    private TextField txfName, txfStartDate;
    private TextArea txaDescription;
    private DatePicker endDatePicker;

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

        //adds vbox for rental info
        rentalInfoVBox = new VBox();
        rentalInfoVBox.setPrefWidth(200);

        //adds labels, textfields, textarea and datepicker for rentalinfo
        VBox nameVBox = new VBox();
        Label lblName = new Label("Name");
        txfName =  new TextField();
        txfName.setEditable(false);
        txfName.appendText(rental.getName());
        nameVBox.getChildren().addAll(lblName,txfName);

        VBox descriptionVBox = new VBox();
        Label lblDescription = new Label("Description");
        txaDescription = new TextArea();
        txaDescription.setEditable(false);
        txaDescription.appendText(rental.getDescription());
        descriptionVBox.getChildren().setAll(lblDescription,txaDescription);

        VBox startdateVBox = new VBox();
        Label lblStartDate = new Label("Start dato");
        txfStartDate = new TextField(rental.getStartDate().toString());
        txfStartDate.setEditable(false);
        startdateVBox.getChildren().addAll(lblStartDate,txfStartDate);

        VBox endDateVBox = new VBox();
        endDatePicker = new DatePicker();
        endDatePicker.setValue(rental.getEndDate());
        Label lblEndDate = new Label("Slut dato");
        endDateVBox.getChildren().addAll(lblEndDate,endDatePicker);

        HBox dateHBox = new HBox();
        dateHBox.getChildren().addAll(startdateVBox,endDateVBox);

        rentalInfoVBox.getChildren().addAll(nameVBox,descriptionVBox,dateHBox);
        pane.add(rentalInfoVBox,0,0);

        //Adds SplitPane to hold OrderLines for used and unused items
        pane.add(splitPane,1,0);

        //Adds a Vbox to hold all OrderLines
        orderLineView = new VBox(new Label("Alle udlejede produkter"));
        orderLineView.setPrefWidth(200);
        for (OrderLine ol : rental.getOrderLines()){
            lvwRentalOrderlines.getItems().setAll(ol);
        }
        orderLineView.getChildren().add(lvwRentalOrderlines);


        //adds button in splitpane
        btnAddToUnused = new Button(">");
        btnAddToUnused.setMinWidth(50);

        //adds a VBox to hold unused products
        unusedOrderLineView = new VBox(new Label("Produkter som er ubrugte"));
        unusedOrderLineView.setPrefWidth(200);
        unusedOrderLineView.getChildren().add(lvwUnusedProducts);

        //adds vboxs to splitpane
        splitPane.getItems().addAll(orderLineView,btnAddToUnused,unusedOrderLineView);

        //Confirm and cancel buttons with vbox to hold them
        buttons = new HBox();
        pane.add(buttons,1,1);
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

    private void removeUnusedProductFromRental(Price price) {
        //Otherwise adds product to order with amount of 1
        if (rental.getOrderLines().contains(price)) {
            orderController.createOrderLineForOrder(rental, 1, price);
        }

        //then displays orderlines in OrderLineView
        updateRental();
    }
}