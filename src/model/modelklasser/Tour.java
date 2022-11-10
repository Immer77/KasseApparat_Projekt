package model.modelklasser;

import java.time.LocalDate;
import java.time.LocalTime;

public class Tour extends Order {
    //Fields ---------------------------------------------------------------------------------------------------------
    private String name;
    private String description;
    private LocalTime time;

    //Constructors ---------------------------------------------------------------------------------------------------
    public Tour(LocalDate endDate, LocalTime time, int orderNumber) {
        super(orderNumber);
        this.setEndDate(endDate);
        this.time = time;
    }

    //Methods - Get, Set & Add ---------------------------------------------------------------------------------------

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

    public LocalTime getTime() {
        return time;
    }

    //Methods - Other ------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return super.getOrderNumber() + "        Dato: " + getEndDate();
    }
}
