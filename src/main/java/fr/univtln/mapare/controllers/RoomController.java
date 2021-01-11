package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.RoomDAO;
import fr.univtln.mapare.entities.Room;

import java.sql.SQLException;

public abstract class RoomController {

    private RoomController() {}

    public static void loadRooms() throws SQLException {
        try (RoomDAO roomDAO = new RoomDAO()) {
            roomDAO.findAll();
        }
    }

    public static void remove(Room room) throws SQLException {
        try (RoomDAO roomDAO = new RoomDAO()) {
            roomDAO.remove(room.getId());
        }
        Room.popRoomInList(room);
    }

    public static void createRoom(String building, int number, int capacity, String label, String info) throws SQLException {
        Room room = new Room(-1, building, number, capacity, label, info);
        try (RoomDAO roomDAO = new RoomDAO()) {
            roomDAO.persist(room);
        }
    }
}
