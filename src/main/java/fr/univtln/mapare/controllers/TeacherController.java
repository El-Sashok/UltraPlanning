package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.TeacherDAO;
import fr.univtln.mapare.entities.Teacher;

import java.sql.SQLException;

public abstract class TeacherController {

    private TeacherController() {}

    public static void loadTeachers() throws SQLException {
        try (TeacherDAO teacherDAO = new TeacherDAO()) {
            teacherDAO.findAll();
        }
    }

    public static void remove(Teacher teacher) throws SQLException {
        try (TeacherDAO teacherDAO = new TeacherDAO()) {
            teacherDAO.remove(teacher.getId());
        }
        Teacher.popTeacherInList(teacher);
    }

}
