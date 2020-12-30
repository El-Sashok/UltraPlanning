package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.TeacherDAO;
import fr.univtln.mapare.entities.Teacher;

import java.sql.SQLException;

public abstract class TeacherController {

    public static void loadTeachers() throws SQLException {
        TeacherDAO teacherDAO = new TeacherDAO();
        teacherDAO.findAll();
        teacherDAO.close();
    }

    public static void remove(Teacher teacher) throws SQLException {
        TeacherDAO teacherDAO = new TeacherDAO();
        teacherDAO.remove(teacher.getId());
        teacherDAO.close();
        Teacher.popTeacherInList(teacher);
    }

}
