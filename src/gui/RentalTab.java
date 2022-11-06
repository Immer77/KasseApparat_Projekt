package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.OrderController;
import model.modelklasser.Order;
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
    private final TextField datePicker;
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
        datePicker = new TextField();
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
        this.add(btnBox, 0, 0,1,1);

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
        String name = lvwActiveRentals.getSelectionModel().getSelectedItem().getName();
        String description = lvwActiveRentals.getSelectionModel().getSelectedItem().getDescription();
        LocalDate date = lvwActiveRentals.getSelectionModel().getSelectedItem().getEndDate();
        txfName.setText(name);
        txaDescription.setText(description + lvwActiveRentals.getSelectionModel().getSelectedItem().getOrderLines());
        datePicker.setText(String.valueOf(date));
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
        lvwActiveRentals.getItems().setAll(controller.getActiveRentals());
        lvwRentals.getItems().setAll(controller.getDoneRentals());

    }

}
