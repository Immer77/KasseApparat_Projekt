package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.OrderController;
import model.modelklasser.Order;
import model.modelklasser.OrderLine;
import model.modelklasser.Rental;
import storage.Storage;

import java.time.LocalDate;

public class RentalTab extends GridPane {
    // Field variables
    private final ListView<Rental> lvwActiveRentals;
    private final ListView<Rental> lvwRentals;
    private OrderControllerInterface controller;
    private SplitPane splitPane;
    private final TextField txfName;
    private final TextArea txaDescription;
    private final TextField txfDatePicker;
    private Order order;


    /**
     * Rental tab to control all the rentals
     */
    public RentalTab() {
        lvwActiveRentals = new ListView<>();
        lvwRentals = new ListView<>();
        controller = new OrderController(Storage.getStorage());
        splitPane = new SplitPane();
        txfName = new TextField();
        txaDescription = new TextArea();
        txfDatePicker = new TextField();
        order = controller.createOrder();

        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);




        // Adding a splitpane to the pane
        this.add(splitPane, 0, 1);
        // All the split in the splitpane left, mid and right
        VBox leftControl = new VBox(new Label("Aktive Udlejninger"));
        VBox midControl = new VBox(new Label("Færdige udlejninger"));
        VBox rightControl = new VBox(new Label("Udlejningsinformation"));

        // To control the buttons
        HBox btnBox = new HBox();
        btnBox.setSpacing(10);
        this.add(btnBox, 0, 0, 1, 1);

        // List of active rentals
        lvwActiveRentals.setPrefWidth(100);
        lvwActiveRentals.setPrefHeight(300);
        lvwActiveRentals.getItems().setAll();
        ChangeListener<Order> rentalChangeListener = (ov, o, v) -> this.updateFieldsInfo();
        lvwActiveRentals.getSelectionModel().selectedItemProperty().addListener(rentalChangeListener);


        // List of closed rentals
        lvwRentals.setPrefWidth(100);
        lvwRentals.setPrefHeight(300);
        lvwRentals.getItems().setAll(controller.getDoneRentals());
//        ChangeListener<Order> rentalListener = (ov, o, v) -> this.updateFieldsInfoForClosedRentals();
        lvwRentals.getSelectionModel().selectedItemProperty().addListener(rentalChangeListener);

        midControl.getChildren().add(lvwRentals);
        midControl.setMinWidth(250);

        // Button to create a new rental
        Button btnRental = new Button("Opret udlejning");
        btnRental.setOnAction(event -> this.createRental());
        btnBox.getChildren().add(btnRental);

        // Button to finish a rental
        Button btnFinishRental = new Button("Afslut udlejning");
        btnFinishRental.setOnAction(event -> this.finishRental());
        btnBox.getChildren().add(btnFinishRental);

        // Textfields and area to hold the information
        txfName.setEditable(false);
        txaDescription.setEditable(false);
        txfDatePicker.setEditable(false);

        // Adding all textfields and area to the right split control pane
        rightControl.getChildren().add(txfName);
        rightControl.getChildren().add(txaDescription);
        rightControl.getChildren().add(txfDatePicker);


        // Adding all active rentals to leftcontrol split pane
        leftControl.getChildren().add(lvwActiveRentals);
        splitPane.getItems().addAll(leftControl, midControl, rightControl);


        // Updatescontrols
        updateControls();
    }

    private void updateFieldsInfoForClosedRentals() {
        try {
            lvwActiveRentals.getSelectionModel().select(-1);
            txfName.clear();
            txaDescription.clear();
            txfDatePicker.clear();
            String name = lvwRentals.getSelectionModel().getSelectedItem().getName();
            String description = lvwRentals.getSelectionModel().getSelectedItem().getDescription();
            LocalDate date = lvwRentals.getSelectionModel().getSelectedItem().getEndDate();

//            double result = calculateFinalPrice();

            txfName.setText(name);
            txaDescription.setText(description + lvwRentals.getSelectionModel().getSelectedItem().getOrderLines());
            txfDatePicker.setText(String.valueOf(date));

        }catch (NullPointerException ne){
            System.out.println("Systemet blev lukket ned uden at foretage ændringer");
        }
    }

    /**
     * Updates fields in right control pane
     */
    private void updateFieldsInfo() {
        try {
            if(!lvwActiveRentals.getSelectionModel().getSelectedItem().getName().isBlank()){
                txfName.clear();
                txaDescription.clear();
                txfDatePicker.clear();
                String name = lvwActiveRentals.getSelectionModel().getSelectedItem().getName();
                String description = lvwActiveRentals.getSelectionModel().getSelectedItem().getDescription();
                LocalDate date = lvwActiveRentals.getSelectionModel().getSelectedItem().getEndDate();
                txfName.setText(name);

                double result = calculateFinalPrice();

                txaDescription.setText(description + lvwActiveRentals.getSelectionModel().getSelectedItem().getOrderLines() + "\nTotal:" + result);
                txfDatePicker.setText(String.valueOf(date));
            }else{
                txfName.clear();
                txaDescription.clear();
                txfDatePicker.clear();
                String name = lvwRentals.getSelectionModel().getSelectedItem().getName();
                String description = lvwRentals.getSelectionModel().getSelectedItem().getDescription();
                LocalDate date = lvwRentals.getSelectionModel().getSelectedItem().getEndDate();

//            double result = calculateFinalPrice();

                txfName.setText(name);
                txaDescription.setText(description + lvwRentals.getSelectionModel().getSelectedItem().getOrderLines());
                txfDatePicker.setText(String.valueOf(date));
            }


        }catch (NullPointerException ne){
            System.out.println("Systemet blev lukket ned uden at foretage ændringer");
        }

    }


    private double calculateFinalPrice() {
        double finalPrice = 0.0;
        for (OrderLine orderLine : lvwActiveRentals.getSelectionModel().getSelectedItem().getOrderLines()) {
            if (lvwActiveRentals.getSelectionModel().getSelectedItem().getFixedPrice() != 0) {
                finalPrice = lvwActiveRentals.getSelectionModel().getSelectedItem().getFixedPrice();
                break;
            }
            finalPrice += orderLine.getAmount() * orderLine.getPrice().getValue();

        }

        double percentageDiscount = lvwActiveRentals.getSelectionModel().getSelectedItem().getPercentDiscount();
        finalPrice *= (100 - percentageDiscount) / 100;

        return finalPrice;
    }

    /**
     * Method to open endrentalwindow where one ends the selectedf rental
     */
    private void finishRental() {
        try {
            Stage stage = new Stage(StageStyle.UTILITY);
            EndRentalWindow endRentalWindow = new EndRentalWindow("Afslut udlejning", stage, lvwActiveRentals.getSelectionModel().getSelectedItem());
            endRentalWindow.showAndWait();
        }catch (NullPointerException ne){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Vælg en udlejning");
            alert.setHeaderText("Der skal vælges en udlejning");
            alert.show();
        }


        updateControls();
    }

    /**
     * Opens up a rental windows where its possible to create a rental
     */
    private void createRental() {
        Stage stage = new Stage(StageStyle.UTILITY);
        CreateRentalWindow rentalWindow = new CreateRentalWindow("Ny udlejning", stage);
        rentalWindow.showAndWait();


        updateControls();
    }

    /**
     * Updates the listview in the pane
     */
    public void updateControls() {
        lvwActiveRentals.getItems().setAll(controller.getActiveRentals());
        lvwRentals.getItems().setAll(controller.getDoneRentals());

    }

}
