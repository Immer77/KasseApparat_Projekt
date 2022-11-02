package model.modelklasser;

import java.time.LocalDate;

public class Rental extends Order{

    private String name;
    private String description;
    private LocalDate startDate;


    public Rental(String name, String description, LocalDate startDate) {
        super();
        this.name = name;
        this.description = description;
        this.startDate = startDate;
    }


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
        return name + "(" + startDate + ')';
    }
}
