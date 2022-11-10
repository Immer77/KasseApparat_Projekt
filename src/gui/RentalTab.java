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

    private void updateSumOfRentedProducts() {

        // Hashmap to contain the amount of products
        HashMap<Product, Integer> productsAndAmounts = new HashMap<>();
        // For each order there is in the active rentals
        for (Order order : controller.getActiveRentals()) {
            // For each orderline there is in the order
            for (OrderLine orderLine : order.getOrderLines()) {
                // We get the product through the coupling of price to product
                Product product = orderLine.getPrice().getProduct();
                // Checking if there already is a key product in the hashmap
                if (productsAndAmounts.containsKey(product)) {
                    // In case there already is a product in the hashmap we need to merge the .getamount so that we update it with more amount of the product
                    // eg. if we have 2 rentals who have the product "Ekstra pilsner" and both have the amount 5, we will need to merge those 2 so that we get 10
                    productsAndAmounts.merge(product, orderLine.getAmount(), Integer::sum);
                } else {
                    // If it doesnt exist we just put it in the hashmap
                    productsAndAmounts.put(orderLine.getPrice().getProduct(), orderLine.getAmount());
                }


            }
        }

        // The stringbuilder for creating a better overview over the hashmap product and amount
        StringBuilder sb = new StringBuilder();
        // Creating a entryset to iterate over
        Set entryset = productsAndAmounts.entrySet();
        // Creating a iterator to iterate through each entryset there is in productsandamounts hashmap
        Iterator iterator = entryset.iterator();


        // While the entryset has a next line
        while (iterator.hasNext()) {
            // Divide the entryset in 2 where regex '=' is the splitter
            String[] linedivider = iterator.next().toString().split("=");

            // Append to Stringbuilder
            sb.append("Produkt: " + linedivider[0] + " Antal: " + linedivider[1] + "\n");

        }
        // Finally we set the text for the Textarea
        txaAllRentalsMade.setText(String.valueOf(sb));

    }

    /**
     * Updates fields in middle control pane
     */
    private void updateFinishedRentalInfo(){
        Rental selectedRental;
        clearTextFields();

        selectedRental = lvwRentals.getSelectionModel().getSelectedItem();
        txfName.setText(selectedRental.getName());
        txaDescription.setText(selectedRental.getDescription() + "\n" + selectedRental.getOrderLines());
        txfDatePicker.setText(String.valueOf(selectedRental.getEndDate()));

        if (selectedRental.getFixedPrice() > 0){
            txaDescription.clear();
            txaDescription.setText(selectedRental.getDescription() + "\n" + selectedRental.getOrderLines() + "\nAftalt Pris for Udlejning: " + calculateFinalPrice());
        }
        if (selectedRental.getPercentDiscount() > 0){
            txaDescription.clear();
            txaDescription.setText(selectedRental.getDescription() + "\n" + selectedRental.getOrderLines() + "\nTotal Pris for Udlejning: " + calculateFinalPrice() + "\nRabat givet: " + selectedRental.getPercentDiscount() + "%");
        }
    }
    /**
     * Updates fields in right control pane
     */
    private void updateOpenRentalInfo(){
        Rental selectedRental;
        clearTextFields();

        selectedRental = lvwActiveRentals.getSelectionModel().getSelectedItem();
        txfName.setText(selectedRental.getName());
        txfDatePicker.setText(String.valueOf(selectedRental.getEndDate()));
        txaDescription.setText(selectedRental.getDescription() + "\n" + selectedRental.getOrderLines() + "\nTotal Pris for Udlejning: " + calculateFinalPrice());

        if (selectedRental.getFixedPrice() > 0){
            txaDescription.clear();
            txaDescription.setText(selectedRental.getDescription() + "\n" + selectedRental.getOrderLines() + "\nAftalt Pris for Udlejning: " + calculateFinalPrice());
        }
        if (selectedRental.getPercentDiscount() > 0){
            txaDescription.clear();
            txaDescription.setText(selectedRental.getDescription() + "\n" + selectedRental.getOrderLines() + "\nTotal Pris for Udlejning: " + calculateFinalPrice() + "\nRabat givet: " + selectedRental.getPercentDiscount() + "%");
        }

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
        if(lvwActiveRentals.getSelectionModel().getSelectedItem() != null){
            for (OrderLine orderLine : lvwActiveRentals.getSelectionModel().getSelectedItem().getOrderLines()) {
                if (lvwActiveRentals.getSelectionModel().getSelectedItem().getFixedPrice() != 0) {
                    finalPrice = lvwActiveRentals.getSelectionModel().getSelectedItem().getFixedPrice();
                    break;
                }
                finalPrice += orderLine.getAmount() * orderLine.getPrice().getValue();

            }

            double percentageDiscount = lvwActiveRentals.getSelectionModel().getSelectedItem().getPercentDiscount();
            finalPrice *= (100 - percentageDiscount) / 100;
        }
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
        lvwActiveRentals.getItems().sort((o1, o2) -> o1.getEndDate().compareTo(o2.getEndDate()));
        lvwRentals.getItems().sort((o1, o2) -> o1.getEndDate().compareTo(o2.getEndDate()));


    }

}
