package gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.modelklasser.Product;
import model.modelklasser.ProductCategory;

import java.util.ArrayList;

import java.util.ArrayList;

public class Gui extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Gui UC9, UC13, UC14");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    // -------------------------------------------------------------------------

    private final ListView<Product> lvwProdukter = new ListView<>();
    private final ListView<ProductCategory> lvwKategorier = new ListView<>();
    private final ArrayList<Product> products = new ArrayList<>();
    private final ArrayList<ProductCategory> productCategories = new ArrayList<>();

    private void initContent(GridPane pane) {

//        this.initContent();

        // pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblNameKategori = new Label("Product Category:");
        pane.add(lblNameKategori, 0, 0);

        Label lblNameProdukt = new Label("Product:");
        pane.add(lblNameProdukt, 1, 0);

        pane.add(lvwKategorier, 0, 1);
        lvwKategorier.setPrefWidth(200);
        lvwKategorier.setPrefHeight(200);
        lvwKategorier.getItems().setAll();

        pane.add(lvwProdukter, 1, 1);
        lvwProdukter.setPrefWidth(200);
        lvwProdukter.setPrefHeight(200);
        lvwProdukter.getItems().setAll();

        ChangeListener<Product> listener = (ov, o, n) -> this.productsItemSelected();
        lvwProdukter.getSelectionModel().selectedItemProperty().addListener(listener);

        Button btnCreateProductCategory = new Button("Create category");
        pane.add(btnCreateProductCategory, 0, 0);
        btnCreateProductCategory.setOnAction(event -> this.createProductCategoryAction());

        Button btnCreateProduct = new Button("Create product");
        pane.add(btnCreateProduct, 1, 0);
        btnCreateProduct.setOnAction(event -> this.createProductAction());

        this.initProduct();
    }

    // -------------------------------------------------------------------------

    // Nogle produkter jeg har oprettet for at se om mit listview virker
    private void initProduct(){
        productCategories.add(new ProductCategory("Candy", "For kids");
        products.add(new Product("Gummibear", "Sweet candy"));
        products.add(new Product("Liqurice", "Tart candy"));
        products.add(new Product("Sourz", "Sour drink"));

    }

    // -------------------------------------------------------------------------

    private void productsItemSelected() {
        Product selected = lvwProdukter.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lvwProdukter.getSelectionModel().getSelectedItem().toString();
        } else {
            lvwProdukter.refresh();
        }
    }

    private void initproductCategories(){
        productCategories.add(new ProductCategory("Candy", "Goodies for kids"));
    }

    // Button actions
    private void createProductCategoryAction(){
        //TODO
    }

    private void createProductAction(){
        //TODO
    }

}
