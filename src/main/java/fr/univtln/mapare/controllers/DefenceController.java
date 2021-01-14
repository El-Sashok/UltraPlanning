package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.DefenceDAO;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.StudentTimeBreakException;

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
    public static void createDefence(LocalDateTime startDate, LocalDateTime endDate, String label, String memo,
                                     Reservation.State state, Room room, Student student, List<Teacher> managers)
            throws SQLException, RoomTimeBreakException, ManagerTimeBreakException, StudentTimeBreakException {
        for (Reservation r : Reservation.getReservationList()) {
            if (r.isNP() && Controllers.checkTimeBreak(r.getStartDate(), r.getEndDate(), startDate, endDate)) {
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

        Defence defence = new Defence(-1, startDate, endDate, label, memo, state, room, student);
        defence.setManagers(managers);
        try(DefenceDAO defenceDAO = new DefenceDAO()) {
            defenceDAO.persist(defence);
        }
    }
}
