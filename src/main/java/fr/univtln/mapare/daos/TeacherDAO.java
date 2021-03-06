package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Constraint;
import fr.univtln.mapare.entities.Teacher;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
public class TeacherDAO extends AbstractDAO<Teacher> {

    public TeacherDAO() throws SQLException {
        super("INSERT INTO TEACHER(SURNAME, NAME, BIRTHDATE, EMAIL, LABORATORY, STATUS) VALUES (?,?,?,?,?,?)",
                "UPDATE TEACHER SET SURNAME=?, NAME=?, BIRTHDATE=?, EMAIL=?, LABORATORY=?, STATUS=? WHERE ID=?");
    }

    @Override
    protected Teacher fromResultSet(ResultSet resultSet) {
        return null;
    }

    protected Teacher fromResultSet(ResultSet resultSet, List<Constraint> constraints) throws SQLException {
        for (Teacher t: Teacher.getTeacherList()) {
            if (t.getId() == resultSet.getLong("ID"))
                return t;
        }
        Teacher teacher = new Teacher(resultSet.getLong("ID"),
                resultSet.getString("SURNAME"),
                resultSet.getString("NAME"),
                resultSet.getDate("BIRTHDATE"),
                resultSet.getString("EMAIL"),
                resultSet.getString("LABORATORY"),
                Teacher.Role.valueOf(resultSet.getString("STATUS")));
        for (Constraint c : constraints) {
            teacher.addConstraint(c);
        }
        return teacher;
    }

    @Override
    public Optional<Teacher> find(long id) throws SQLException {
        Teacher teacher = null;
        List<Constraint> constraints;
        try (ConstraintDAO constraintDAO = new ConstraintDAO()) {
            constraints = constraintDAO.findByTeacher(id);
        }
        findPS.setLong(1, id);
        ResultSet findRS = findPS.executeQuery();
        if (findRS.next()) teacher = fromResultSet(findRS, constraints);

        return Optional.ofNullable(teacher);
    }

    @Override
    public List<Teacher> findAll() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        ResultSet findAllRS = findAllPS.executeQuery();
        try (ConstraintDAO constraintDAO = new ConstraintDAO()) {
            while (findAllRS.next()) {
                List<Constraint> constraints = constraintDAO.findByTeacher(findAllRS.getLong("ID"));
                teachers.add(fromResultSet(findAllRS, constraints));
            }
        }
        return teachers;
    }

    @Override
    public Teacher persist(Teacher teacher) throws SQLException {
        populate(persistPS, teacher);
        Teacher t = super.persist();
        Teacher.popTeacherInList(t);
        try (ConstraintDAO constraintDAO = new ConstraintDAO()) {
            for (Constraint c : teacher.getConstraints()) {
                c.setTeacher(t);
                constraintDAO.persist(c);
            }
        }
        return find(t.getId()).get();
    }

    @Override
    public void update(Teacher teacher) throws SQLException {
        populate(updatePS, teacher);
        updatePS.setLong(7, teacher.getId());
        try (ConstraintDAO constraintDAO = new ConstraintDAO()) {
            for (Constraint c : teacher.getConstraints())
                constraintDAO.update(c);
        }
        super.update();
    }

    public void populate(PreparedStatement popPS, Teacher teacher) throws SQLException {
        popPS.setString(1, teacher.getLastName());
        popPS.setString(2, teacher.getFirstName());
        popPS.setDate(3, new java.sql.Date(teacher.getBirthdate().getTime()));
        popPS.setString(4, teacher.getEmail());
        popPS.setString(5, teacher.getLaboratory());
        popPS.setString(6, teacher.getRole().toString());
    }


    @Override
    public String getTableName() { return "TEACHER"; }
}
