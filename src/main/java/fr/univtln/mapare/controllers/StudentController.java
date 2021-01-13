package fr.univtln.mapare.controllers;

import fr.univtln.mapare.daos.StudentDAO;
import fr.univtln.mapare.entities.Student;
import fr.univtln.mapare.exceptions.updateexceptions.EmptyAttributeException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import java.sql.SQLException;
import java.util.Date;

public abstract class StudentController {

    private StudentController() {}

    /**
     * Fonction d'initialisation : Elle permet de charger tout les Étudiants
     * @throws SQLException Exception SQL
     */
    public static void loadStudents() throws SQLException {
        try (StudentDAO studentDAO = new StudentDAO()) {
            studentDAO.findAll();
        }
    }

    /**
     * Permet de supprimer un étudiant
     * @param student Étudiant à supprimer
     * @throws SQLException Exception SQL
     */
    public static void remove(Student student) throws SQLException {
        try (StudentDAO studentDAO = new StudentDAO()) {
            studentDAO.remove(student.getId());
        }
        Student.popStudentInList(student);
    }

    /**
     * Permet de créer un nouvel étudiant
     * @param lastName Le nom de l'étudiant
     * @param firstName Le prénom de l'étudiant
     * @param birthdate La date de naissance de l'étudiant
     * @param email Email de l'étudiant
     * @throws SQLException Exception SQL
     */
    public static void createStudent(String lastName, String firstName, Date birthdate, String email) throws SQLException {
        Student student = new Student(-1, lastName, firstName, birthdate, email);
        try (StudentDAO studentDAO = new StudentDAO()) {
            studentDAO.persist(student);
        }
    }

    /**
     * Permet de changer l'email d'un étudiant
     * @param student L'étudiant
     * @param email La nouvelle adresse email de l'enseignant
     * @throws SQLException Exception SQL
     * @throws EmptyAttributeException email est vide
     * @throws NotChangedException Aucune modification apportée
     */
    public static void changeEmail(Student student, String email) throws SQLException, EmptyAttributeException, NotChangedException {
        if (email.isEmpty())
            throw new EmptyAttributeException("changeEmail", student);
        if (student.getEmail().equals(email))
            throw new NotChangedException(student);

        student.setEmail(email);
        try (StudentDAO studentDAO = new StudentDAO()) {
            studentDAO.update(student);
        }
    }

}
