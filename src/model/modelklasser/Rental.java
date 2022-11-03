package model.modelklasser;

import net.bytebuddy.asm.Advice;

import java.time.LocalDate;

public class Rental extends Order{

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;


    public Rental(String name, String description, LocalDate endDate) {
        super();
        this.name = name;
        this.description = description;
        this.startDate = LocalDate.now();
        this.endDate = endDate;

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
    public LocalDate getEndDate(){
        return endDate;
    }

    @Override
    public String toString() {
        return "Navn på Udlejer " + name + " Slutdato: " + endDate;
    }
}
