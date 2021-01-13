package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.TeacherDAO;
import fr.univtln.mapare.entities.Teacher;

import java.sql.SQLException;

public abstract class TeacherController {

    private TeacherController() {}

    /**
     * Fonction d'initialisation : Elle permet de charger tout les Enseignants
     * @throws SQLException Exception SQL
     */
    public static void loadTeachers() throws SQLException {
        try (TeacherDAO teacherDAO = new TeacherDAO()) {
            teacherDAO.findAll();
        }
    }

    /**
     * Permet de Supprimer un enseignant
     * @param teacher Enseignant à supprimer
     * @throws SQLException Exception SQL
     */
    public static void remove(Teacher teacher) throws SQLException {
        try (TeacherDAO teacherDAO = new TeacherDAO()) {
            teacherDAO.remove(teacher.getId());
        }
        Teacher.popTeacherInList(teacher);
    }

}
