package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import model.controller.OrderController;
import model.modelklasser.Order;
import model.modelklasser.Rental;
import model.modelklasser.Tour;
import storage.Storage;

import java.time.LocalDate;

public class StatTab extends GridPane {
    //Fields ---------------------------------------------------------------------------------------------------------
    private OrderControllerInterface controller;
    private DatePicker dpckFrom;
    private DatePicker dpckTo;
    private Label lblAllOrdersResult;
    private Label lblRentalsResult;
    private Label lblTourResult;
    private Label lblCardsSoldResult;
    private Label lblPunchesSpentResult;


    //Constructors ---------------------------------------------------------------------------------------------------
    public StatTab() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);
        this.controller = new OrderController(Storage.getStorage());

        resetTab();
    }

    //Methods - Get, Set & Add ---------------------------------------------------------------------------------------


    //Methods - Other ------------------------------------------------------------------------------------------------
    public void initContent() {
//-----Datepickers-----
        dpckFrom = new DatePicker(LocalDate.now());
        ChangeListener<LocalDate> fromListener = (ov, o, n) -> this.datesChanged();
        dpckFrom.valueProperty().addListener(fromListener);
        dpckTo = new DatePicker(LocalDate.now());
        ChangeListener<LocalDate> toListener = (ov, o, n) -> this.datesChanged();
        dpckTo.valueProperty().addListener(toListener);

        HBox hbxDatepickers = new HBox(dpckFrom, dpckTo);
        hbxDatepickers.setSpacing(10);
        this.add(hbxDatepickers, 0, 0);

        //-----Orderstatisticks-----
        Label lblAllOrders = new Label("Antal salg i alt:");
        lblAllOrders.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        lblAllOrdersResult = new Label();
        lblAllOrdersResult.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, Font.getDefault().getSize()));
        lblAllOrdersResult.setAlignment(Pos.BASELINE_CENTER);

        Label lblRentals = new Label("Heraf Udlejninger");
        lblRentals.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        lblRentalsResult = new Label();
        lblRentalsResult.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, Font.getDefault().getSize()));
        lblRentalsResult.setAlignment(Pos.BASELINE_CENTER);
        Label lblTours = new Label("Heraf Rundvisninger");
        lblTours.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        lblTourResult = new Label();
        lblTourResult.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, Font.getDefault().getSize()));
        lblTourResult.setAlignment(Pos.BASELINE_CENTER);



        GridPane gpOrderStats = new GridPane();
        gpOrderStats.add(lblAllOrders, 0,0);
        gpOrderStats.add(lblAllOrdersResult,1,0);
        gpOrderStats.add(lblRentals,0,2);
        gpOrderStats.add(lblRentalsResult, 1,2);
        gpOrderStats.add(lblTours,0,3);
        gpOrderStats.add(lblTourResult,1,3);
        gpOrderStats.setBackground(Background.fill(Color.WHITE));
        gpOrderStats.setBorder(Border.stroke(Color.BLACK));
        gpOrderStats.setPadding(new Insets(20));
        gpOrderStats.setHgap(10);
        gpOrderStats.setVgap(10);
        this.add(gpOrderStats,0,1);

        //-----Punchcard statistics-----
        Label lblCardsSold = new Label("Klippekort solgt:");
        lblCardsSold.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        lblCardsSoldResult = new Label();
        lblCardsSoldResult.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, Font.getDefault().getSize()));
        lblCardsSoldResult.setAlignment(Pos.BASELINE_CENTER);
        Label lblPunchesSpent = new Label("Klip brugt");
        lblPunchesSpent.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        lblPunchesSpentResult = new Label();
        lblPunchesSpentResult.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, Font.getDefault().getSize()));
        lblPunchesSpentResult.setAlignment(Pos.BASELINE_CENTER);

        GridPane gpPunchStats = new GridPane();
        gpPunchStats.add(lblCardsSold, 0,0);
        gpPunchStats.add(lblCardsSoldResult,1,0);
        gpPunchStats.add(lblPunchesSpent,0,2);
        gpPunchStats.add(lblPunchesSpentResult, 1,2);
        gpPunchStats.setBackground(Background.fill(Color.WHITE));
        gpPunchStats.setBorder(Border.stroke(Color.BLACK));
        gpPunchStats.setPadding(new Insets(20));
        gpPunchStats.setHgap(10);
        gpPunchStats.setVgap(10);
        this.add(gpPunchStats,0,2);

    }

    public void updateControls() {
        int allOrders = 0;
        int tours = 0;
        int rentals = 0;

        for (Order order : controller.getOrders()) {
            LocalDate endDate = order.getEndDate();

            if (endDate.isAfter(dpckFrom.getValue().minusDays(1)) && endDate.isBefore(dpckTo.getValue().plusDays(1))) {
                allOrders++;
                if (order instanceof Tour) {
                    tours++;
                } else if (order instanceof Rental) {
                    rentals++;
                }
            }
        }

        lblAllOrdersResult.setText(""+allOrders);
        lblTourResult.setText(""+tours);
        lblRentalsResult.setText(""+rentals);

    }

    public void resetTab() {
        initContent();

        dpckTo.setValue(LocalDate.now());
        dpckFrom.setValue(LocalDate.now());


        updateControls();
    }

    public void datesChanged() {
        if (dpckFrom.getValue().isAfter(dpckTo.getValue())) {
            Alert illegalDateError = new Alert(Alert.AlertType.ERROR);
            illegalDateError.setHeaderText("Ugyldig Dato");
            illegalDateError.setContentText("\"Fra\" datoen må ikke være senere end \"Til\" datoen");
            illegalDateError.showAndWait();

            dpckFrom.setValue(LocalDate.now());
            dpckTo.setValue(LocalDate.now());
        }

        updateControls();
    }



}
