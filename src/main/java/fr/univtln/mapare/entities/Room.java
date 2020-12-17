package fr.univtln.mapare.entities;

public class Room {
    private final long id;
    private String building;
    private int number;
    private int seats;
    private String label;
    private String info;

    //Constructor
    public Room(long id, String building, int number, int seats, String label, String info) {
        this.id = id;
        this.building = building;
        this.number = number;
        this.seats = seats;
        this.label = label;
        this.info = info;
    }

    //Getters & Setters
    public long getId() {
        return id;
    }

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    //Methods
    @Override
    public String toString() {
        return "Room{" +
                "building='" + building + '\'' +
                ", number=" + number +
                ", seats=" + seats +
                ", label='" + label + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
