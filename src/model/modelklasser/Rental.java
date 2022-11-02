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


    @Override
    public double calculateSumPriceForUnit(Unit unit){
        double sumPrice = 0.0;
        if(unit.equals(Unit.DKK)){
            for (OrderLine orderLine : super.getOrderLines()){
                if (orderLine.getPrice().getUnit().equals(unit)) {
                    sumPrice += orderLine.calculateOrderLinePrice();
                }
            }
        }
        else{
            throw new IllegalArgumentException("Can't calculate price for rental in punches");
        }
        return sumPrice;
    }

    @Override
    public String toString() {
        return name + "(" + startDate + ')';
    }
}
