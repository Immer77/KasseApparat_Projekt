package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.controller.OrderController;
import model.modelklasser.Order;
import model.modelklasser.OrderLine;
import model.modelklasser.Rental;
import model.modelklasser.Tour;
import storage.Storage;

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
        Label lblOrders = new Label("Ordrer");
        Label lblRentals = new Label("Udlejninger");
        Label lblTours = new Label("Rundvisninger");

        // VBox for dates and orders
        vboxDateNOrders = new VBox();
        vboxDateNOrders.getChildren().setAll(hboxDates,lblOrders,lvwOrders,lblRentals,lvwRental,lblTours,lvwTour);

        // Textfields and listview for displaying order info and orderlines
        Label lblOrderName = new Label("Order");
        txfOrderName = new TextField();
        txfOrderName.setEditable(false);

        Label lblDescription = new Label("Beskrivelse");
        txaOrderDescription = new TextArea();
        txaOrderDescription.setEditable(false);

        Label lblOrderLines = new Label("Orderlinjer");
        lvwOrderLines = new ListView<>();
        lvwOrderLines.setEditable(false);

        vboxOrderInfo = new VBox();
        vboxOrderInfo.getChildren().setAll(lblOrderName,txfOrderName,lblDescription,txaOrderDescription,lblOrderLines,lvwOrderLines);

        hboxEntireSheit = new HBox();
        hboxEntireSheit.getChildren().setAll(vboxDateNOrders,vboxOrderInfo);
        hboxEntireSheit.setMaxHeight(450);
        hboxEntireSheit.setSpacing(20);
        this.add(hboxEntireSheit,0,0);

        ChangeListener<Order> orderChangeListener = (ov,o,v) -> this.updateOrderInfo();
        lvwOrders.getSelectionModel().selectedItemProperty().addListener(orderChangeListener);
        lvwRental.getSelectionModel().selectedItemProperty().addListener(orderChangeListener);
        lvwTour.getSelectionModel().selectedItemProperty().addListener(orderChangeListener);

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
        if (lvwOrders.isFocused()){
            clearFields();

            selectedOrder = lvwOrders.getSelectionModel().getSelectedItem();
            txfOrderName.setText(selectedOrder.getOrderNumber() + "");
            txaOrderDescription.setText("");
            lvwOrderLines.getItems().addAll(selectedOrder.getOrderLines());

        } else if(lvwRental.isFocused()){
            clearFields();

            selectedOrder = lvwRental.getSelectionModel().getSelectedItem();
            txfOrderName.setText(selectedOrder.getOrderNumber() + "");
            txaOrderDescription.setText(((Rental) selectedOrder).getDescription());
            lvwOrderLines.getItems().addAll(selectedOrder.getOrderLines());

        } else if (lvwTour.isFocused()){
            clearFields();
            lvwTour.getFocusModel();

            selectedOrder = lvwTour.getSelectionModel().getSelectedItem();
            txfOrderName.setText(selectedOrder.getOrderNumber() + "");
            txaOrderDescription.setText(((Tour) selectedOrder).getDescription());
            lvwOrderLines.getItems().addAll(selectedOrder.getOrderLines());

        } else {
            clearFields();
        }
    }


    public void updateControls(){
        updateOrderInfo();
    }

    public void clearFields(){
        txfOrderName.clear();
        txaOrderDescription.clear();
        lvwOrderLines.getItems().clear();
    }
}
