package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.controller.OrderController;
import model.modelklasser.Order;
import storage.Storage;

public class RentalTab extends GridPane {
    private final ListView<Order> lvwActiveRentals = new ListView<>();
    private final ListView<Order> lvwRentals = new ListView<>();
    private OrderControllerInterface rentalController = OrderController.getOrderController(Storage.getStorage());
    private SplitPane splitPane = new SplitPane();



    public RentalTab(){
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);


        this.add(splitPane,0,1);
        VBox leftControl  = new VBox(new Label("Aktive Udlejninger"));
        VBox rightControl = new VBox(new Label("Færdige udlejninger"));

        splitPane.getItems().addAll(leftControl, rightControl);


//        this.add(lvwActiveRentals,0,2);
//        lvwActiveRentals.setPrefWidth(200);
//        lvwActiveRentals.setPrefHeight(300);
//        lvwActiveRentals.getItems().setAll();
//

//        this.add(lvwRentals,1,2);
//        lvwRentals.setPrefWidth(200);
//        lvwRentals.setPrefHeight(300);
//        lvwRentals.getItems().setAll();
        Button btnRental = new Button("Opret udlejning");
        btnRental.setOnAction(event -> this.createRental());

        this.initProduct();
        updateControls();

    }

    private void createRental() {

    }

    public void updateControls() {
        //Update choiceboxes
        lvwActiveRentals.getItems().setAll(rentalController.getRentals());
        splitPane.getItems().setAll();

    }
    /**
     * Creates initial products and categories
     */
    private void initProduct() {
        if (rentalController instanceof OrderController) {
            OrderController controller = (OrderController) rentalController;
            controller.initContent();
        }

    }







}
