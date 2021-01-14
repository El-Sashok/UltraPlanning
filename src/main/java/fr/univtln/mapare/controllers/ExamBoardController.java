package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ExamBoardDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.StudentTimeBreakException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class ExamBoardController {

    private ExamBoardController() {}

    /**
     * Permet de créer une réservation d'un jury si il n'y a aucune collision avec une autre reservation puis la sauvegarde dans la base de donnée
     * @param startDate Début de la réservation
     * @param endDate Fin de la réservation
     * @param label Intitulé de la réservation
     * @param memo Informations complémentaires
     * @param state État de la réservation
     * @param room  Salle dans laquelle se déroule le jury
     * @param managers Liste des enseignants en charge de la réservation
     * @param yg Promotion concernée
     * @throws SQLException Exception SQL
     * @throws RoomTimeBreakException La salle est déjà occupée pendant cet horaire
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cet horaire
     * @throws IncorrectEndHourException La date de début se situe apres la date de fin
     */
    public static void createExamBoard(LocalDateTime startDate, LocalDateTime endDate, String label, String memo,
                                       Reservation.State state, Room room, Yeargroup yg, List<Teacher> managers)
            throws SQLException, RoomTimeBreakException, ManagerTimeBreakException, IncorrectEndHourException {
        ReservationController.checkCollision(startDate, endDate, room, managers);

        ExamBoard examBoard = new ExamBoard(-1, startDate, endDate, label, memo, state, room, yg);
        examBoard.setManagers(managers);
        try (ExamBoardDAO ebDAO = new ExamBoardDAO()) {
            ebDAO.persist(examBoard);
        }
    }

    /**
     * Permet de changer la promotion concernée par le jury
     * @param examBoard Le jury
     * @param yg Promotion concernée
     * @throws SQLException Exception SQL
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeYeargroup(ExamBoard examBoard, Yeargroup yg) throws SQLException, NotChangedException {
        if (examBoard.getYeargroup().equals(yg))
            throw new NotChangedException(examBoard);

        examBoard.setYeargroup(yg);
        try (ExamBoardDAO examBoardDAO = new ExamBoardDAO()) {
            examBoardDAO.update(examBoard);
        }
    }
}
