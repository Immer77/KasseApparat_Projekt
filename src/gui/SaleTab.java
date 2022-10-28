package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.controller.OrderController;
import model.controller.ProductOverviewController;
import model.modelklasser.*;
import storage.Storage;

public class SaleTab extends GridPane {
    //Fields ------------------------------------------------------------
    private OrderControllerInterface orderController = OrderController.getOrderController(Storage.getStorage());
    private ProductOverviewControllerInterface productController = ProductOverviewController.getProductOverviewController(Storage.getStorage());
    private Accordion accProductOverview;
    private ListView<HBox> orderOverview;
    private ChoiceBox<Situation> chSituation;
    private ChoiceBox<Unit> chUnits;

    //Constructors ------------------------------------------------------
    public SaleTab () {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);


        //Adds a choicebox to select the Situation
        chSituation = new ChoiceBox<>();
        this.add(chSituation, 0,0);

        ChangeListener<Situation> situationListener = (ov, o, n) -> this.situationSelectionChanged();
        chSituation.getSelectionModel().selectedItemProperty().addListener(situationListener);

        //Adds a choicebox to select which unit to show prices in
        chUnits = new ChoiceBox<>();
        this.add(chUnits, 1, 0);

        ChangeListener<Unit> unitListener = (ov, o, n) -> this.unitSelectionChanged();
        chUnits.getSelectionModel().selectedItemProperty().addListener(unitListener);




        //Initiates examples of situations
        orderController.initContent();


        //Updates all controls
        updateControls();





        //___________SKAL SES PÅ________________________________
/*        updateProductOverview();



        orderOverview = new ListView<>();
        orderOverview.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
        this.add(orderOverview, 1, 0, 2, 2);



        Label lblProductName = new Label("Produkt");

        Label lblAmount = new Label("Antal");

        HBox orderTitles = new HBox(lblProductName, lblAmount);
        orderOverview.getItems().setAll(orderTitles);*/
        //_________________________________________________________



    }

    //Methods - Get, Set & Add -------------------------------------------


    //Methods - Other ----------------------------------------------------
    public void updateControls () {
        //Update choiceboxes
        chSituation.getItems().setAll(orderController.getSituations());
        if (chSituation.getSelectionModel().isEmpty()) {
            chSituation.getSelectionModel().select(0);
        }

        chUnits.getItems().setAll(Unit.values());
        if (chUnits.getSelectionModel().isEmpty()) {
            chUnits.getSelectionModel().select(0);
        }

        //Update productOverview
        updateProductOverview();

    }

    public void productCategorySelectionChanged () {

    }


    /**
     * Adds a product from the overview to the current order
     * @param product the Product to add
     */
    private void addProductToOrder(Product product) {
        //Create label for the product and add to the overview
        Label lblProduct = new Label(product.toString());



        //Create amount textfield, with a + & - button

        //Create an orderline for the product and add to the order

    }

    private void updateProductOverview() {
        //Accordion control for showing of categories and products
        //Create accordion view, and add the titled pane
        accProductOverview = new Accordion();
        accProductOverview.setMaxWidth(Double.MAX_VALUE);
        accProductOverview.setPrefWidth(250);
        accProductOverview.setPadding(Insets.EMPTY);

        //For each category...
        for (ProductCategory proCat : productController.getProductCategories()) {
            //Create a vbox
            VBox buttonVBox = new VBox();
            buttonVBox.setPadding(new Insets(5));
            buttonVBox.setFillWidth(true);
            buttonVBox.maxWidth(Double.MAX_VALUE);
            buttonVBox.setPrefWidth(accProductOverview.getPrefWidth());

            //for each product in the category, create a button and add to the vbox
            for (Product prod : proCat.getProducts()) {
                BorderPane borderpane = new BorderPane();

                Button productButton = new Button(prod.toString());
                productButton.setOnAction(event -> addProductToOrder(prod));
                productButton.setWrapText(true);
                productButton.setMaxWidth(Double.MAX_VALUE);
                borderpane.setLeft(productButton);

                TextField txfPrice = new TextField();
                for (Price price : prod.getPrices()) {
                    if (price.getSituation().equals(chSituation.getSelectionModel().getSelectedItem()) && price.getUnit().equals(chUnits.getSelectionModel().getSelectedItem())) {
                        txfPrice.setText(price.toString());
                    }
                }
                if (txfPrice.getText().isBlank()) {
                    txfPrice.setText("-");
                }
                txfPrice.setEditable(false);
                borderpane.setRight(txfPrice);

                buttonVBox.getChildren().add(borderpane);

            }

            //Create a titledPane, add the vbox
            TitledPane titledPane = new TitledPane(proCat.getTitle(), buttonVBox);
            //Add the titledPane to the accordionView
            accProductOverview.getPanes().add(titledPane);
        }

        //add accordionView to tab
        this.add(accProductOverview, 0,1, 2, 2);
    }

    private void situationSelectionChanged() {

    }

    private void unitSelectionChanged() {

    }


}
