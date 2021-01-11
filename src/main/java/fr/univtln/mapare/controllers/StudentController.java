package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.StudentDAO;
import fr.univtln.mapare.entities.Student;

import java.sql.SQLException;

public abstract class StudentController {

    private StudentController() {}

    public static void loadStudents() throws SQLException {
        try (StudentDAO studentDAO = new StudentDAO()) {
            studentDAO.findAll();
        }
    }

    public static void remove(Student student) throws SQLException {
        try (StudentDAO studentDAO = new StudentDAO()) {
            studentDAO.remove(student.getId());
        }
        Student.popStudentInList(student);
    }

}
