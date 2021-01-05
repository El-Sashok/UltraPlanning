package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ReservationDAO;
import fr.univtln.mapare.daos.RoomDAO;
import fr.univtln.mapare.entities.Reservation;
import fr.univtln.mapare.entities.Room;
import fr.univtln.mapare.entities.Teacher;
import fr.univtln.mapare.exceptions.TimeBreakExceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.TimeBreakExceptions.RoomTimeBreakException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class ReservationController {

    public static void loadReservations() throws SQLException {
        ReservationDAO reservationDAO = new ReservationDAO();
        reservationDAO.findAll();
        reservationDAO.close();
    }

    public static void createReservation(LocalDateTime startDate, LocalDateTime endDate, String label, String memo, Reservation.State state, Room room, List<Teacher> managers) throws SQLException, ManagerTimeBreakException, RoomTimeBreakException {
        for (Reservation r : Reservation.getReservationList()){
            if (r.getState() == Reservation.State.NP) {
                if (Controllers.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate)) {
                    for (Teacher dbTeacher : r.getManagers())
                        for (Teacher LocalTeacher : managers)
                            if (dbTeacher.getId() == LocalTeacher.getId())
                                throw new ManagerTimeBreakException(LocalTeacher);
                    if (r.getRoom().getId() == room.getId())
                        throw new RoomTimeBreakException(room);
                }
            }
        }

        Reservation reservation = new Reservation(-1, startDate, endDate, label, memo, state, room);
        for (Teacher t : managers) reservation.addTeacher(t);
        ReservationDAO reservationDAO = new ReservationDAO();
        reservationDAO.persist(reservation);
        reservationDAO.close();
    }

}
