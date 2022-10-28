package model.modelklasser;

public class Situation {
    private String name;

    public Situation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}


