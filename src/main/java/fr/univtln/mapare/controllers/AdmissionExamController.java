package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.AdmissionExamDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.StudentTimeBreakException;
import fr.univtln.mapare.exceptions.updateexceptions.EmptyAttributeException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class AdmissionExamController {

    private AdmissionExamController() {}

    /**
     * Permet de créer une réservation de concours
     * @param startDate Début du concours
     * @param endDate Fin du concours
     * @param label Intitulé de la réservation
     * @param memo Informations complémentaires
     * @param state État de la réservation
     * @param room  Salle dans laquelle se déroule le concours
     * @param managers Liste des enseignants en charge de la réservation
     * @param students Liste d'étudiants qui passent le concours
     * @param examLabel Intitulé du concours
     * @throws SQLException Exception SQL
     * @throws RoomTimeBreakException La salle est déjà occupée pendant cet horaire
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cet horaire
     * @throws StudentTimeBreakException Un étudiant est déjà occupé pendant cet horaire
     */
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
     * Permet de vérifier qu'il n'y ait pas de collision avec une autre réservation
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
                checkCollisionStudents(r, students);
            }
        }
    }

    /**
     * Permet de vérifier qu'il n'y ait pas de collision avec une autre réservation concernant les étudiants
     * @param r La réservation
     * @param students Liste des étudiants inscrits au concours
     * @throws StudentTimeBreakException Un étudiant est déjà occupé pendant cette horaire
     */
    private static void checkCollisionStudents(Reservation r, List<Student> students) throws StudentTimeBreakException {
        if (r instanceof Defence ) {
            if (students.contains(((Defence) r).getStudent()))
                throw new StudentTimeBreakException(((Defence) r).getStudent());
        }
        else if (r instanceof Lesson) {
            for (Student s : students)
                for (Group g : ((Lesson) r).getGroups())
                    if (g.getStudents().contains(s))
                        throw new StudentTimeBreakException(s);
        }
        else if (r instanceof AdmissionExam) {
            for (Student s : students)
                if (((AdmissionExam) r).getStudents().contains(s))
                    throw new StudentTimeBreakException(s);
        }
    }

    /**
     * Permet de changer l'intitulé du concours d'une réservation de type concours
     * @param admissionExam Le concours
     * @param examLabel Intitulé du concours
     * @throws SQLException Exception SQL
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeExamLabel(AdmissionExam admissionExam, AdmissionExamLabel examLabel) throws SQLException, NotChangedException {
        if (admissionExam.getAdmissionExamLabel().equals(examLabel))
            throw new NotChangedException(admissionExam);

        admissionExam.setAdmissionExamLabel(examLabel);
        try (AdmissionExamDAO admissionExamDAO = new AdmissionExamDAO()) {
            admissionExamDAO.update(admissionExam);
        }
    }

    /**
     * Permet de changer les étudiants d'un concours
     * @param admissionExam Le concours
     * @param students Nouvelle liste d'étudiants passant le concours
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException students ne contient pas d'étudiant
     * @throws NotChangedException Aucune modification apportée
     * @throws StudentTimeBreakException Un des étudiants dans students n'est pas disponible
     */
    public static void changeStudents(AdmissionExam admissionExam, List<Student> students) throws SQLException, EmptyAttributeException, NotChangedException, StudentTimeBreakException {
        if (students.size() == 0)
            throw new EmptyAttributeException("changeStudents", admissionExam);
        if (students.size() == admissionExam.getStudents().size())
            if (admissionExam.getStudents().containsAll(students))
                throw new NotChangedException(admissionExam);

        for (Reservation r : Reservation.getReservationList()) {
            if (!r.equals(admissionExam) && r.isNP() && ControllerTools.checkTimeBreak(r, admissionExam)) {
                checkCollisionStudents(r, students);
            }
        }

        admissionExam.setStudents(students);
        try (AdmissionExamDAO admissionExamDAO = new AdmissionExamDAO()) {
            admissionExamDAO.updateStudents(admissionExam);
        }
    }
}
