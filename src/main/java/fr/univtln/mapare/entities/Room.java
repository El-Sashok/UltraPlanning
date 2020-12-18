package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;

public class Room implements Entity {
    private final long id;
    private String building;
    private int number;
    private int capacity;
    private String label;
    private String info;
    private static final List<Room> ROOMS = new ArrayList<>();

    //Constructor
    public Room(long id, String building, int number, int capacity, String label, String info) {
        this.id = id;
        this.building = building;
        this.number = number;
        this.capacity = capacity;
        this.label = label;
        this.info = info;
        ROOMS.add(this);
    }

    //Getters & Setters
    @Override
    public long getId() {
        return id;
    }

    public static List<Room> getRoomsList() {
        return ROOMS;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
                ", seats=" + capacity +
                ", label='" + label + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
