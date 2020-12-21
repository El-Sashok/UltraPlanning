package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Constraint;
import fr.univtln.mapare.entities.Teacher;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log
public class TeacherDAO extends AbstractDAO<Teacher> {

    private final PreparedStatement findConstraintsPS;
    private final PreparedStatement persistConstraintsPS;
    private final PreparedStatement updateConstraintsPS;

    public TeacherDAO() throws SQLException {
        super("INSERT INTO TEACHER(SURNAME, NAME, BIRTHDATE, EMAIL, LABORATORY, STATUS, PASSWORD) VALUES (?,?,?,?,?,?,?)",
                "UPDATE TEACHER SET SURNAME=?, NAME=?, BIRTHDATE=?, EMAIL=?, LABORATORY=?, STATUS=?, PASSWORD=? WHERE ID=?");
        this.findConstraintsPS = connection.prepareStatement("SELECT * FROM CONSTRAINTS WHERE TEACHER=?");
        this.persistConstraintsPS = connection.prepareStatement("INSERT INTO CONSTRAINTS(TEACHER, START, END) VALUES(?,?,?)");
        this.updateConstraintsPS = connection.prepareStatement("UPDATE CONSTRAINTS SET TEACHER=?, START=?, END=? WHERE ID=?");
    }

    @Override
    protected Teacher fromResultSet(ResultSet resultSet) {
        return null;
    }

    protected Teacher fromResultSet(ResultSet resultSet, List<Constraint> constraints) throws SQLException {
        Teacher teacher;

        teacher = new Teacher(resultSet.getLong("ID"),
                resultSet.getString("SURNAME"),
                resultSet.getString("NAME"),
                resultSet.getDate("BIRTHDATE"),
                resultSet.getString("EMAIL"),
                resultSet.getString("PASSWORD"),
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
        List<Constraint> constraints = new ArrayList<>();

        findConstraintsPS.setLong(1, id);
        ResultSet findConstraintsRS = findConstraintsPS.executeQuery();
        while (findConstraintsRS.next()) constraints.add(new Constraint(findConstraintsRS.getDate("START"), findConstraintsRS.getDate("END")));

        findPS.setLong(1, id);
        ResultSet findRS = findPS.executeQuery();
        if (findRS.next()) teacher = fromResultSet(findRS, constraints);

        return Optional.ofNullable(teacher);
    }

    @Override
    public List<Teacher> findAll() throws SQLException {
        List<Teacher> entityList = new ArrayList<>();
        ResultSet rs_findPS = findAllPS.executeQuery();
        while (rs_findPS.next()){
            ArrayList<Constraint> constraints = new ArrayList<>();
            findConstraintsPS.setLong(1, rs_findPS.getLong("ID"));
            ResultSet rs_findCS = findConstraintsPS.executeQuery();
            while (rs_findCS.next()) constraints.add(new Constraint(rs_findCS.getDate("START"), rs_findCS.getDate("END")));
            entityList.add(fromResultSet(rs_findPS, constraints));
        }
        return entityList;
    }

    @Override
    public Teacher persist(Teacher teacher) throws SQLException {
        populate(persistPS, teacher);
        Teacher t = super.persist();
        persistConstraints(teacher, t);
        return t;
    }

    @Override
    public void update(Teacher teacher) throws SQLException {
        populate(updatePS, teacher);
        updatePS.setLong(8, teacher.getId());
        super.update();
    }

    public void populate(PreparedStatement popPS, Teacher teacher) throws SQLException {
        popPS.setString(1, teacher.getLastName());
        popPS.setString(2, teacher.getFirstName());
        popPS.setDate(3, new java.sql.Date(teacher.getBirthdate().getTime()));
        popPS.setString(4, teacher.getEmail());
        popPS.setString(5, teacher.getLaboratory());
        popPS.setString(6, teacher.getRole().toString());
        popPS.setString(7, teacher.getPassword());
    }

    public void persistConstraints(Teacher teacher, Teacher teacherWithID) throws SQLException {
        for (Constraint c: teacher.getConstraints()) {
            persistConstraint(teacherWithID, c);
        }
    }

    public void persistConstraint(Teacher teacher, Constraint constraint) throws SQLException {
        populateConstraint(persistConstraintsPS, teacher, constraint);
        persistConstraintsPS.executeUpdate();
    }

    private void updateConstraint(Teacher teacher, Constraint constraint, Long teacherConstraintID) throws SQLException {
        populateConstraint(updateConstraintsPS, teacher, constraint);
        updateConstraintsPS.setLong(4, teacherConstraintID);
        updateConstraintsPS.executeUpdate();
    }

    private void populateConstraint(PreparedStatement popTeacherPS, Teacher teacher, Constraint constraint) throws SQLException {
        popTeacherPS.setLong(1, teacher.getId());
        popTeacherPS.setDate(2, new java.sql.Date(constraint.getStartDate().getTime()));
        popTeacherPS.setDate(3, new java.sql.Date(constraint.getStartDate().getTime()));
    }

    @Override
    public String getTableName() { return "TEACHER"; }
}
