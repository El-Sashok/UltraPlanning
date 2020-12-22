package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Constraint;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class ConstraintDAO extends AbstractDAO<Constraint> {

    public ConstraintDAO() throws SQLException {
        super("INSERT INTO CONSTRAINTS(TEACHER, START, END) VALUES(?,?,?)",
                "UPDATE CONSTRAINTS SET TEACHER=?, START=?, END=? WHERE ID=?");
    }

    @Override
    protected Constraint fromResultSet(ResultSet resultSet) throws SQLException {
        for (Constraint c: Constraint.getConstraintList()) {
            if (c.getId() == resultSet.getLong("ID"))
                return c;
        }
        return new Constraint(
                resultSet.getLong("id"),
                resultSet.getDate("START"),
                resultSet.getDate("END"));
    }

    @Override
    public Constraint persist(Constraint constraint) {
        return null;
    }

    public Constraint persist(Constraint constraint, Long teacherID) throws SQLException {
        populate(persistPS, constraint, teacherID);
        return super.persist();
    }

    @Override
    public void update(Constraint constraint) throws SQLException {
        populate(updatePS, constraint, null);
        updatePS.setLong(4, constraint.getId());
        super.update();
    }

    private void populate(PreparedStatement popPS, Constraint constraint, Long teacherID) throws SQLException {
        popPS.setLong(1, teacherID);
        popPS.setDate(2, new java.sql.Date(constraint.getStartDate().getTime()));
        popPS.setDate(3, new java.sql.Date(constraint.getEndDate().getTime()));
    }

    @Override
    public String getTableName() {
        return "STUDENT";
    }
}