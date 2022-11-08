package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.controller.OrderController;
import model.modelklasser.OrderLine;
import model.modelklasser.PaymentMethod;
import model.modelklasser.Tour;
import storage.Storage;

public class EndTourWindow extends Stage {

    private OrderControllerInterface orderController;
    private Tour tour;
    private ChoiceBox<PaymentMethod> chPaymentMethod;
    private VBox orderLineView, unusedOrderLineView, tourInfoVBox;
    private ListView<HBox> lvwTourOrderlines = new ListView<>();
    private HBox buttons;
    private TextField txfName;
    private TextArea txaDescription;
    private TextField endDatePicker;
    private ListView lvwOrderlines = new ListView<>();
    TextField txfFinalPrice = new TextField();


    public EndTourWindow(String title, Stage owner, Tour tour){
        this.tour = tour;
        this.initOwner(owner);
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setMinHeight(300);
        this.setMinWidth(500);
        this.setResizable(false);

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);

        orderController = new OrderController(Storage.getStorage());
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        //adds vbox for tour info
        tourInfoVBox = new VBox();
        tourInfoVBox.setPrefWidth(200);

        //adds labels, textfields, textarea and timepicker for tourinfo
        VBox nameVBox = new VBox();
        Label lblName = new Label("Name");
        txfName =  new TextField();
        txfName.setEditable(false);
        txfName.appendText(tour.getName());
        nameVBox.getChildren().addAll(lblName, txfName);

        VBox descriptionVBox = new VBox();
        Label lblDescription = new Label("Description");
        txaDescription = new TextArea();
        txaDescription.setEditable(false);
        txaDescription.appendText(tour.getDescription());
        descriptionVBox.getChildren().setAll(lblDescription,txaDescription);


        VBox endDateVBox = new VBox();
        endDatePicker = new TextField();
        endDatePicker.setText(String.valueOf(tour.getEndDate()));
        endDatePicker.setEditable(false);
        Label lblEndDate = new Label("Slut dato");
        endDateVBox.getChildren().addAll(lblEndDate,endDatePicker);

        tourInfoVBox.getChildren().addAll(nameVBox,descriptionVBox,endDateVBox);
        pane.add(tourInfoVBox,0,0);

        //Adds a Vbox to hold all OrderLines
        orderLineView = new VBox(new Label("Alle rundvisninger"));
        orderLineView.setPrefWidth(300);
        for (OrderLine ol : tour.getOrderLines()){
            HBox orderline = new HBox();
            Label lblOL = new Label(" "+ol.getAmount() + " " + ol.getPrice().getProduct().toString() + " " + ol.getPrice().getValue() + ol.getPrice().getUnit());
            orderline.getChildren().setAll(lblOL);
            lvwTourOrderlines.getItems().setAll(orderline);
        }
        orderLineView.getChildren().add(lvwTourOrderlines);

        pane.add(lvwOrderlines,1,0);
        lvwOrderlines.getItems().addAll(orderLineView);

        //Confirm and cancel buttons with hbox to hold them
        buttons = new HBox();
        pane.add(buttons,1,2);
        buttons.setSpacing(10);
        buttons.setPrefWidth(200);

        Button btnOK = new Button("Bekræft");
        btnOK.setMaxWidth(Double.MAX_VALUE);
        btnOK.setOnAction(event -> oKAction());
        btnOK.setDefaultButton(true);

        Button btnCancel = new Button("Fortryd");
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setOnAction(event -> cancelAction());
        btnCancel.setCancelButton(true);

        buttons.getChildren().addAll(btnOK,btnCancel);

        //TODO - I oprettelsen af rundvisningen kan man sætte en procentrabat og/eller en fixed price.
        // Enten skal vi gøre så de to værdier er med på slutningen af rundvisningen, eller også skal vi helt
        // fjerne den mulighed på rundvisninger.
        //Add field for the final price
        Label lblFinal = new Label("Endelig Total: ");
        lblFinal.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, Font.getDefault().getSize()));
        pane.add(lblFinal, 4, 9);

        txfFinalPrice.setPadding(new Insets(10, 10, 0, 0));
        pane.add(txfFinalPrice, 5, 9);

        chPaymentMethod = new ChoiceBox<>();
        chPaymentMethod.setPrefWidth(150);
        chPaymentMethod.setMaxWidth(Double.MAX_VALUE);
        chPaymentMethod.getItems().setAll(PaymentMethod.values());
        chPaymentMethod.getSelectionModel().select(0);
        pane.add(chPaymentMethod,1,7);
    }

    //----------------------------------------------------------------------------------------------------
    private void oKAction() {
        orderController.setPaymentMethodForOrder(tour, chPaymentMethod.getSelectionModel().getSelectedItem());
        orderController.saveOrder(tour);
        this.close();
    }

    private void cancelAction() {
        this.close();
    }

}
