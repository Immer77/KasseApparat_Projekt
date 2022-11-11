package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.controller.OrderController;
import model.controller.ProductOverviewController;
import storage.Storage;

public class StartWindow extends Application {
    private Stage mainStage;

    public void start(Stage stage) {
        stage.setTitle("Aarhus Bryghus");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        mainStage = stage;
        stage.show();
    }


    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();

        pane.setCenter(tabPane);

        ProductOverviewController productcontroller = new ProductOverviewController(Storage.getStorage());
        productcontroller.initContent();

        OrderController orderController = new OrderController(Storage.getStorage());
        orderController.initContent();

        this.initTabPane(tabPane);
    }

    /**
     * Initialises tabs in the tabpane
     *
     * @param tabPane
     */
    private void initTabPane(TabPane tabPane) {
        //tabPane size
        tabPane.setPrefSize(1000, 750);

        //disabled closing of tabs
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //-----------Product Overview Tab creation------------------------------------
        //Creates a new instance of a SaleTab
        SaleTab saleTab = new SaleTab();
        //Creates a new Tab, with a title and the saleTab as content
        Tab secondTab = new Tab("Salg", saleTab);
        //...then adds it to the tabPane
        tabPane.getTabs().add(secondTab);
        //Updates controls when tab selection changes
        secondTab.setOnSelectionChanged(event -> saleTab.updateControls());

        //-----------Rental overview tab creation-------------------------------------
        //Creates a new instance of a Rentaltab
        RentalTab rentalTab = new RentalTab();
        // Creatas a new tab, with a title and the rentaltab as content
        Tab thirdTab = new Tab("Udlejning", rentalTab);
        //...then adds it to the tabPane
        tabPane.getTabs().add(thirdTab);
        //Updates controls when tab selection changes
        thirdTab.setOnSelectionChanged(event -> rentalTab.updateControls());

        //-----------Tour overview tab creation-------------------------------------
        //Creates a new instance of a Rentaltab
        TourTab tourTab = new TourTab();
        // Creatas a new tab, with a title and the rentaltab as content
        Tab fourthTab = new Tab("Rundvisninger", tourTab);
        //...then adds it to the tabPane
        tabPane.getTabs().add(fourthTab);
        //Updates controls when tab selection changes
        fourthTab.setOnSelectionChanged(event -> tourTab.updateControls());

        //-----------Product Overview Tab creation
        //Creates a new Tab with a label, and adds it to the tabPane
        Tab firstTab = new Tab("Produktoversigt");
        tabPane.getTabs().add(firstTab);

        //Creates a new object of the ProductOverviewTab class
        ProductOverviewTab productOverviewTab = new ProductOverviewTab();
        //Sets the new tabs content as the ProductOverviewTab object
        firstTab.setContent(productOverviewTab);
        //Updates controls when tab selection changes
        firstTab.setOnSelectionChanged(event -> productOverviewTab.updateControls());

        //-----------Product Statistics Tab creation------------------------------------
        //Creates a new instance of a SaleTab
        StatTab statTab = new StatTab();
        //Creates a new Tab, with a title and the saleTab as content
        Tab fifthTab = new Tab("Salgsstatistik", statTab);
        //...then adds it to the tabPane
        tabPane.getTabs().add(fifthTab);
        //Updates controls when tab selection changes
        fifthTab.setOnSelectionChanged(event -> statTab.resetTab());

        //-----------Order overview tab creation-------------------------------------
        // Creates a new instance of the OrderOverviewTab
        OrderOverviewTab orderOverviewTab = new OrderOverviewTab();
        // Creates a new tab, with a title and the orderOverviewTab as content
        Tab sixthTab = new Tab("Ordrer Oversigt", orderOverviewTab);
        // adding orderOverviewTab to the tabPane
        tabPane.getTabs().add(sixthTab);
        // updates the tab when selecting it
        sixthTab.setOnSelectionChanged(event -> orderOverviewTab.updateOrderList());
    }
}
