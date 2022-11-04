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
import model.modelklasser.Tour;
import storage.Storage;

import java.time.LocalTime;

public class TourTab extends GridPane {
    private final ListView<Tour> lvwTours = new ListView<>();
    private final ListView<Tour> lvwActiveTours = new ListView<>();
    private SplitPane splitPane = new SplitPane();
    private OrderControllerInterface controller = OrderController.getOrderController(Storage.getStorage());
    private Order order = controller.createOrder();
    private final TextField txfName = new TextField();
    private final TextArea txaDescription = new TextArea();
    private final TextField timePicker = new TextField();

    /**
     * Tour tab to control all the tours
     */
public TourTab(){
    this.setPadding(new Insets(20));
    this.setHgap(10);
    this.setVgap(10);

    // Adding a splitpane to the pane
    this.add(splitPane, 0, 1);
    // All the split in the splitpane left, mid and right
    VBox leftControl = new VBox(new Label("Bestilte Rundvisninger"));
    VBox midControl = new VBox(new Label("afsluttede Rundvisninger"));
    VBox rightControl = new VBox(new Label("Rundvisningsinformation"));

    // To control the buttons
    HBox btnBox = new HBox();
    btnBox.setSpacing(10);
    this.add(btnBox, 0, 0,1,1);

    // List of active tours
    lvwActiveTours.setPrefWidth(100);
    lvwActiveTours.setPrefHeight(300);
    lvwActiveTours.getItems().setAll();
    ChangeListener<Order> tourChangeListener = (ov, o, v) -> this.updateFieldsInfo();
    lvwActiveTours.getSelectionModel().selectedItemProperty().addListener(tourChangeListener);

    // List of closed tours
    lvwActiveTours.setPrefWidth(100);
    lvwActiveTours.setPrefHeight(300);
    lvwActiveTours.getItems().setAll();
    midControl.getChildren().add(lvwTours);
    midControl.setMinWidth(200);

    // Button to create a new rundvisning
    Button btnTour = new Button("Opret rundtur");
    btnTour.setOnAction(event -> this.createTour());
    btnBox.getChildren().add(btnTour);

    // Button to finish a rundvisning
    Button btnFinishTour = new Button("Afslut rundvisning");
    btnFinishTour.setOnAction(event -> this.finishTour());
    btnBox.getChildren().add(btnFinishTour);

    // Textfields and area to hold the information
    txfName.setEditable(false);
    txaDescription.setEditable(false);
    timePicker.setEditable(false);

    // Adding all textfields and area to the right split control pane
    rightControl.getChildren().add(txfName);
    rightControl.getChildren().add(txaDescription);
    rightControl.getChildren().add(timePicker);

    // Adding all active rentals to leftcontrol split pane
    leftControl.getChildren().add(lvwActiveTours);
    splitPane.getItems().addAll(leftControl, midControl, rightControl);

    // Updatescontrols
    updateControls();
}
//-----------------------------------------------
    /**
     * Updates fields in right(??) control pane
     */
private void updateFieldsInfo() {
    String name = lvwActiveTours.getSelectionModel().getSelectedItem().getName();
    String description = lvwActiveTours.getSelectionModel().getSelectedItem().getDescription();
    LocalTime time = lvwActiveTours.getSelectionModel().getSelectedItem().getTime();
    txfName.setText(name);
    txaDescription.setText(description + lvwActiveTours.getSelectionModel().getSelectedItem().getOrderLines());
    timePicker.setText(String.valueOf(time));
}

private void finishTour(){
    Stage stage = new Stage(StageStyle.UTILITY);
    //TODO MAGNUS> E>R HER
    updateControls();
}

private void createTour(){
    Stage stage = new Stage(StageStyle.UTILITY);
    CreateTourWindow tourWindow = new CreateTourWindow("Ny rundvisning", stage);
    tourWindow.showAndWait();
    updateControls();
}

    /**
     * Updates the listview in the pane
     */
public void updateControls(){
    lvwActiveTours.getItems().setAll(controller.getTours());
}

}
