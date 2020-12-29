package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Constraint;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Log
public class ConstraintDAO extends AbstractDAO<Constraint> {
    public final PreparedStatement findConstraintsByTeacher;

    public ConstraintDAO() throws SQLException {
        super("INSERT INTO CONSTRAINTS(TEACHER, START, END) VALUES(?,?,?)",
                "UPDATE CONSTRAINTS SET TEACHER=?, START=?, END=? WHERE ID=?");
        this.findConstraintsByTeacher = connection.prepareStatement("SELECT * FROM CONSTRAINTS WHERE TEACHER=?");
    }

    @Override
    protected Constraint fromResultSet(ResultSet resultSet) throws SQLException {
        for (Constraint c: Constraint.getConstraintList()) {
            if (c.getId() == resultSet.getLong("ID"))
                return c;
        }
        return new Constraint(resultSet.getLong("id"),
                resultSet.getTimestamp("START").toLocalDateTime(),
                resultSet.getTimestamp("END").toLocalDateTime(),
        resultSet.getLong("TEACHER"));
    }


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

    private void populate(PreparedStatement popPS, Constraint constraint) throws SQLException {
        popPS.setLong(1, constraint.getTeacherID());
        popPS.setTimestamp(2, Timestamp.valueOf(constraint.getStartDate()));
        popPS.setTimestamp(3, Timestamp.valueOf(constraint.getEndDate()));
    }

    @Override
    public String getTableName() {
        return "CONSTRAINTS";
    }
}