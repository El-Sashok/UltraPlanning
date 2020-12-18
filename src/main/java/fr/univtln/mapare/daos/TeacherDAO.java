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
    private final PreparedStatement persistConstraintsPS;
    private final PreparedStatement updateConstraintsPS;
    private final ConstraintDAO constraintDAO;

    public TeacherDAO() throws SQLException {
        super("", "");
        this.constraintDAO = new ConstraintDAO();

        this.findConstraintsPS = connection.prepareStatement("SELECT * FROM CONSTRAINTS WHERE TEACHER=?");
        this.persistConstraintsPS = connection.prepareStatement("SELECT * FROM CONSTRAINTS WHERE TEACHER=?");
        this.updateConstraintsPS = connection.prepareStatement("SELECT * FROM CONSTRAINTS WHERE TEACHER=?");
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
        while (findConstraintsRS.next()) constraints.add(constraintDAO.fromResultSet(findConstraintsRS));

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
            while (rs_findCS.next()) constraints.add(constraintDAO.fromResultSet(rs_findCS));
            entityList.add(fromResultSet(rs_findPS, constraints));
        }
        return entityList;
    }

    @Override
    public Teacher persist(Teacher teacher) {
        return null;
    }

    @Override
    public void update(Teacher teacher) {

    }

    @Override
    public String getTableName() { return "TEACHER"; }
}
