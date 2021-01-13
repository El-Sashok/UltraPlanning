package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room implements Entity {
    private long id;
    private String building;
    private int number;
    private int capacity;
    private String label;
    private String info;
    private static final List<Room> ROOMS = new ArrayList<>();

    @Override
    public String toString() {
        return building + "." + number;
    }

    //Constructor
    public Room(long id, String building, int number, int capacity, String label, String info) {
        this.id = id;
        this.building = building;
        this.number = number;
        this.capacity = capacity;
        this.label = label;
        this.info = info;
        if (id != -1) // To differentiate the ones which are yet in database
            ROOMS.add(this);
    }

    //Getters & Setters
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public static List<Room> getRoomList() {
        return ROOMS;
    }

    public static void popRoomInList(Room room) {
        ROOMS.remove(room);
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
    public String print() {
        return "Room{" +
                "building='" + building + '\'' +
                ", number=" + number +
                ", seats=" + capacity +
                ", label='" + label + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
