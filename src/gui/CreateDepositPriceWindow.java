package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.OrderController;
import model.controller.ProductOverviewController;
import model.modelklasser.Product;
import model.modelklasser.Situation;
import model.modelklasser.Unit;
import storage.Storage;

public class CreateDepositPriceWindow extends Stage {
    //Fields ---------------------------------------------------------------------------------------------------------
    private Product product;
    private ProductOverviewControllerInterface controller;
    private Spinner<Double> spnAmount;

    //Constructors ---------------------------------------------------------------------------------------------------
    public CreateDepositPriceWindow(String title, Product product, Stage owner) {
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

    //Methods - Get, Set & Add ---------------------------------------------------------------------------------------


    //Methods - Other ------------------------------------------------------------------------------------------------
    public void initContent(GridPane pane) {
        //pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        spnAmount = new Spinner<>(0.0,Double.MAX_VALUE,0.0);
        spnAmount.setEditable(true);

        Label lblUnit = new Label("DKK");

        HBox hbxAmountInUnit = new HBox(spnAmount, lblUnit);
        hbxAmountInUnit.setSpacing(5);
        hbxAmountInUnit.setAlignment(Pos.BASELINE_LEFT);
        hbxAmountInUnit.setPadding(new Insets(20,0,20,0));
        pane.add(hbxAmountInUnit,0,1);

        Button btnOK = new Button("Ok");
        btnOK.setOnAction(event -> oKAction());
        btnOK.setDefaultButton(true);
        btnOK.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnOK,Priority.ALWAYS);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> cancelAction());
        btnCancel.setCancelButton(true);
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnCancel,Priority.ALWAYS);

        HBox hbxButtons = new HBox(btnOK, btnCancel);
        hbxButtons.setSpacing(10);
        pane.add(hbxButtons,0,3);
    }

    /**
     * Creates a new Price object with the entered name and description, adding it to the product as a deposit price, then closes the window. If no name is given, an alert is shown and nothing happens instead.
     */
    public void oKAction() {
        Unit unit = Unit.DKK;
        double amount = spnAmount.getValue();
        Situation situation = controller.getSituations().get(0);

        product.createDeposit(amount, unit, situation);

        this.close();
    }

    /**
     * Closes the window, discarding any changes.
     */
    public void cancelAction () {
        this.close();
    }

}
