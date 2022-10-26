package gui;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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


        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 2, 3);
        btnCancel.setOnAction(event -> cancelAction());
    }

    public void oKAction() {
        ProductOverviewController controller = ProductOverviewController.getProductOverviewController(Storage.getUnique_Storage());
        String name = "";
        String description = "";
        if (txfName.getText().trim().length() > 0) {
            name = txfName.getText().trim();
        }
        if (txaDescription.getText().trim().length() > 0) {
            description = txaDescription.getText().trim();
        }

        controller.createProduct(productCategory, name, description);
        this.close();

    }

    public void cancelAction () {
        this.close();
    }

}
