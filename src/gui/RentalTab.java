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

public class RentalTab extends GridPane {
    private final ListView<Rental> lvwActiveRentals = new ListView<>();
    private final ListView<Rental> lvwRentals = new ListView<>();
    private OrderControllerInterface controller = OrderController.getOrderController(Storage.getStorage());
    private SplitPane splitPane = new SplitPane();
    private final TextField txfName = new TextField();
    private final TextArea txaDescription = new TextArea();
    private final TextField datePicker = new TextField();



    public RentalTab(){
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);


        this.add(splitPane,0,1);
        VBox leftControl  = new VBox(new Label("Aktive Udlejninger"));
        VBox midControl = new VBox(new Label("Færdige udlejninger"));
        VBox rightControl = new VBox(new Label("Udlejningsinformation"));
//        VBox rentalField = new VBox();
        HBox btnBox = new HBox();
        btnBox.setSpacing(10);
        this.add(btnBox,0,0);

        lvwActiveRentals.setPrefWidth(200);
        lvwActiveRentals.setPrefHeight(300);
        lvwActiveRentals.getItems().setAll();
        ChangeListener<Order> rentalChangeListener = (ov, o, v) -> this.updateFieldsInfo();
        lvwActiveRentals.getSelectionModel().selectedItemProperty().addListener(rentalChangeListener);


        lvwRentals.setPrefWidth(200);
        lvwRentals.setPrefHeight(300);
        lvwRentals.getItems().setAll();
        midControl.getChildren().add(lvwRentals);

        Button btnRental = new Button("Opret udlejning");
        btnRental.setOnAction(event -> this.createRental());
        btnBox.getChildren().add(btnRental);

        Button btnFinishRental = new Button("Afslut udlejning");
        btnFinishRental.setOnAction(event -> this.finishRental());
        btnBox.getChildren().add(btnFinishRental);

        txfName.setEditable(false);
        txaDescription.setEditable(false);
        datePicker.setEditable(false);
//        rentalField.getChildren().add(txfName);
//        rentalField.getChildren().add(txaDescription);
//        rentalField.getChildren().add(datePicker);
        rightControl.getChildren().add(txfName);
        rightControl.getChildren().add(txaDescription);
        rightControl.getChildren().add(datePicker);


        leftControl.getChildren().add(lvwActiveRentals);
        splitPane.getItems().addAll(leftControl, midControl, rightControl);


        updateControls();

    }

    private void updateFieldsInfo() {
        String name = lvwActiveRentals.getSelectionModel().getSelectedItem().getName();
        String description = lvwActiveRentals.getSelectionModel().getSelectedItem().getDescription();
        txfName.setText(name);
        txaDescription.setText(description);
        datePicker.setText(String.valueOf(lvwRentals.getSelectionModel().getSelectedItem().getStartDate()));


    }

    private void finishRental() {
    }

    private void createRental() {
        Stage stage = new Stage(StageStyle.UTILITY);
        CreateRentalWindow rentalWindow = new CreateRentalWindow("Ny udlejning", stage);
        rentalWindow.showAndWait();

        updateControls();
    }

    public void updateControls() {
        lvwActiveRentals.getItems().setAll(controller.getRentals());
    }

}
