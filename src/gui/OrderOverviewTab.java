package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.controller.OrderController;
import model.modelklasser.Order;
import storage.Storage;

import java.util.ArrayList;

public class OrderOverviewTab extends GridPane {

    private OrderControllerInterface orderController;
    private DatePicker startDate, endDate;
    private ListView<Order> lvwOrders;
    private VBox vboxOrderInfo, vboxDateNOrders;
    private HBox hboxDates;
    private TextField txfOrderName, txfOrderDescription;

    public OrderOverviewTab(){
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.TOP_CENTER);

        orderController = new OrderController(Storage.getStorage());

        // HBox for the datepickers
        hboxDates = new HBox();

        startDate = new DatePicker();

        endDate = new DatePicker();

        hboxDates.getChildren().setAll(startDate,endDate);


        // Listview with all orders in the defined period
        lvwOrders = new ListView<>();

        // VBox for dates and orders
        vboxDateNOrders = new VBox();
        vboxDateNOrders.getChildren().setAll(hboxDates,lvwOrders);


        // Textfields and listview for displaying order info and orderlines
        Order selectedOrder = lvwOrders.getSelectionModel().getSelectedItem();
        txfOrderName = new TextField("" + selectedOrder);


        updateControls();
    }



    public void updateOrderList(){
        ArrayList<Order> ordersInPeriod = new ArrayList<>();
        for (Order order : orderController.getOrders()){
            if (order.getEndDate().isEqual(startDate.getValue()) || order.getEndDate().isAfter(startDate.getValue()) && order.getEndDate().isEqual(endDate.getValue()) || order.getEndDate().isBefore(endDate.getValue())){
                ordersInPeriod.add(order);
            }
        }
        lvwOrders.getItems().setAll(ordersInPeriod);
    }

    public void updateOrderInfo(){

    }


    public void updateControls(){
        updateOrderList();

    }
}
