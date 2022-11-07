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

import java.awt.*;
import java.time.LocalDate;

public class RentalTab extends GridPane {
    // Field variables
    private final ListView<Rental> lvwActiveRentals = new ListView<>();
    private final ListView<Rental> lvwRentals = new ListView<>();
    private OrderControllerInterface controller = OrderController.getOrderController(Storage.getStorage());
    private SplitPane splitPane = new SplitPane();
    private final TextField txfName = new TextField();
    private final TextArea txaDescription = new TextArea();
    private final TextField datePicker = new TextField();
    private Order order = controller.createOrder();


    /**
     * Rental tab to control all the rentals
     */
    public RentalTab() {
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
        lvwRentals.getItems().setAll();
        midControl.getChildren().add(lvwRentals);
        midControl.setMinWidth(200);

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
        datePicker.setEditable(false);

        // Adding all textfields and area to the right split control pane
        rightControl.getChildren().add(txfName);
        rightControl.getChildren().add(txaDescription);
        rightControl.getChildren().add(datePicker);


        // Adding all active rentals to leftcontrol split pane
        leftControl.getChildren().add(lvwActiveRentals);
        splitPane.getItems().addAll(leftControl, midControl, rightControl);


        // Updatescontrols
        updateControls();

    }

    /**
     * Updates fields in right control pane
     */
    private void updateFieldsInfo() {
        try {
            String name = lvwActiveRentals.getSelectionModel().getSelectedItem().getName();
            String description = lvwActiveRentals.getSelectionModel().getSelectedItem().getDescription();
            LocalDate date = lvwActiveRentals.getSelectionModel().getSelectedItem().getEndDate();
            txfName.setText(name);

            double result = calculateFinalPrice();

            txaDescription.setText(description + lvwActiveRentals.getSelectionModel().getSelectedItem().getOrderLines() + "\nTotal:" + result);
            datePicker.setText(String.valueOf(date));

        }catch (NullPointerException ne){
            throw new RuntimeException("Fejler med navn" ,ne);
        }

    }


    private double calculateFinalPrice() {
        double finalPrice = 0.0;
        for (OrderLine orderLine : lvwActiveRentals.getSelectionModel().getSelectedItem().getOrderLines()) {
            if (lvwActiveRentals.getSelectionModel().getSelectedItem().getFixedPrice() != 0) {
                finalPrice = lvwActiveRentals.getSelectionModel().getSelectedItem().getFixedPrice();
                break;
            }
            if (lvwActiveRentals.getSelectionModel().getSelectedItem().getPercentDiscount() != 0) {
                finalPrice += orderLine.getPrice().getValue();
            } else {
                finalPrice += orderLine.getPrice().getValue();
            }

        }
        double percentageMultiplier = 1.0;
        double percentageDiscount = lvwActiveRentals.getSelectionModel().getSelectedItem().getPercentDiscount();

        if (percentageDiscount >= 0 && percentageDiscount <= 100) {
            percentageMultiplier = (100 - percentageDiscount) / 100;
        } else {
            throw new NumberFormatException("Procentrabatten skal være et tal mellem 0 og 100");
        }
        finalPrice *= percentageMultiplier;

        return finalPrice;
    }

    /**
     * Method to open endrentalwindow where one ends the selectedf rental
     */
    private void finishRental() {
        Stage stage = new Stage(StageStyle.UTILITY);
        EndRentalWindow endRentalWindow = new EndRentalWindow("Afslut udlejning", stage, lvwActiveRentals.getSelectionModel().getSelectedItem());
        endRentalWindow.showAndWait();

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
        lvwActiveRentals.getItems().setAll(controller.getRentals());

    }

}
