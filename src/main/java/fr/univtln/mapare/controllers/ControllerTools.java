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

    public static void checkStartAfterEnd(LocalDateTime start, LocalDateTime end) throws IncorrectEndHourException {
        if (start.isAfter(end) || start.isEqual(end)){
            throw new IncorrectEndHourException();
        }
    }

    public static boolean checkTimeBreak(LocalDateTime dbStart, LocalDateTime dbEnd, LocalDateTime localStart, LocalDateTime localEnd) {
        return (dbStart.isAfter(localStart) && dbStart.isBefore(localEnd)) ||
                (dbEnd.isAfter(localStart) && dbEnd.isBefore(localEnd)) ||
                (dbStart.isBefore(localStart) && dbEnd.isAfter(localEnd)) ||
                dbStart.isEqual(localStart) || dbEnd.isEqual(localEnd);
    }

    public static boolean checkTimeBreak(Reservation dbRes, Reservation newRes) {
        return checkTimeBreak(dbRes.getStartDate(), dbRes.getEndDate(), newRes.getStartDate(), newRes.getEndDate());
    }
}
