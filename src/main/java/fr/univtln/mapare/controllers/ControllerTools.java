package fr.univtln.mapare.controllers;

import fr.univtln.mapare.entities.Reservation;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public abstract class ControllerTools {

    private ControllerTools() {}

    /**
     * Fonction d'initialisation : Elle permet d'initialiser toutes les listes nécessaire au bon fonctionnement du programme
     * @throws SQLException Exception SQL
     */
    public static void loadDB() throws SQLException {
        GroupController.loadGroups();
        ModuleController.loadModules();
        RoomController.loadRooms();
        StudentController.loadStudents();
        TeacherController.loadTeachers();
        ReservationController.loadReservations();
        YeargroupController.loadYeargroup();
        AdmissionExamLabelController.loadLabels();
    }

    /**
     * Permet de vérifier si la date de début est bien avant la date de fin
     * @param start Date de début
     * @param end Date de fin
     * @throws IncorrectEndHourException
     */
    public static void checkStartAfterEnd(LocalDateTime start, LocalDateTime end) throws IncorrectEndHourException {
        if (start.isAfter(end) || start.isEqual(end)){
            throw new IncorrectEndHourException();
        }
    }

    /**
     * Permet de vérifier que 2 horaires ne se chevauchent pas
     * @param dbStart Date de début
     * @param dbEnd Date de fin
     * @param localStart Date de début à vérifier
     * @param localEnd Date de fin à vérifier
     * @return True si les dates se chevauchent
     */
    public static boolean checkTimeBreak(LocalDateTime dbStart, LocalDateTime dbEnd, LocalDateTime localStart, LocalDateTime localEnd) {
        return (dbStart.isAfter(localStart) && dbStart.isBefore(localEnd)) ||
                (dbEnd.isAfter(localStart) && dbEnd.isBefore(localEnd)) ||
                (dbStart.isBefore(localStart) && dbEnd.isAfter(localEnd)) ||
                dbStart.isEqual(localStart) || dbEnd.isEqual(localEnd);
    }

    /**
     * Permet de vérifier que 2 réservations ne se chevauchent pas
     * @param dbRes Une Réservation
     * @param newRes La nouvelle réservation à tester
     * @return True si les deux réservations se chevauchent
     */
    public static boolean checkTimeBreak(Reservation dbRes, Reservation newRes) {
        return checkTimeBreak(dbRes.getStartDate(), dbRes.getEndDate(), newRes.getStartDate(), newRes.getEndDate());
    }
}
