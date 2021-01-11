package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.DefenceDAO;
import fr.univtln.mapare.entities.Defence;
import fr.univtln.mapare.entities.Reservation;
import fr.univtln.mapare.entities.Room;
import fr.univtln.mapare.entities.Student;

import java.sql.SQLException;
import java.time.LocalDateTime;

public abstract class DefenceController {

    private DefenceController() {}

    public static void createDefence(LocalDateTime startDate, LocalDateTime endDate, String label, String memo, Reservation.State state, Room room, Student student) throws SQLException {
        Defence defence = new Defence(-1, startDate, endDate, label, memo, state, room, student);
        try(DefenceDAO defenceDAO = new DefenceDAO()) {
            defenceDAO.persist(defence);
        }
    }
}
