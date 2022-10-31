package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    /**
     * Initialises tabs in the tabpane
     *
     * @param tabPane
     */
    private void initTabPane(TabPane tabPane) {
        //disabled closing of tabs
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //-----------Product Overview Tab creation------------------------------------
        //Creates a new Tab with a label, and adds it to the tabPane
        Tab firstTab = new Tab("Produktoversigt");
        tabPane.getTabs().add(firstTab);

        //Creates a new object of the ProductOverviewTab class
        ProductOverviewTab productOverviewTab = new ProductOverviewTab();
        //Sets the new tabs content as the ProductOverviewTab object
        firstTab.setContent(productOverviewTab);
        //Updates controls when tab selection changes
        firstTab.setOnSelectionChanged(event -> productOverviewTab.updateControls());
        //----------------------------------------------------------------------------

        //-----------Product Overview Tab creation------------------------------------
        //Creates a new instance of a SaleTab
        SaleTab saleTab = new SaleTab();
        //Creates a new Tab, with a title and the saleTab as content
        Tab secondTab = new Tab("Salg", saleTab);
        //...then adds it to the tabPane
        tabPane.getTabs().add(secondTab);
        //Updates controls when tab selection changes
        secondTab.setOnSelectionChanged(event -> saleTab.updateControls());
        //----------------------------------------------------------------------------

    }

}
