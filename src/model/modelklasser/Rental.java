package model.modelklasser;

import java.time.LocalDate;

//------- SUBCLASS OF ORDER --------//
public class Rental extends Order {
    // Fields variables
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;


    // Constructor
    public Rental(String name, String description, LocalDate endDate, int orderNumber) {
        super(orderNumber);
        this.name = name;
        this.description = description;
        this.startDate = LocalDate.now();
        this.endDate = endDate;

    }

    // Getters and setters--------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }


    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Method to calculate the deposit
     * @return
     */

    public double calculateDeposit() {
        double sumPrice = 0.0;
        for (OrderLine orderLine : super.getOrderLines()) {
            if (orderLine.getPrice().getProduct().getDepositPrice() != null) {
                sumPrice += orderLine.getPrice().getProduct().getDepositPrice().getValue() * orderLine.getAmount();
            }
        }
        return sumPrice;
    }

    @Override
    public String toString() {
        return super.getOrderNumber() + "        Slutdato: " + endDate;
    }
}
