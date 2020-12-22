package fr.univtln.mapare.daos;

import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Student;
import lombok.extern.java.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class StudentDAO extends AbstractDAO<Student> {

    public StudentDAO() throws SQLException {
        super("INSERT INTO STUDENT(SURNAME, NAME, BIRTHDATE, EMAIL, PASSWORD) VALUES(?,?,?,?,?)",
                "UPDATE STUDENT SET SURNAME=?, NAME=?, BIRTHDATE=?, EMAIL=?, PASSWORD=? WHERE ID=?");
    }

    @Override
    protected Student fromResultSet(ResultSet resultSet) throws SQLException {
        for (Student s: Student.getStudentList()) {
            if (s.getId() == resultSet.getLong("ID"))
                return s;
        }
        return new Student(resultSet.getInt("ID"),
                resultSet.getString("SURNAME"),
                resultSet.getString("NAME"),
                resultSet.getDate("BIRTHDATE"),
                resultSet.getString("EMAIL"),
                resultSet.getString("PASSWORD"));
    }


    @Override
    public Student persist(Student student) throws SQLException {
        populate(persistPS, student);
        return super.persist();
    }

    @Override
    public void update(Student student) throws SQLException {
        populate(updatePS, student);
        updatePS.setLong(6, student.getId());
        super.update();
    }

    private void populate(PreparedStatement popPS, Student student) throws SQLException {
        popPS.setString(1, student.getLastName());
        popPS.setString(2, student.getFirstName());
        popPS.setDate(3, new java.sql.Date(student.getBirthdate().getTime()));
        popPS.setString(4, student.getEmail());
        popPS.setString(5, student.getPassword());
    }

    @Override
    public String getTableName() {
        return "STUDENT";
    }
}
