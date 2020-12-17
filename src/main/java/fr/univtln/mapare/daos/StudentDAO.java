package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Student;
import fr.univtln.mapare.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class StudentDAO extends AbstractDAO<Student> {

    public StudentDAO() {
        super("",
                "");
    }

    @Override
    protected Student fromResultSet(ResultSet resultSet) throws SQLException {
        return new Student(resultSet.getInt("ID"), resultSet.getString("SURNAME"), resultSet.getString("NAME"), resultSet.getDate("BIRTHDATE"), resultSet.getString("EMAIL"), resultSet.getString("PASSWORD"));
    }

    @Override
    public Student persist(Student student) throws DataAccessException {
        return null;
    }

    @Override
    public void update(Student student) throws DataAccessException {

    }

    @Override
    public String getTableName() {
        return "PERSONNE";
    }
}
