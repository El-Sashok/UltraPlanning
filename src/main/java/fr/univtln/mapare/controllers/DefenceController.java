package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.DefenceDAO;
import fr.univtln.mapare.entities.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class DefenceController {

    private DefenceController() {}

    public static void createDefence(LocalDateTime startDate, LocalDateTime endDate, String label, String memo, Reservation.State state, Room room, Student student) throws SQLException {
        Defence defence = new Defence(-1, startDate, endDate, label, memo, state, room, student);
        try(DefenceDAO defenceDAO = new DefenceDAO()) {
            defenceDAO.persist(defence);
        }
    }

    public static void createDefence(Reservation reservation, Student student, List<Teacher> managers) throws SQLException {
        Defence defence = new Defence(reservation, student);
        defence.setManagers(managers);
        try(DefenceDAO defenceDAO = new DefenceDAO()) {
            defenceDAO.persist(defence);
        }
    }
}
