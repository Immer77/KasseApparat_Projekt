package gui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.ProductOverviewController;
import model.modelklasser.*;
import storage.Storage;

public class CreateDepositPriceWindow extends Stage {

    //Fields ---------------------------------------------------------------------------------------------------------
    private Product product;
    private ProductOverviewControllerInterface controller;
    private Spinner<Double> spnAmount;
    private ProductCategory category;
    private ToggleGroup tglGroup;
    private RadioButton rbtnProduct;
    private RadioButton rbtnCategory;

    public CreateDepositPriceWindow(String title, Product product, Stage owner, ProductCategory category) {
        this.product = product;
        this.category = category;
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

    //Methods - Other ------------------------------------------------------------------------------------------------
    private void initContent(GridPane pane) {
        //pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblDepositFor = new Label("Lav ny pantpris for:");

        rbtnProduct = new RadioButton("Produktet " + product.getName());
        if (!product.getDescription().isBlank()) {
            rbtnProduct.setText(rbtnProduct.getText() + " (" + product.getDescription() + ")");
        }

        rbtnCategory = new RadioButton("Hele kategorien " + category.getTitle());
        if (!category.getDescription().isBlank()) {
            rbtnCategory.setText(rbtnCategory.getText() + " (" + category.getDescription() + ")");
        }

        tglGroup = new ToggleGroup();
        tglGroup.getToggles().add(rbtnProduct);
        tglGroup.getToggles().add(rbtnCategory);
        tglGroup.selectToggle(rbtnProduct);

        VBox vbxRadioButtons = new VBox(lblDepositFor, rbtnProduct, rbtnCategory);
        vbxRadioButtons.setSpacing(5);
        pane.add(vbxRadioButtons, 0, 0);

        double startAmount = 0.0;
        if (product.getDepositPrice() != null) {
            startAmount = product.getDepositPrice().getValue();
        }
        spnAmount = new Spinner<>(0.0, Double.MAX_VALUE, startAmount);
        spnAmount.setEditable(true);

        Label lblUnit = new Label("DKK");

        HBox hbxAmountInUnit = new HBox(spnAmount, lblUnit);
        hbxAmountInUnit.setSpacing(5);
        hbxAmountInUnit.setAlignment(Pos.BASELINE_LEFT);
        pane.add(hbxAmountInUnit, 0, 2);

        Button btnOK = new Button("Ok");
        btnOK.setOnAction(event -> oKAction());
        btnOK.setDefaultButton(true);
        btnOK.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnOK, Priority.ALWAYS);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> cancelAction());
        btnCancel.setCancelButton(true);
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnCancel, Priority.ALWAYS);

        HBox hbxButtons = new HBox(btnOK, btnCancel);
        hbxButtons.setSpacing(10);
        pane.add(hbxButtons, 0, 3);
    }

    /**
     * Creates a new Price object with the entered name and description, adding it to the product as a deposit price, then closes the window. If no name is given, an alert is shown and nothing happens instead.
     */
    private void oKAction() {
        Unit unit = Unit.DKK;
        double amount = spnAmount.getValue();
        Situation situation = controller.getSituations().get(0);

        if (tglGroup.getSelectedToggle().equals(rbtnProduct)) {
            product.createDeposit(amount, unit, situation);
        } else if (tglGroup.getSelectedToggle().equals(rbtnCategory)) {
            for (Product prod : category.getProducts()) {
                prod.createDeposit(amount, unit, situation);
            }
        }
        this.close();
    }

    /**
     * Closes the window, discarding any changes.
     */
    private void cancelAction() {
        this.close();
    }
}
