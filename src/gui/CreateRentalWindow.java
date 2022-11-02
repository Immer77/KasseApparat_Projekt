package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.OrderController;
import storage.Storage;

import java.time.LocalDate;

public class CreateRentalWindow extends Stage {

    private final TextField txfName = new TextField();
    private final TextArea txaDescription = new TextArea();
    private final OrderControllerInterface controller = OrderController.getOrderController(Storage.getStorage());
    private final DatePicker datePicker = new DatePicker();



    public CreateRentalWindow(String title, String description, Stage owner){
        this.initOwner(owner);
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setMinHeight(300);
        this.setMinWidth(500);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    public void initContent(GridPane pane) {
        //pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblName = new Label("Navn:");
        pane.add(lblName, 0,0);
        Label lblDescription = new Label("Beskrivelse:");
        pane.add(lblDescription, 0,1);

        pane.add(txfName, 1,0, 2, 1);
        pane.add(txaDescription, 1, 1, 2, 2);

        Button btnOK = new Button("Ok");
        pane.add(btnOK, 1, 3);
        btnOK.setOnAction(event -> oKAction());
        btnOK.setDefaultButton(true);

        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 2, 3);
        btnCancel.setOnAction(event -> cancelAction());
        btnCancel.setCancelButton(true);

        pane.add(datePicker,3,1);

    }

    private void cancelAction() {
    }

    public void oKAction() {
        String name = "";
        String description = "";
        LocalDate datepicker = null;
        if (!txfName.getText().isBlank()) {
            name = txfName.getText().trim();

            if (!txaDescription.getText().isBlank()) {
                description = txaDescription.getText().trim();
            }

            controller.createRental(name, description, LocalDate.from(datepicker.getDayOfWeek()));
            this.close();

        } else {
            Alert nameAlert = new Alert(Alert.AlertType.ERROR);
            nameAlert.setTitle("Manglende navn!");
            nameAlert.setHeaderText("En udlejning skal have et navn før det kan oprettes!");
            nameAlert.showAndWait();
        }
    }
}
