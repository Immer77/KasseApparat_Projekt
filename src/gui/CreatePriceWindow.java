package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.ProductOverviewController;
import model.modelklasser.Product;
import model.modelklasser.Situation;
import model.modelklasser.Unit;
import storage.Storage;

public class CreatePriceWindow extends Stage {
    //Fields ------------------------------------------------------------

    private Product product;
    private ProductOverviewControllerInterface controller;
    private ChoiceBox<Situation> chSituation;
    private ChoiceBox<Unit> chUnit;
    private Spinner<Double> spnAmount;

    //Constructors ------------------------------------------------------
    public CreatePriceWindow (String title, Product product, Stage owner) {
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

    //Methods - Other ----------------------------------------------------
    /**
     * Initialises the content in the window.
     * @param pane The Gridpane parent of the content
     */
    public void initContent(GridPane pane) {
        //pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblSituation = new Label("For situation:");
        HBox.setHgrow(lblSituation, Priority.ALWAYS);
        lblSituation.setAlignment(Pos.BASELINE_LEFT);

        chSituation = new ChoiceBox<>();
        chSituation.getItems().setAll(controller.getSituations());
        chSituation.getSelectionModel().select(0);
        chSituation.setPrefWidth(100);


        VBox vbxSituation = new VBox(lblSituation,chSituation);
        pane.add(vbxSituation,0,0);

        spnAmount = new Spinner<>(0.0,Double.MAX_VALUE,0.0);
        spnAmount.setEditable(true);

        chUnit = new ChoiceBox<>();
        chUnit.getItems().setAll(Unit.values());
        chUnit.getSelectionModel().select(0);

        HBox hbxAmountInUnit = new HBox(spnAmount, chUnit);
        hbxAmountInUnit.setSpacing(5);
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
     * Creates a new Price object with the entered name and description, then closes the window. If no name is given, an alert is shown and nothing happens instead.
     */
    public void oKAction() {
        Unit unit = chUnit.getValue();
        double amount = spnAmount.getValue();
        Situation situation = chSituation.getValue();

        product.createPrice(amount, unit,situation);

        this.close();
    }

    /**
     * Closes the window, discarding any changes.
     */
    public void cancelAction () {
        this.close();
    }



}
