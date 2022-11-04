package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.controller.OrderController;
import model.modelklasser.Order;
import model.modelklasser.Tour;
import storage.Storage;

public class TourTab extends GridPane {
    private final ListView<Tour> lvwTours = new ListView<>();
    private final ListView<Tour> lvwActiveTours = new ListView<>();
    private SplitPane splitPane = new SplitPane();
    private OrderControllerInterface controller = OrderController.getOrderController(Storage.getStorage());
    private Order order = controller.createOrder();

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
    btnTour.setOnAction(event -> this.createRundtur());
    // MAGNUS ER HER

    // Updatescontrols
    updateControls();
}
//-----------------------------------------------
    /**
     * Updates fields in right(??) control pane
     */
private void updateFieldsInfo() {
    //TODO
}

private void createRundtur(){
    //TODO
    updateControls();

}

public void updateControls(){
    lvwActiveTours.getItems().setAll(controller.getTours());
}

}
