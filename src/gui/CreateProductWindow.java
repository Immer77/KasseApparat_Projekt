package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.ProductOverviewController;
import model.modelklasser.ProductCategory;
import storage.Storage;

public class CreateProductWindow extends Stage {


    private TextField txfName = new TextField();
    private TextArea txaDescription = new TextArea();
    private ProductCategory productCategory;
    private ProductOverviewControllerInterface controller = ProductOverviewController.getProductOverviewController(Storage.getStorage());

    /**
     * Creates a new window used for creating a new Product
     * @param title The title of the window
     * @param productCategory The ProductCategory for which the Product should belong to
     * @param owner The Stage owning this window
     */
    public CreateProductWindow (String title, ProductCategory productCategory, Stage owner) {
        this.productCategory = productCategory;
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


    /**
     * Initialises the content in the window.
     * @param pane The Gridpane parent of the content
     */
    public void initContent(GridPane pane) {
        //pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblName = new Label("Produktnavn:");
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
    }

    /**
     * Creates a new Product object with the entered name and description, then closes the window. If no name is given, an alert is shown and nothing happens instead.
     */
    public void oKAction() {
        String name = "";
        String description = "";
        if (!txfName.getText().isBlank()) {
            name = txfName.getText().trim();

            if (!txaDescription.getText().isBlank()) {
                description = txaDescription.getText().trim();
            }

            controller.createProduct(productCategory, name, description);
            this.close();

        } else {
            Alert nameAlert = new Alert(Alert.AlertType.ERROR);
            nameAlert.setTitle("Manglende navn!");
            nameAlert.setHeaderText("Et produkt skal have et navn før det kan oprettes!");
            nameAlert.showAndWait();
        }
    }

    /**
     * Closes the window, discarding any changes.
     */
    public void cancelAction () {
        this.close();
    }

}
