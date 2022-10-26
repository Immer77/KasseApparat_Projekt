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

    private final ListView<Produkt> lvwProdukter = new ListView<>();
    private final ListView<OrdreKategori> lvwKategorier = new ListView<>();
    private final ArrayList<Kategori> kategorier = new ArrayList<>();

    private void initContent(GridPane pane) {

        // pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblNameKategori = new Label("Ordrekategori:");
        pane.add(lblNameKategori, 0, 0);

        Label lblNameProdukt = new Label("Produkt:");
        pane.add(lblNameProdukt, 1, 0);

        pane.add(lvwKategorier, 1, 1);
        lvwKategorier.setPrefWidth(200);
        lvwKategorier.setPrefHeight(200);
        lvwKategorier.getItems().setAll(persons);

    }



    }
