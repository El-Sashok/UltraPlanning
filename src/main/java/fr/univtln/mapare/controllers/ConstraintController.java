package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.ConstraintDAO;
import fr.univtln.mapare.entities.Constraint;
import fr.univtln.mapare.entities.Teacher;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public abstract class ConstraintController {

    private ConstraintController() {}

    public static void createConstraint(LocalDate day, LocalTime start, LocalTime end, Teacher t) throws SQLException {
        try (ConstraintDAO cDAO = new ConstraintDAO()) {
            Constraint newc = cDAO.persist(new Constraint(-1, day, start, end, t));
            newc.setTeacher(t);
        }
    }

    public static List<Constraint> findConstraints(Teacher t) throws SQLException {
        try (ConstraintDAO cDAO = new ConstraintDAO()) {
            return cDAO.findByTeacher(t.getId());
        }
    }

    public static void removeConstraint(Constraint c) throws SQLException {
        try (ConstraintDAO cDAO = new ConstraintDAO()) {
            cDAO.remove(c.getId());
            c.getTeacher().getConstraints().remove(c);
            Constraint.popConstraintInList(c);
        }
    }
}
