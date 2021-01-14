package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Constraint;
import fr.univtln.mapare.entities.Teacher;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe DAO d'une contrainte d' emploie du temps d'un enseignant
 * @author Equipe MAPARE
 * @version 1.0
 */
@Log
public class ConstraintDAO extends AbstractDAO<Constraint> {
    private final PreparedStatement findConstraintsByTeacher;

    public ConstraintDAO() throws SQLException {
        super("INSERT INTO CONSTRAINTS(TEACHER, START, END, DAY) VALUES(?,?,?,?)",
                "UPDATE CONSTRAINTS SET TEACHER=?, START=?, END=?, DAY=? WHERE ID=?");
        this.findConstraintsByTeacher = connection.prepareStatement("SELECT * FROM CONSTRAINTS WHERE TEACHER=?");
    }

    @Override
    protected Constraint fromResultSet(ResultSet resultSet) throws SQLException {
        for (Constraint c: Constraint.getConstraintList()) {
            if (c.getId() == resultSet.getLong("ID"))
                return c;
        }
        Teacher teacher = null;

        /*  This code block leads to an infinite loop
            try (TeacherDAO tDAO = new TeacherDAO()) {
            teacher = tDAO.find(resultSet.getLong("TEACHER")).get();
        }*/
        return new Constraint(resultSet.getLong("id"),
                resultSet.getDate("DAY").toLocalDate(), resultSet.getTime("START").toLocalTime(),
                resultSet.getTime("END").toLocalTime());
    }

    /**
     * Permet de trouver une liste de contrainte associé à un enseignant
     * @param teacherID Identifiant de l'enseignant auquel on cherche les contraintes
     * @return Une Liste de contraintes
     * @throws SQLException Exception SQL
     */
    public List<Constraint> findByTeacher(long teacherID) throws SQLException {
        List<Constraint> constraints = new ArrayList<>();

        findConstraintsByTeacher.setLong(1, teacherID);
        ResultSet findConstraintsRS = findConstraintsByTeacher.executeQuery();
        if (findConstraintsRS.next()) constraints.add(fromResultSet(findConstraintsRS));

        return constraints;
    }

    @Override
    public Constraint persist(Constraint constraint) throws SQLException {
        long id = -1;
        populate(persistPS, constraint);
        return super.persist();
    }

    @Override
    public void update(Constraint constraint) throws SQLException {
        populate(updatePS, constraint);
        updatePS.setLong(4, constraint.getId());
        super.update();
    }

    /**
     * Permet de peupler les Prepared Statements pour les contraintes
     * @param popPS La Prepared Statements à peupler
     * @param constraint La contrainte associé
     * @throws SQLException Exception SQL
     */
    private void populate(PreparedStatement popPS, Constraint constraint) throws SQLException {
        popPS.setLong(1, constraint.getTeacher().getId());
        popPS.setTime(2, Time.valueOf(constraint.getStart()));
        popPS.setTime(3, Time.valueOf(constraint.getEnd()));
        popPS.setDate(4, Date.valueOf(constraint.getDay()));
    }

    @Override
    public String getTableName() {
        return "CONSTRAINTS";
    }
}