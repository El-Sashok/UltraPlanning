package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ReservationDAO;
import fr.univtln.mapare.daos.RoomDAO;
import fr.univtln.mapare.entities.Room;

import java.sql.SQLException;

public abstract class ReservationController {

    public static void loadReservations() throws SQLException {
        ReservationDAO reservationDAO = new ReservationDAO();
        reservationDAO.findAll();
        reservationDAO.close();
    }

}
