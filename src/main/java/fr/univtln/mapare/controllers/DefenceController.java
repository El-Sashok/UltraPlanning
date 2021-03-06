package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.DefenceDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.StudentTimeBreakException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public abstract class DefenceController {

    private DefenceController() {}
    /**
     * Permet de créer une réservation de soutenance si il n'y a aucune collision avec une autre reservation puis la sauvegarde dans la base de donnée
     * @param startDate Début de la soutenance
     * @param endDate Fin de la soutenance
     * @param label Intitulé de la soutenance
     * @param memo Informations complémentaires
     * @param state État de la réservation
     * @param room  Salle dans laquelle se déroule la soutenance
     * @param managers Liste des enseignants en charge de la réservation
     * @param student Étudiant qui présente la soutenance
     * @throws SQLException Exception SQL
     * @throws RoomTimeBreakException La salle est déjà occupée pendant cet horaire
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cet horaire
     * @throws StudentTimeBreakException Un étudiant est déjà occupé pendant cet horaire
     * @throws IncorrectEndHourException La date de début se situe apres la date de fin
     */
    public static void createDefence(LocalDateTime startDate, LocalDateTime endDate, String label, String memo,
                                     Reservation.State state, Room room, Student student, List<Teacher> managers)
            throws SQLException, RoomTimeBreakException, ManagerTimeBreakException, StudentTimeBreakException, IncorrectEndHourException {
        checkCollisions(startDate, endDate, room, student, managers);

        Defence defence = new Defence(-1, startDate, endDate, label, memo, state, room, student);
        defence.setManagers(managers);
        try(DefenceDAO defenceDAO = new DefenceDAO()) {
            defenceDAO.persist(defence);
        }
    }

    /**
     * Permet de vérifier qu'il n'y à pas de collision avec une autre réservation
     * @param startDate Début du cours
     * @param endDate Fin du cours
     * @param room Salle dans laquelle se déroule le cours
     * @param student L'étudiant inscrit à la soutenance
     * @param managers Professeur en charge de la classe
     * @throws ManagerTimeBreakException Un enseignant est déjà occupé pendant cette horaire
     * @throws RoomTimeBreakException La salle est déjà occupé pendant cette horaire
     * @throws StudentTimeBreakException Un étudiant est déjà occupé pendant cette horaire
     * @throws IncorrectEndHourException La date de début se situe apres la date de fin
     */
    private static void checkCollisions(LocalDateTime startDate, LocalDateTime endDate, Room room, Student student, List<Teacher> managers) throws ManagerTimeBreakException, RoomTimeBreakException, StudentTimeBreakException, IncorrectEndHourException {
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
                if (r instanceof Defence && student.equals(((Defence) r).getStudent()))
                    throw new StudentTimeBreakException(((Defence) r).getStudent());
                if (r instanceof Lesson)
                    for (Group g : ((Lesson) r).getGroups()) {
                        if (g.getStudents().contains(student))
                            throw new StudentTimeBreakException(student);
                    }
                else if (r instanceof AdmissionExam) {
                    if (((AdmissionExam) r).getStudents().contains(student))
                        throw new StudentTimeBreakException(student);
                }
            }
        }
    }

    /**
     * Permet de changer l'étudiant présentant la soutenance
     * @param defence La soutenance
     * @param student Nouvel étudiant présentant la soutenance
     * @throws SQLException Exception SQL
     * @throws NotChangedException Aucune modification apportée
     * @throws StudentTimeBreakException L'étudiant student n'est pas disponible
     */
    public static void changeStudent(Defence defence, Student student) throws SQLException, NotChangedException, StudentTimeBreakException {
        if (defence.getStudent().equals(student))
            throw new NotChangedException(defence);

        for (Reservation r : Reservation.getReservationList()) {
            if (!r.equals(defence) && r.isNP() && ControllerTools.checkTimeBreak(r, defence)) {
                if (r instanceof Defence) {
                    if (student.equals(((Defence) r).getStudent()))
                        throw new StudentTimeBreakException(((Defence) r).getStudent());
                }
                else if (r instanceof Lesson)
                    for (Group g : ((Lesson) r).getGroups()) {
                        if (g.getStudents().contains(student))
                            throw new StudentTimeBreakException(student);
                    }
                else if (r instanceof AdmissionExam) {
                    if (((AdmissionExam) r).getStudents().contains(student))
                        throw new StudentTimeBreakException(student);
                }
            }
        }

        defence.setStudent(student);
        try (DefenceDAO defenceDAO = new DefenceDAO()) {
            defenceDAO.update(defence);
        }
    }


}
