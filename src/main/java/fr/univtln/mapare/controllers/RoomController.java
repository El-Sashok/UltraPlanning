package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.RoomDAO;
import fr.univtln.mapare.entities.Room;

import java.sql.SQLException;

public abstract class RoomController {

    public static void loadRooms() throws SQLException {
        RoomDAO roomDAO = new RoomDAO();
        roomDAO.findAll();
        roomDAO.close();
    }

    public static void remove(Room room) throws SQLException {
        RoomDAO roomDAO = new RoomDAO();
        roomDAO.remove(room.getId());
        roomDAO.close();
        Room.popRoomInList(room);
    }

    public static void createRoom(String building, int number, int capacity, String label, String info) throws SQLException {
        Room room = new Room(-1, building, number, capacity, label, info);
        RoomDAO roomDAO = new RoomDAO();
        roomDAO.persist(room);
        roomDAO.close();
    }
}
