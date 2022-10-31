package model.modelklasser;

public class Situation {
    // Field variables
    private String name;

    // Constructor
    public Situation(String name) {
        this.name = name;
    }

    // Getter
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}


