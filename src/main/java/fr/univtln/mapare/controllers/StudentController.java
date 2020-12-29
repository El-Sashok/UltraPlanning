package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.StudentDAO;
import fr.univtln.mapare.entities.Student;

import java.sql.SQLException;

public abstract class StudentController {

    public static void loadStudents() throws SQLException {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.findAll();
        studentDAO.close();
    }

    public static void remove(Student student) throws SQLException {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.remove(student.getId());
        studentDAO.close();
        Student.popStudentInList(student);
    }

}
