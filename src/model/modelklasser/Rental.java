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
}
