package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ConstraintDAO;
import fr.univtln.mapare.entities.Constraint;
import fr.univtln.mapare.entities.Teacher;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class ConstraintController {

    private ConstraintController() {}

    /**
     * Permet de créer une contrainte d'emploi du temps pour un professeur
     * @param day Jour de la contrainte
     * @param start Heure de début
     * @param end Heure de fin
     * @param t Enseignant qui a la contrainte
     * @throws SQLException Exception SQL
     */
    public static void createConstraint(LocalDate day, LocalTime start, LocalTime end, Teacher t) throws SQLException {
        Constraint prePersist = new Constraint(-1, day, start, end, t);
        if (!t.getConstraints().contains(prePersist))
            try (ConstraintDAO cDAO = new ConstraintDAO()) {
                Constraint newc = cDAO.persist(prePersist);
                newc.setTeacher(t);
                t.addConstraint(newc);
            }
        else
            throw new IllegalStateException();
    }

    /**
     * Permet de supprimer une contriante
     * @param c La contrainte à supprimer
     * @throws SQLException
     */
    public static void removeConstraint(Constraint c) throws SQLException {
        try (ConstraintDAO cDAO = new ConstraintDAO()) {
            cDAO.remove(c.getId());
            c.getTeacher().getConstraints().remove(c);
            Constraint.popConstraintInList(c);
        }
    }

    /**
     * Permet de vérifier qu'il n'y ait pas de conflits avec une contrainte d'emploi du temps
     * @param startDate Début de la réservation
     * @param endDate Fin de la réservation
     * @param c La contrainte concernée
     * @throws ManagerTimeBreakException
     */
    public static void checkConflicts(LocalDateTime startDate, LocalDateTime endDate, Constraint c)
            throws ManagerTimeBreakException {
        if (c.getDayOfTheWeek().getDayOfWeek().equals(startDate.getDayOfWeek()) &&
                (c.getStartHour().equals(startDate.toLocalTime()) ||
                        (c.getStartHour().isBefore(startDate.toLocalTime()) &&
                                c.getEndHour().isAfter(startDate.toLocalTime())) ||
                        (c.getStartHour().isBefore(endDate.toLocalTime()) &&
                                c.getEndHour().isAfter(endDate.toLocalTime())) ||
                        c.getEndHour().equals(endDate.toLocalTime())))
            throw new ManagerTimeBreakException(c.getTeacher());
    }
}
