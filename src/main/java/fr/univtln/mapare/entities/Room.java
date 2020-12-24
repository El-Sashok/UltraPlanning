package fr.univtln.mapare.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room {
    private String building;
    private int number;
    private static List<Room> roomList = new ArrayList<Room>(Arrays.asList(new Room[]{new Room("U1", 110),
            new Room("W1", 203)}));

    Room(String b, int n) {
        building = b;
        number = n;
    };

    public String getBuilding() {
        return building;
    }

    public int getNumber() {
        return number;
    }

    public static List<Room> getRoomList() {
        return roomList;
    }

    @Override
    public String toString() {
        return building + "." + number;
    }
}
