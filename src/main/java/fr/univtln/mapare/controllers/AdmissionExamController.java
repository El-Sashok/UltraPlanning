package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.AdmissionExamDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.StudentTimeBreakException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class AdmissionExamController {

    private AdmissionExamController() {}

    public static void createAdmissionExam(LocalDateTime startDate, LocalDateTime endDate, String label, String memo,
                                           Reservation.State state, Room room, AdmissionExamLabel examLabel,
                                           List<Teacher> managers, List<Student> students) throws SQLException,
            ManagerTimeBreakException, RoomTimeBreakException, StudentTimeBreakException, IncorrectEndHourException {
        checkCollision(startDate, endDate, room, managers, students);

        AdmissionExam aem = new AdmissionExam(-1, startDate, endDate, label, memo, state, room, examLabel);
        aem.setManagers(managers);
        aem.setStudents(students);
        try (AdmissionExamDAO aemDAO = new AdmissionExamDAO()) {
            aemDAO.persist(aem);
        }
    }

    /**
     * Permet de vérifier qu'il n'y à pas de collision avec une autre réservation
     * @param startDate Début du cours
     * @param endDate Fin du cours
     * @param room Salle dans laquelle se déroule le cours
     * @param managers Professeur en charge de la classe
     * @param students Liste des étudiants inscrit au concours
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cette horaire
     * @throws RoomTimeBreakException La salle est déjà occupé pendant cette horaire
     * @throws StudentTimeBreakException Un étudiant est déjà occupé pendant cette horaire
     */
    private static void checkCollision(LocalDateTime startDate, LocalDateTime endDate, Room room, List<Teacher> managers, List<Student> students) throws ManagerTimeBreakException, RoomTimeBreakException, StudentTimeBreakException, IncorrectEndHourException {
        ControllerTools.checkStartAfterEnd(startDate, endDate);

        for (Teacher t: managers)
            for (Constraint c : t.getConstraints())
                ConstraintController.checkConflicts(startDate, endDate, c);

        for (Reservation r : Reservation.getReservationList()) {
            if (r.isNP() && ControllerTools.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate)) {
                if (room.equals(r.getRoom()))
                    throw new RoomTimeBreakException(room);
                for (Teacher t : managers)
                    if (r.getManagers().contains(t))
                        throw new ManagerTimeBreakException(t);
                if (r instanceof Defence && students.contains(((Defence) r).getStudent()))
                    throw new StudentTimeBreakException(((Defence) r).getStudent());
                if (r instanceof Lesson)
                    for (Student s : students) {
                        for (Group g : ((Lesson) r).getGroups()) {
                            if (g.getStudents().contains(s))
                                throw new StudentTimeBreakException(s);
                        }
                    }
                else if (r instanceof AdmissionExam) {
                    for (Student s : students) {
                        if (((AdmissionExam) r).getStudents().contains(s))
                            throw new StudentTimeBreakException(s);
                    }
                }
            }
        }
    }
}
