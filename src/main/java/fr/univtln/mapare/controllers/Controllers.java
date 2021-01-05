package fr.univtln.mapare.controllers;

import fr.univtln.mapare.entities.Yeargroup;

import java.sql.SQLException;
import java.time.LocalDateTime;

public abstract class Controllers {
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

    public static boolean checkTimeBreak(LocalDateTime dbStart, LocalDateTime dbEnd, LocalDateTime localStart, LocalDateTime localEnd) {
        return (dbStart.isAfter(localStart) && dbStart.isBefore(localEnd)) ||
                (dbEnd.isAfter(localStart) && dbEnd.isBefore(localEnd)) ||
                (dbStart.isBefore(localStart) && dbEnd.isAfter(localEnd));
    }
}