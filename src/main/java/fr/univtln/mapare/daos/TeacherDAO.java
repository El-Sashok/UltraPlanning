package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Constraint;
import fr.univtln.mapare.entities.Teacher;
import fr.univtln.mapare.exceptions.DataAccessException;
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
    private final ConstraintDAO constraintDAO;

    public TeacherDAO() throws DataAccessException {
        super("", "");
        this.constraintDAO = new ConstraintDAO();
        PreparedStatement _findConstraintsPS = null;
        try {
            _findConstraintsPS = connection.prepareStatement("SELECT * FROM CONSTRAINTS WHERE TEACHER=?");
        } catch (SQLException throwable) {
            throw new DataAccessException(throwable.getLocalizedMessage());
        }
        this.findConstraintsPS = _findConstraintsPS;
    }

    @Override
    protected Teacher fromResultSet(ResultSet resultSet) {
        return null;
    }

    protected Teacher fromResultSet(ResultSet resultSet, ArrayList<Constraint> constraints) throws SQLException {
        Teacher teacher;
        Teacher.Role role;
        switch (resultSet.getString("STATUS")){
            case "P":
                role = Teacher.Role.PROFESSOR;
                break;
            case "V":
                role = Teacher.Role.ADJUNCT_PROF;
                break;
            default:
                role = Teacher.Role.LECTURER;
                break;
        }
        teacher = new Teacher(resultSet.getLong("ID"),
                resultSet.getString("SURNAME"),
                resultSet.getString("NAME"),
                resultSet.getDate("BIRTHDATE"),
                resultSet.getString("EMAIL"),
                resultSet.getString("PASSWORD"),
                resultSet.getString("LABORATORY"),
                role);
        for (Constraint c : constraints) {
            teacher.addConstraint(c);
        }
        return teacher;
    }

    @Override
    public Optional<Teacher> find(long id) throws DataAccessException {
        Teacher teacher = null;
        ArrayList<Constraint> constraints = new ArrayList<>();
        try {
            findPS.setLong(1, id);
            findConstraintsPS.setLong(1, id);
            ResultSet rs_findPS = findPS.executeQuery();
            ResultSet rs_findCS = findConstraintsPS.executeQuery();
            while (rs_findCS.next()) constraints.add(constraintDAO.fromResultSet(rs_findCS));
            while (rs_findPS.next()) teacher = fromResultSet(rs_findPS, constraints);
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return Optional.ofNullable(teacher);
    }

    @Override
    public List<Teacher> findAll() throws DataAccessException {
        List<Teacher> entityList = new ArrayList<>();
        try {
            ResultSet rs_findPS = findAllPS.executeQuery();
            while (rs_findPS.next()){
                ArrayList<Constraint> constraints = new ArrayList<>();
                try {
                    findConstraintsPS.setLong(1, rs_findPS.getLong("ID"));
                    ResultSet rs_findCS = findConstraintsPS.executeQuery();
                    while (rs_findCS.next()) constraints.add(constraintDAO.fromResultSet(rs_findCS));
                    entityList.add(fromResultSet(rs_findPS, constraints));
                } catch (SQLException e) {
                    throw new DataAccessException(e.getLocalizedMessage());
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getLocalizedMessage());
        }
        return entityList;
    }

    @Override
    public Teacher persist(Teacher teacher) throws DataAccessException {
        return null;
    }

    @Override
    public void update(Teacher teacher) throws DataAccessException {

    }

    @Override
    public String getTableName() { return "TEACHER"; }
}
