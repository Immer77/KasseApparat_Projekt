package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.ProductOverviewController;
import storage.Storage;

public class CreateSituationWindow extends Stage {
    private TextField txfName = new TextField();

    private ProductOverviewControllerInterface controller;

    /**
     * Creates a new window used for editing a ProductCategory
     *
     * @param title The title of the window
     * @param owner The Stage owning this window
     */
    public CreateSituationWindow(String title, Stage owner) {
        this.initOwner(owner);
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setMinHeight(300);
        this.setMinWidth(500);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();


        Scene scene = new Scene(pane);
        this.setScene(scene);

        controller = new ProductOverviewController(Storage.getStorage());
        this.initContent(pane);
    }

    /**
     * Initialises the content in the window.
     *
     * @param pane The Gridpane parent of the content
     */
    public void initContent(GridPane pane) {
        //pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblTitle = new Label("Situationsnavn:");
        pane.add(lblTitle, 0, 0);

        pane.add(txfName, 0, 1);

        Button btnOK = new Button("Ok");
        pane.add(btnOK, 1, 3);
        btnOK.setOnAction(event -> oKAction());
        btnOK.setDefaultButton(true);


        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 2, 3);
        btnCancel.setOnAction(event -> cancelAction());
        btnCancel.setCancelButton(true);
    }

    /**
     * Updates the title and description of the current Product Category
     */
    public void oKAction() {
        try {
            String name = "";
            if (!txfName.getText().isBlank()) {
                name = txfName.getText().trim();
            }
            controller.createSituation(name);

            this.close();

        } catch (IllegalArgumentException iae) {
            Alert titleAlert = new Alert(Alert.AlertType.ERROR);
            titleAlert.setTitle("Advarsel!");
            titleAlert.setHeaderText(iae.getMessage());
            titleAlert.showAndWait();
        }
    }

    /**
     * Closes the window, discarding any changes.
     */
    public void cancelAction() {
        this.close();
    }
}
