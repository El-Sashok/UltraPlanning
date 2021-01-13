package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.DefenceDAO;
import fr.univtln.mapare.entities.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class DefenceController {

    private DefenceController() {}

    /**
     * Permet de créer une réservation de soutenance si il n'y a aucune collisions avec une autre reservation puis la sauvegarde dans la base de donnée
     * @param startDate Début de la soutenance
     * @param endDate Fin de la soutenance
     * @param label Intitulé de la soutenance
     * @param memo Texte complémentaire
     * @param state Étant de la réservation
     * @param room  Salle dans laquelle se déroule la soutenance
     * @param student Étudiant qui présente la soutenance
     * @throws SQLException Exception SQL
     */
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
