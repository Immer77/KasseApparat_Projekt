package gui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import model.controller.OrderController;
import model.modelklasser.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
    private Accordion accProductsSold;


    //Constructors ---------------------------------------------------------------------------------------------------
    public StatTab() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);
        this.controller = new OrderController(Storage.getStorage());

        accProductsSold = new Accordion();
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

        //-----Orderstatistics-----
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
        gpOrderStats.add(lblRentals,0,1);
        gpOrderStats.add(lblRentalsResult, 1,1);
        gpOrderStats.add(lblTours,0,2);
        gpOrderStats.add(lblTourResult,1,2);
        gpOrderStats.setBackground(Background.fill(Color.WHITE));
        gpOrderStats.setBorder(Border.stroke(Color.BLACK));
        gpOrderStats.setPadding(new Insets(20));
        gpOrderStats.setHgap(10);
        gpOrderStats.setVgap(10);
        this.add(gpOrderStats,0,2);
        ColumnConstraints orderColumn1 = new ColumnConstraints();
        orderColumn1.setPercentWidth(70);
        gpOrderStats.getColumnConstraints().add(orderColumn1);
        ColumnConstraints orderColumn2 = new ColumnConstraints();
        orderColumn2.setPercentWidth(30);
        gpOrderStats.getColumnConstraints().add(orderColumn2);

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
        ColumnConstraints punchColumn1 = new ColumnConstraints();
        punchColumn1.setPercentWidth(70);
        gpPunchStats.getColumnConstraints().add(punchColumn1);
        ColumnConstraints punchColumn2 = new ColumnConstraints();
        punchColumn2.setPercentWidth(30);
        gpPunchStats.getColumnConstraints().add(punchColumn2);
        this.add(gpPunchStats,0,3);

        //-----Products sold overview-----
        Label lblProductsSold = new Label("Produkter solgt i denne periode:");
        this.add(lblProductsSold, 2,1);



    }

    public void updateControls() {
        accProductsSold.getPanes().clear();
        Map<Product, Integer> soldProductsMap = new HashMap<>();

        int allOrders = 0;
        int tours = 0;
        int rentals = 0;

        int punchCardsSold = 0;
        int punchesSpent = 0;

        for (Order order : controller.getOrders()) {
            LocalDate endDate = order.getEndDate();

            if (endDate.isAfter(dpckFrom.getValue().minusDays(1)) && endDate.isBefore(dpckTo.getValue().plusDays(1))) {
                allOrders++;
                if (order instanceof Tour) {
                    tours++;
                } else if (order instanceof Rental) {
                    rentals++;
                }

                for (OrderLine orderLine : order.getOrderLines()) {
                    if (controller.getProductCategories().get(0).getProducts().contains(orderLine.getPrice().getProduct())) {
                        punchCardsSold += orderLine.getAmount();
                    }
                    if (orderLine.getPrice().getUnit().equals(Unit.Klip)) {
                        punchesSpent += orderLine.getAmount() * orderLine.getPrice().getValue();
                    }

                    Product currentProduct = orderLine.getPrice().getProduct();
                    if (soldProductsMap.containsKey(currentProduct)) {
                        soldProductsMap.put(currentProduct, soldProductsMap.get(orderLine.getPrice().getProduct())+orderLine.getAmount());
                    } else {
                        soldProductsMap.put(currentProduct, orderLine.getAmount());
                    }

                }



            }
        }

        lblAllOrdersResult.setText(""+allOrders);
        lblTourResult.setText(""+tours);
        lblRentalsResult.setText(""+rentals);

        lblCardsSoldResult.setText(""+punchCardsSold);
        lblPunchesSpentResult.setText(""+punchesSpent);


        if (soldProductsMap.size() == 0) {
            Label lblNoProductsSold = new Label("Ingen produkter solgt i denne periode");
            this.add(lblNoProductsSold,2,2);
        } else {
            accProductsSold = new Accordion();
            accProductsSold.setBorder(Border.stroke(Color.BLACK));
            this.add(accProductsSold, 2,2,1,3);

            for (ProductCategory category : controller.getProductCategories()) {
                VBox vbxSoldProductsInCategory = new VBox();
                vbxSoldProductsInCategory.setBackground(Background.fill(Color.WHITE));
                int soldInCategoryCounter = 0;

                for (Product product : soldProductsMap.keySet()) {
                    if (category.getProducts().contains(product)) {
                        soldInCategoryCounter += soldProductsMap.get(product);

                        Label lblName = new Label(product.getName());
                        if (!product.getDescription().isBlank()) {
                            lblName.setText(lblName.getText() + " ("+product.getDescription()+")");

                        }

                        Label lblAmount = new Label(""+soldProductsMap.get(product));
                        lblAmount.setAlignment(Pos.BASELINE_RIGHT);


                        BorderPane bpListItem = new BorderPane(null,null,lblAmount,null,lblName);
                        vbxSoldProductsInCategory.getChildren().add(bpListItem);

                    }
                }

                if (!vbxSoldProductsInCategory.getChildren().isEmpty()) {
                    TitledPane tpCategory = new TitledPane(category.getTitle()+ " ("+soldInCategoryCounter+")", vbxSoldProductsInCategory);
                    accProductsSold.getPanes().add(tpCategory);
                }

            }
        }

        accProductsSold.getPanes().get(0).setExpanded(true);
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
