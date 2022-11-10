package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.controller.OrderController;
import model.modelklasser.*;
import storage.Storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderOverviewTab extends GridPane {

    private OrderControllerInterface orderController;
    private DatePicker startDate, endDate;
    private ListView<Order> lvwOrders;
    private ListView<Rental> lvwRental;
    private ListView<Tour> lvwTour;
    private VBox vboxOrderInfo, vboxDateNOrders;
    private HBox hboxDates, hboxEntireSheit;
    private TextField txfOrderName;
    private TextArea txaOrderDescription;
    private ListView<OrderLine> lvwOrderLines;

    public OrderOverviewTab(){
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.TOP_CENTER);

        orderController = new OrderController(Storage.getStorage());

        // HBox for the datepickers
        hboxDates = new HBox();

        startDate = new DatePicker();
        startDate.setOnAction(event -> this.updateOrderList());
        startDate.setValue(LocalDate.now());

        endDate = new DatePicker();
        endDate.setOnAction(event -> this.updateOrderList());
        endDate.setValue(LocalDate.now());

        hboxDates.getChildren().setAll(startDate,endDate);


        // Listviews with all orders, rentals and tours in the defined period
        lvwOrders = new ListView<>();
        lvwOrders.setPrefHeight(150);
        lvwRental = new ListView<>();
        lvwRental.setPrefHeight(70);
        lvwTour = new ListView<>();
        lvwTour.setPrefHeight(70);

        // Labels for defining listviews
        Label lblOrders = new Label("Salgordre");
        Label lblRentals = new Label("Udlejninger");
        Label lblTours = new Label("Rundvisninger");

        //Button for printing orders in period
        Button btnPrintReceipt = new Button("Print periodeopgørelse");
        btnPrintReceipt.setPrefWidth(150);
        btnPrintReceipt.setOnAction(event -> printReceipt());

        // VBox for dates and orders
        vboxDateNOrders = new VBox();
        vboxDateNOrders.setSpacing(5);
        vboxDateNOrders.getChildren().setAll(hboxDates,lblOrders,lvwOrders,lblRentals,lvwRental,lblTours,lvwTour,btnPrintReceipt);

        // Textfields and listview for displaying order info and orderlines
        Label lblOrderName = new Label("Ordrenummer");
        txfOrderName = new TextField();
        txfOrderName.setEditable(false);

        Label lblDescription = new Label("Beskrivelse");
        txaOrderDescription = new TextArea();
        txaOrderDescription.setEditable(false);

        Label lblOrderLines = new Label("Orderlinjer");
        lvwOrderLines = new ListView<>();
        lvwOrderLines.setEditable(false);

        vboxOrderInfo = new VBox();
        vboxOrderInfo.setSpacing(5);
        vboxOrderInfo.getChildren().setAll(lblOrderName,txfOrderName,lblDescription,txaOrderDescription,lblOrderLines,lvwOrderLines);

        hboxEntireSheit = new HBox();
        hboxEntireSheit.getChildren().setAll(vboxDateNOrders,vboxOrderInfo);
        hboxEntireSheit.setMaxHeight(450);
        hboxEntireSheit.setSpacing(20);
        this.add(hboxEntireSheit,0,0);

        ChangeListener<Order> orderChangeListener = (ov,o,v) -> this.updateOrderInfo();
        lvwOrders.getSelectionModel().selectedItemProperty().addListener(orderChangeListener);
        ChangeListener<Order> rentalChangeListener = (ov,o,v) -> this.updateRentalInfo();
        lvwRental.getSelectionModel().selectedItemProperty().addListener(rentalChangeListener);
        ChangeListener<Order> tourChangeListener = (ov,o,v) -> this.updateTourInfo();
        lvwTour.getSelectionModel().selectedItemProperty().addListener(tourChangeListener);



        updateControls();
        updateOrderList();
    }



    public void updateOrderList(){
        ArrayList<Order> ordersInPeriod = new ArrayList<>();
        ArrayList<Rental> rentalsInPeriod = new ArrayList<>();
        ArrayList<Tour> toursInPeriod = new ArrayList<>();
        for (Order order : orderController.getOrders()){
            if (order.getEndDate().isEqual(startDate.getValue()) || order.getEndDate().isAfter(startDate.getValue()) && order.getEndDate().isEqual(endDate.getValue()) || order.getEndDate().isBefore(endDate.getValue())){
                if (order instanceof Rental){
                    rentalsInPeriod.add((Rental) order);
                } else if (order instanceof Tour) {
                    toursInPeriod.add((Tour) order);
                } else {
                    ordersInPeriod.add(order);
                }
            }
        }
        lvwRental.getItems().setAll(rentalsInPeriod);
        lvwTour.getItems().setAll(toursInPeriod);
        lvwOrders.getItems().setAll(ordersInPeriod);
    }

    public void updateOrderInfo(){
        Order selectedOrder;
        clearFields();
        selectedOrder = lvwOrders.getSelectionModel().getSelectedItem();
        txfOrderName.setText(selectedOrder.getOrderNumber() + "");
        txaOrderDescription.setText("");
        lvwOrderLines.getItems().addAll(selectedOrder.getOrderLines());
    }

    public void updateRentalInfo(){
        Order selectedOrder;
        clearFields();
        selectedOrder = lvwRental.getSelectionModel().getSelectedItem();
        txfOrderName.setText(selectedOrder.getOrderNumber() + "");
        txaOrderDescription.setText(((Rental) selectedOrder).getDescription());
        lvwOrderLines.getItems().addAll(selectedOrder.getOrderLines());
    }

    public void updateTourInfo(){
        Order selectedOrder;
        clearFields();
        selectedOrder = lvwTour.getSelectionModel().getSelectedItem();
        txfOrderName.setText(selectedOrder.getOrderNumber() + "");
        txaOrderDescription.setText(((Tour) selectedOrder).getDescription());
        lvwOrderLines.getItems().addAll(selectedOrder.getOrderLines());

    }

    private void printReceipt(){
        String currentWorkingPath = System.getProperty("user.dir");
        String fileName = "receipt.txt";
        String path = currentWorkingPath  + File.separator+ fileName;
        File file = new File(path);
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (Order order : orderController.getOrders()){
                if (order.getEndDate().isEqual(startDate.getValue()) || order.getEndDate().isAfter(startDate.getValue()) && order.getEndDate().isEqual(endDate.getValue()) || order.getEndDate().isBefore(endDate.getValue())){
                    String salgstype = "salg";
                    if (order instanceof Rental){
                        salgstype = "Udlejning";
                    } else if (order instanceof Tour){
                        salgstype = "Rundvisning";
                    }
                    String betaling = "";
                    if (order.getFixedPrice() > 0){
                        betaling = "Aftalt pris: " + order.getFixedPrice() + "\n";
                    } else if (order.getPercentDiscount() > 0){
                        betaling = "Rabat givet: " + order.getPercentDiscount() + "%\nPris med rabat: " + (order.calculateSumPriceForUnit(Unit.DKK)-(order.calculateSumPriceForUnit(Unit.DKK)*order.getPercentDiscount()/100)) + "\n" + "\n";
                    } else {
                        betaling = "Total pris: " + order.calculateSumPriceForUnit(Unit.DKK) + "\n";
                    }
                    fileWriter.write("type af salg: " + salgstype + "\nOrdernummer: " + order.getOrderNumber() + "\n" + order.getOrderLines() + "\n" + betaling + "\n");
                    Alert fileMessage = new Alert(Alert.AlertType.INFORMATION);
                    fileMessage.setHeaderText("Periodeopgørelse kan findes i projekt mappen");
                    fileMessage.showAndWait();
                }
            }
            fileWriter.close();
        } catch (IOException ioe){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Fejl!");
            alert.setContentText("File path er forkert!");
            alert.showAndWait();
        } finally {

        }

    }

    public void updateControls(){
        updateOrderList();
    }

    public void clearFields(){
        txfOrderName.clear();
        txaOrderDescription.clear();
        lvwOrderLines.getItems().clear();
    }
}
