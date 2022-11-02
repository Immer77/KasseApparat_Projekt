package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
        HBox btnBox = new HBox();
        btnBox.setSpacing(10);
        this.add(btnBox,0,0);

        lvwActiveRentals.setPrefWidth(200);
        lvwActiveRentals.setPrefHeight(300);
        lvwActiveRentals.getItems().setAll();



        lvwRentals.setPrefWidth(200);
        lvwRentals.setPrefHeight(300);
        lvwRentals.getItems().setAll();
        rightControl.getChildren().add(lvwRentals);

        Button btnRental = new Button("Opret udlejning");
        btnRental.setOnAction(event -> this.createRental());
        btnBox.getChildren().add(btnRental);

        Button btnFinishRental = new Button("Afslut udlejning");
        btnFinishRental.setOnAction(event -> this.finishRental());
        btnBox.getChildren().add(btnFinishRental);

        leftControl.getChildren().add(lvwActiveRentals);
        splitPane.getItems().addAll(leftControl, rightControl);

        updateControls();

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
        lvwActiveRentals.getItems().setAll(rentalController.getRentals());


    }
    /**
     * Creates initial products and categories
     */
//    private void initProduct() {
//        if (rentalController instanceof OrderController) {
//            OrderController controller = (OrderController) rentalController;
//            controller.initContent();
//        }
//
//    }







}
