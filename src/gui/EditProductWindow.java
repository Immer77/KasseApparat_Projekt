package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.ProductOverviewController;
import model.modelklasser.Product;
import storage.Storage;

public class EditProductWindow extends Stage {
    private Product product;
    private TextField txfTitle = new TextField();
    private TextArea txaDescription = new TextArea();

    private ProductOverviewControllerInterface controller;

    /**
     * Creates a new window used for editing a ProductCategory
     *
     * @param title The title of the window
     * @param owner The Stage owning this window
     */
    public EditProductWindow(String title, Stage owner, Product product) {
        this.product = product;
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
    private void initContent(GridPane pane) {
        //pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblTitle = new Label("Kategorinavn:");
        pane.add(lblTitle, 0, 0);
        Label lblDescription = new Label("Beskrivelse:");
        pane.add(lblDescription, 0, 1);

        txfTitle.setText(product.getName());
        pane.add(txfTitle, 1, 0, 2, 1);

        txaDescription.setText(product.getDescription());
        pane.add(txaDescription, 1, 1, 2, 2);

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
    private void oKAction() {
        try {
            String title = "";
            String description = "";
            if (!txfTitle.getText().isBlank()) {
                title = txfTitle.getText().trim();
            }
            if (!txaDescription.getText().isBlank()) {
                description = txaDescription.getText().trim();
            }

            controller.setNameForProduct(title, product);
            controller.setDescriptionForProduct(description, product);

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
    private void cancelAction() {
        this.close();
    }
}
