package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.TeacherDAO;
import fr.univtln.mapare.entities.Teacher;
import fr.univtln.mapare.exceptions.updateexceptions.EmptyAttributeException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import java.sql.SQLException;
import java.util.Date;

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

    /**
     * Permet de créer un nouveau professeur
     * @param lastName Le nom de l'enseignant
     * @param firstName Le prénom de l'enseignant
     * @param birthdate La date de naissance de l'enseignant
     * @param email Email de l'enseignant
     * @param laboratory Laboratoire affilié
     * @param role Qualification donné à l'enseignant
     * @throws SQLException Exception SQL
     */
    public static void createTeacher(String lastName, String firstName, Date birthdate, String email, String laboratory, Teacher.Role role) throws SQLException {
        Teacher teacher = new Teacher(-1, lastName, firstName, birthdate, email, laboratory, role);
        try (TeacherDAO teacherDAO = new TeacherDAO()) {
            Teacher t = teacherDAO.persist(teacher);
            System.out.println(t);
        }
    }

    /**
     * Permet de changer l'email d'un enseignant
     * @param teacher L'enseignant
     * @param email La nouvelle adresse email de l'enseignant
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException email est vide
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeEmail(Teacher teacher, String email) throws SQLException, EmptyAttributeException, NotChangedException {
        if (email.isEmpty())
            throw new EmptyAttributeException("changeEmail", teacher);
        if (teacher.getEmail().equals(email))
            throw new NotChangedException(teacher);

        teacher.setEmail(email);
        try (TeacherDAO teacherDAO = new TeacherDAO()) {
            teacherDAO.update(teacher);
        }
    }

    /**
     * Permet de changer le statut d'un enseignant (Maître de conférence, ...)
     * @param teacher L'enseignant
     * @param role Le nouveau statut de l'enseignant
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException email est vide
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeStatus(Teacher teacher, Teacher.Role role) throws SQLException, NotChangedException {
        if (teacher.getRole().equals(role))
            throw new NotChangedException(teacher);

        teacher.setRole(role);
        try (TeacherDAO teacherDAO = new TeacherDAO()) {
            teacherDAO.update(teacher);
        }
    }

    /**
     * Permet de changer le statut d'un enseignant (Maître de conférence, ...)
     * @param teacher L'enseignant
     * @param laboratory Le nouveau laboratoire où est affilié l'enseignant
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException email est vide
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeLabo(Teacher teacher, String laboratory) throws SQLException, EmptyAttributeException, NotChangedException {
        if (laboratory.isEmpty())
            throw new EmptyAttributeException("chnageLabo", teacher);
        if (teacher.getLaboratory().equals(laboratory))
            throw new NotChangedException(teacher);

        teacher.setLaboratory(laboratory);
        try (TeacherDAO teacherDAO = new TeacherDAO()) {
            teacherDAO.update(teacher);
        }
    }

}
