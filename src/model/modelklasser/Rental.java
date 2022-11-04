package model.modelklasser;

import java.time.LocalDate;

//------- SUBCLASS OF ORDER --------//
public class Rental extends Order{
    // Fields variables
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;


    // Constructor
    public Rental(String name, String description, LocalDate endDate) {
        super();
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

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public LocalDate getEndDate(){
        return endDate;
    }

    /**
     * Method to calculate the deposit
     * @param unit which unit it uses to calculate the price
     * @return
     */

    public double calculateDeposit(Unit unit){
        double sumPrice = 0.0;
        for (OrderLine orderLine : super.getOrderLines()){
            if (orderLine.getPrice().getProduct().getDepositPrice().getUnit().equals(unit)) {
                sumPrice += orderLine.getPrice().getProduct().getDepositPrice().getValue();
            }
        }
        return sumPrice;
    }

    @Override
    public String toString() {
        return "Udlejer: " + name + " Slutdato: " + endDate;
    }
}
