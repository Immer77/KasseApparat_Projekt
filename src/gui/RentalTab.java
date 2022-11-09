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
import model.modelklasser.*;
import storage.Storage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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
    private TextArea txaAllRentalsMade = new TextArea();


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
        lvwActiveRentals.setPrefHeight(500);
        lvwActiveRentals.getItems().setAll();
        ChangeListener<Order> rentalChangeListener = (ov, o, v) -> this.updateOpenRentalInfo();
        lvwActiveRentals.getSelectionModel().selectedItemProperty().addListener(rentalChangeListener);


        // List of closed rentals
        lvwRentals.setPrefWidth(100);
        lvwRentals.setPrefHeight(500);
        lvwRentals.getItems().setAll();
        ChangeListener<Order> rentalListener = (ov, o, v) -> this.updateFinishedRentalInfo();
        lvwRentals.getSelectionModel().selectedItemProperty().addListener(rentalListener);

        midControl.getChildren().add(lvwRentals);
        midControl.setMinWidth(250);

        txaAllRentalsMade.setEditable(false);
        txaAllRentalsMade.getPrefRowCount();

        // Button to create a new rental
        Button btnRental = new Button("Ny udlejning");
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
        Label lblname = new Label("Navn på udlejning");
        rightControl.getChildren().add(lblname);
        rightControl.getChildren().add(txfName);
        Label lblDescription = new Label("Beskrivelse for udlejning");
        rightControl.getChildren().add(lblDescription);
        rightControl.getChildren().add(txaDescription);
        Label lblDate = new Label("Date");
        rightControl.getChildren().add(lblDate);
        rightControl.getChildren().add(txfDatePicker);
        Label lblProductrented = new Label("----------------------------------------------------------\nAlle udlejede produkter");
        rightControl.getChildren().add(lblProductrented);
        rightControl.getChildren().add(txaAllRentalsMade);


        // Adding all active rentals to leftcontrol split pane
        leftControl.getChildren().add(lvwActiveRentals);
        splitPane.getItems().addAll(leftControl, midControl, rightControl);


        // Updatescontrols
        updateControls();
    }

    /**
     * Method to update the textare with the sum of all current rentals
     */

    public void updateSumOfRentedProducts() {

        HashMap<Product, Integer> productsAndAmounts = new HashMap<>();
        for (Order order : controller.getActiveRentals()) {
            for (OrderLine orderLine : order.getOrderLines()) {
                Product product = orderLine.getPrice().getProduct();
                if (productsAndAmounts.containsKey(product)) {
                    productsAndAmounts.merge(product, orderLine.getAmount(), Integer::sum);
                } else {
                    productsAndAmounts.put(orderLine.getPrice().getProduct(), orderLine.getAmount());
                }


            }
        }

        StringBuilder sb = new StringBuilder();
        Set entryset = productsAndAmounts.entrySet();
        Iterator iterator = entryset.iterator();

        while (iterator.hasNext()) {
            String[] linedivider = iterator.next().toString().split("=");

            sb.append("Produkt: " + linedivider[0] + " Antal: " + linedivider[1] + "\n");

        }
        txaAllRentalsMade.setText(String.valueOf(sb));

    }

    /**
     * Updates fields in right control pane
     */
//    private void updateFieldsInfo() {
//
//        try {
//            if (lvwActiveRentals.getSelectionModel().getSelectedItem() != null) {
//                clearTextFields();
//
//
//                txfName.setText(lvwActiveRentals.getSelectionModel().getSelectedItem().getName());
//                txaDescription.setText(lvwActiveRentals.getSelectionModel().getSelectedItem().getDescription() + lvwActiveRentals.getSelectionModel().getSelectedItem().getOrderLines() + "\nTotal Pris for Udlejning: " + calculateFinalPrice());
//                txfDatePicker.setText(String.valueOf(lvwActiveRentals.getSelectionModel().getSelectedItem().getEndDate()));
//            }
//            else if (lvwRentals.getSelectionModel().getSelectedItem() != null) {
//                clearTextFields();
//
//                txfName.setText(lvwRentals.getSelectionModel().getSelectedItem().getName());
//                txaDescription.setText(lvwRentals.getSelectionModel().getSelectedItem().getDescription() + lvwRentals.getSelectionModel().getSelectedItem().getOrderLines() + "\nTotal Pris for Udlejning: " + calculateFinalPrice());
//                txfDatePicker.setText(String.valueOf(lvwRentals.getSelectionModel().getSelectedItem().getEndDate()));
//            }
//
//        } catch (NullPointerException ne) {
//            System.out.println("Systemet blev lukket ned uden at foretage ændringer");
//        }
//    }


    private void updateFinishedRentalInfo(){
        Rental selectedRental;
        clearTextFields();

        selectedRental = lvwRentals.getSelectionModel().getSelectedItem();
        txfName.setText(selectedRental.getName());
        txaDescription.setText(selectedRental.getDescription() + selectedRental.getOrderLines() + "\nTotal Pris for Udlejning: " + calculateFinalPrice());
        txfDatePicker.setText(String.valueOf(selectedRental.getEndDate()));
    }

    private void updateOpenRentalInfo(){
        Rental selectedRental;
        clearTextFields();

        selectedRental = lvwActiveRentals.getSelectionModel().getSelectedItem();
        txfName.setText(selectedRental.getName());
        txaDescription.setText(selectedRental.getDescription() + selectedRental.getOrderLines() + "\nTotal Pris for Udlejning: " + calculateFinalPrice());
        txfDatePicker.setText(String.valueOf(selectedRental.getEndDate()));
    }

    /**
     * Method to clear textfields
     */
    private void clearTextFields() {
        txfName.clear();
        txaDescription.clear();
        txfDatePicker.clear();
    }


    /**
     * Calculates the final price for rental made
     * @return finalprice which contains all rentals orderlines price
     */
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
        } catch (NullPointerException ne) {
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
        updateSumOfRentedProducts();


    }

}
