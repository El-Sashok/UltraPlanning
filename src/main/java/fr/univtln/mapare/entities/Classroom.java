package fr.univtln.mapare.entities;

public class Classroom {
    private String building;
    private int number;
    private int seats;
    private String label = "";

    //Constructors
    public Classroom(String building, int number, int seats) {
        this.building = building;
        this.number = number;
        this.seats = seats;
    }

    public Classroom(String building, int number, int seats, String label) {
        this(building, number, seats);
        this.label = label;
    }

    //Getters & Setters
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
