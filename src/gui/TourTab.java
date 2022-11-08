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
import java.util.ArrayList;
import java.util.List;

public class TourTab extends GridPane {
    private final ListView<Tour> lvwActiveTours;
    private final ListView<Tour> lvwTours;
    private OrderControllerInterface controller;
    private SplitPane splitPane;
    private final TextField txfName;
    private final TextArea txaDescription;
    private final TextField timePicker;
    private Order order;

    /**
     * Tour tab to control all the tours
     */
public TourTab(){
    lvwActiveTours = new ListView<>();
    lvwTours = new ListView<>();
    splitPane = new SplitPane();
    txfName = new TextField();
    txaDescription = new TextArea();
    timePicker = new TextField();
    controller = new OrderController(Storage.getStorage());
    order = controller.createOrder();

    this.setPadding(new Insets(20));
    this.setHgap(10);
    this.setVgap(10);

    // Adding a splitpane to the pane
    this.add(splitPane, 0, 1);
    // All the split in the splitpane left, mid and right
    VBox leftControl = new VBox(new Label("Bestilte Rundvisninger"));
    VBox midControl = new VBox(new Label("Afsluttede Rundvisninger"));
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
    lvwTours.setPrefWidth(100);
    lvwTours.setPrefHeight(300);
    lvwTours.getItems().setAll();
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
    //TODO - Lav et if-statement som tjekker om der overhovedet er en Tour selected i lvwActiveTours.
    // Opdater kun felterne hvis selected != null.
    String name = lvwActiveTours.getSelectionModel().getSelectedItem().getName();
    String description = lvwActiveTours.getSelectionModel().getSelectedItem().getDescription();
    LocalTime time = lvwActiveTours.getSelectionModel().getSelectedItem().getTime();
    txfName.setText(name);
    txaDescription.setText(description + lvwActiveTours.getSelectionModel().getSelectedItem().getOrderLines());
    timePicker.setText(String.valueOf(time));
}

    /**
     * Method to open endTourWindow where one ends the selectedf tour
     */
private void finishTour(){
    //TODO - Tjek evt om der er en selected Tour før man kan afslutte en tour.
    // Alternativt kan du disable knappen så længe der ikke er en tour selected.
    Stage stage = new Stage(StageStyle.UTILITY);
    EndTourWindow endTourWindow = new EndTourWindow("Afslut rundtur",stage, lvwActiveTours.getSelectionModel().getSelectedItem());
    endTourWindow.showAndWait();

    updateControls();
}

    /**
     * Opens up a tour windows where its possible to create a tour
     */
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
    //TODO - Du skal finde en måde kun at sætte de aktive rundvisninger i det ene vindue, og de afsluttede i det andet.
    // Forskellen på dem er at de afsluttede har en paymentmethod, mens de aktives paymentmethod er null. Lav evt to
    // lister, løb alle tours igennem, og sorter dem i de korrekte lister, og kald så setAll() på de korrekte listview,
    // med den passende liste. Det kan muligvis også først betale sig at sortere dem efter du har fordelt dem, men det
    // kan jeg ikke lige overskue
    List<Tour> lvwToursSorted = new ArrayList<>();
    lvwToursSorted = controller.getTours();
    lvwToursSorted.sort((o1, o2) -> o1.getEndDate().compareTo(o2.getEndDate()));
//    lvwActiveTours.getItems().setAll(controller.getTours());
//    lvwActiveTours.getItems().setAll(controller.getTours().sort((o1, o2) -> o1.getEndDate().compareTo(o2.getEndDate())));
    lvwActiveTours.getItems().setAll(lvwToursSorted);
//    lvwTours.getItems().setAll(controller.getDoneTours());
}



}
